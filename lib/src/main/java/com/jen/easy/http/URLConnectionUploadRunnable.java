package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpFullListener;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestState;
import com.jen.easy.http.response.EasyHttpResponse;
import com.jen.easy.http.response.EasyResponseState;
import com.jen.easy.log.JsonLogFormat;

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

        String result = buffer.toString();
        StringBuilder retLogBuild = new StringBuilder();
        retLogBuild.append(mRequestLogInfo).append("\n返回原始数据：\n").append(result);
        if (mRequest.getReplaceResult().size() > 0) {
            result = replaceResult(result);
            retLogBuild.append("\n格式化后数据：\n").append(result);
        }
        retLogBuild.append("\n服务器返回码：").append(mResponseCode);
        HttpLog.i(JsonLogFormat.formatJson(retLogBuild.toString()));
        success(result, null);
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.d(mRequestLogInfo + " 请求中断。\n \t");
            return;
        }
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
            if (parseObject instanceof EasyHttpResponse) {
                ((EasyHttpResponse) parseObject).setResponseState(EasyResponseState.finish);
            }
            mRequest.setRequestState(EasyRequestState.finish);
            HttpLog.d(mRequestLogInfo + " 返回成功。\n \t");
            fullListener.success(flagCode, flagStr, parseObject, headMap);
        }
    }

    @Override
    protected void fail(String msg) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.d(mRequestLogInfo + " 请求中断。\n \t");
            return;
        }
        if (fullListener == null) {
            return;
        }
        mRequest.setRequestState(EasyRequestState.finish);
        HttpLog.w(mRequestLogInfo + " 返回失败。\n \t");
        fullListener.fail(flagCode, flagStr, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        if (fullListener != null) {
            fullListener.progress(flagCode, flagStr, currentPoint, endPoint);
        }
    }
}