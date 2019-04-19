package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpFullListener;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestState;
import com.jen.easy.http.response.EasyHttpResponse;
import com.jen.easy.http.response.EasyResponseState;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class URLConnectionUploadRunnable extends URLConnectionFactoryRunnable {
    private EasyHttpFullListener fullListener;

    URLConnectionUploadRunnable(EasyHttpUploadRequest request, EasyHttpFullListener fullListener, int flagCode, String flagStr) {
        super(request, flagCode, flagStr);
        this.fullListener = fullListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        EasyHttpUploadRequest request = (EasyHttpUploadRequest) mRequest;
        if (request.isBreak && request.endPoint > request.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.startPoint + "-" + request.endPoint);
        }

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        File file = new File(request.filePath);
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        long curBytes = request.startPoint;
        int len;
        byte[] bufferOut = new byte[1024];
        while ((len = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, len);
            if (request.getRequestState() == EasyRequestState.interrupt) {
                break;
            } else {
                progress(curBytes, request.endPoint);
            }
        }
        in.close();
        out.flush();
        out.close();

        // 读取返回数据
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), mCharset));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {//拦截数据解析
            HttpLog.d(mUrlStr + " 网络请求停止!\n   ");
            return;
        }
        String result = buffer.toString();
        HttpLog.i(mUrlStr + " 上传成功，返回数据：" + result);
        result = replaceResult(result);
        mRequest.setRequestState(EasyRequestState.finish);
        success(result, null);
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (fullListener == null) {
            return;
        }
        Object parseObject;
        if (FieldType.isObject(mResponse) || FieldType.isString(mResponse)) {//Object和String类型不做数据解析
            parseObject = result;
        } else {
            parseObject = new HttpParseManager().parseResponseBody(mResponse, result);
        }
        if (parseObject == null) {
            fail("");
        } else {
            HttpLog.d(mUrlStr + " 成功!\n   ");
            if(parseObject instanceof EasyHttpResponse){
                ((EasyHttpResponse) parseObject).setResponseState(EasyResponseState.finish);
            }
            fullListener.success(flagCode, flagStr, parseObject, headMap);
        }
    }

    @Override
    protected void fail(String msg) {
        HttpLog.w(mUrlStr + " 失败!\n   ");
        if (fullListener != null) {
            fullListener.fail(flagCode, flagStr, msg);
        }
    }

    private void progress(long currentPoint, long endPoint) {
        if (fullListener != null) {
            fullListener.progress(flagCode, flagStr, currentPoint, endPoint);
        }
    }
}