package com.jen.easy.http;

import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpUploadRequest;

import org.json.JSONException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.UUID;

class URLConnectionUploadRunnable extends URLConnectionFactoryRunnable {
    private Object responseObjectProgress;

    URLConnectionUploadRunnable(EasyHttpUploadRequest request, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super(request, httpListener, flagCode, flagStr);
        mType = Type.fileUp;
    }

    @Override
    protected boolean childRun(HttpURLConnection connection) throws IOException {
        responseObjectProgress = createResponseObject(Response.progress, null);
        EasyHttpUploadRequest request = (EasyHttpUploadRequest) mRequest;
        File file = new File(request.filePath);
        /*虽然说这个方法可行，但需要注意的是，一些服务器并不支持这种模式。
        可以改用 从1.7后引入的新方法：setFixedLengthStreamingMode(writeLength);
        它同setChunkedStreamingMode(0)有类似的效果，但在服务器兼容性上做的更好。*/
//        connection.setFixedLengthStreamingMode(file.length());//HttpURLConnection上传大文件内存溢出的原因及解决办法（文件大小无变化时才适用）
        connection.setChunkedStreamingMode(0);//注意部分服务器可能不支持
        if (request.isBreak && request.endPoint > request.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.startPoint + "-" + request.endPoint);
        }
        long startTime = System.currentTimeMillis();
        switch (request.uploadType) {
            case OnlyFile:
                uploadFile(connection, request, file);
                break;
            case ParamFile:
                uploadFileAndParam(connection, request, file);
                break;
        }
        return runResponse(connection, startTime);
    }

    /**
     * 单文件上传模式
     */
    private void uploadFile(HttpURLConnection connection, EasyHttpUploadRequest request, File file) throws IOException {
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
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
    private void uploadFileAndParam(HttpURLConnection connection, EasyHttpUploadRequest request, File file) throws IOException {
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

    private void progress(long currentPoint, long endPoint) {
        if (httpListener != null) {
            httpListener.progress(flagCode, flagStr, mRequest, responseObjectProgress, currentPoint, endPoint);
        }
    }
}