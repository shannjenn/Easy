package com.jen.easy.http;

import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestState;

import org.json.JSONException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class URLConnectionUploadRunnable extends URLConnectionFactoryRunnable {
    private Object responseObjectProgress;

    URLConnectionUploadRunnable(EasyHttpUploadRequest request, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super(request, httpListener, flagCode, flagStr);
    }

    @Override
    protected boolean childRun(HttpURLConnection connection) throws IOException {
        responseObjectProgress = createResponseObjectProgress(Type.fileUp);
        EasyHttpUploadRequest request = (EasyHttpUploadRequest) mRequest;
        if (request.isBreak && request.endPoint > request.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.startPoint + "-" + request.endPoint);
        }
        long startTime = System.currentTimeMillis();
        switch (request.uploadType) {
            case OnlyFile:
                uploadFile(connection, request);
                break;
            case ParamFile:
                uploadFileAndParam(connection, request);
                break;
        }
        return runResponse(connection, startTime);
    }

    /**
     * 单文件上传模式
     */
    private void uploadFile(HttpURLConnection connection, EasyHttpUploadRequest request) throws IOException {
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        File file = new File(request.filePath);
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        long curBytes = request.startPoint;
        long endBytes = file.length();

        int len;
        byte[] bufferOut = new byte[1024];
        while ((len = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, len);
            curBytes += len;
            progress(curBytes, endBytes);
        }
        in.close();
        out.flush();
        out.close();
    }


    /**
     * 参数和文件上传模式
     */
    private void uploadFileAndParam(HttpURLConnection connection, EasyHttpUploadRequest request) throws IOException {
        File file = new File(request.filePath);
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        connection.setInstanceFollowRedirects(false);//不自动重定向
        connection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);//设置请求头
        connection.setRequestProperty("connection", "keep-alive");//保持连接
        connection.connect();

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        Iterator<String> keys = mRequestObject.body.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            StringBuilder buffer = new StringBuilder();
            Object value = "";
            try {
                value = mRequestObject.body.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            buffer.append(PREFIX).append(BOUNDARY).append(LINE_END);
            buffer.append("Content-Disposition: form-data; name=\"")
                    .append(key).append("\"").append(LINE_END)
                    .append(LINE_END);
            buffer.append(value).append(LINE_END);
            String params = buffer.toString();
            out.write(params.getBytes());
        }

        if (null == request.fileNameValue || request.fileNameValue.trim().length() == 0) {
            request.fileNameValue = file.getName();
        }
        String buffer = PREFIX + BOUNDARY + LINE_END
                + "Content-Disposition: form-data; name=\"" + request.fileNameKey
                + "\"; filename=\"" + request.fileNameValue + "\""
                + LINE_END
                + "Content-Type: application/octet-stream; charset="
                + mCharset + LINE_END
                + LINE_END;
        out.write(buffer.getBytes());
        InputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len;
        long curBytes = request.startPoint;
        long endBytes = file.length();
        while ((len = fileInputStream.read(bytes)) != -1) {
            out.write(bytes, 0, len);
            curBytes += len;
            progress(curBytes, endBytes);
        }
        out.write(LINE_END.getBytes());
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
        out.write(end_data);

        fileInputStream.close();
        out.flush();
        out.close();
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        mRequest.setRequestState(EasyRequestState.finish);
        if (httpListener != null)
            httpListener.success(flagCode, flagStr, createResponseObjectSuccess(Type.fileUp, result), headMap);
    }

    @Override
    protected void fail() {
        mRequest.setRequestState(EasyRequestState.finish);
        if (httpListener != null)
            httpListener.fail(flagCode, flagStr, createResponseObjectFail(Type.fileUp));
    }

    private void progress(long currentPoint, long endPoint) {
        HttpLog.d(mUrlStr + " 上传进度：currentPoint = " + currentPoint + " endPoint = " + endPoint);
        if (httpListener != null)
            httpListener.progress(flagCode, flagStr, responseObjectProgress, currentPoint, endPoint);
    }
}