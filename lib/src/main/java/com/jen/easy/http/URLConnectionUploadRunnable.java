package com.jen.easy.http;

import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestState;
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
    private Object responseObjectProgress;

    URLConnectionUploadRunnable(EasyHttpUploadRequest request, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super(request, httpListener, flagCode, flagStr);
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        responseObjectProgress = createResponseObjectProgress(Type.fileUp);
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
        StringBuilder bodyBuffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), mCharset));
        String line;
        while ((line = reader.readLine()) != null) {
            bodyBuffer.append(line);
        }
        reader.close();

        String body = bodyBuffer.toString();

        StringBuilder retLogBuild = new StringBuilder();
        retLogBuild.append(mRequestLogInfo).append("\nresponse body：\n").append(body);
        if (mRequest.getReplaceResult().size() > 0) {
            body = replaceResult(body);
            retLogBuild.append("\nformat return body：\n").append(body);
        }
        retLogBuild.append("\nresponse code：").append(mResponseCode);
        HttpLog.i(JsonLogFormat.formatJson(retLogBuild.toString()));
        success(body, null);
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (checkListener())
            httpListener.success(flagCode, flagStr, createResponseObjectSuccess(Type.fileUp, result), headMap);
    }

    @Override
    protected void fail() {
        if (checkListener())
            httpListener.fail(flagCode, flagStr, createResponseObjectFail(Type.fileUp));
    }

    private void progress(long currentPoint, long endPoint) {
        HttpLog.d(mUrlStr + " 上传进度：currentPoint = " + currentPoint + " endPoint = " + endPoint);
        if (checkListener())
            httpListener.progress(flagCode, flagStr, responseObjectProgress, currentPoint, endPoint);
    }
}