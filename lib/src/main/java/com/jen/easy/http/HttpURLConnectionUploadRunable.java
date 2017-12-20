package com.jen.easy.http;

import com.jen.easy.log.EasyLibLog;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

class HttpURLConnectionUploadRunable extends HttpURLConnectionRunable {

    HttpURLConnectionUploadRunable(HttpUploadRequest request) {
        super(request, "HttpUpload : ");
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (request.flag.isBreak && request.flag.endPoit > request.flag.startPoit + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.flag.startPoit + "-" + request.flag.endPoit);
        }

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        File file = new File(request.flag.filePath);
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        long curBytes = request.flag.startPoit;
        int len = 0;
        byte[] bufferOut = new byte[1024];
        while ((len = in.read(bufferOut)) != -1 && !request.flag.userCancel) {
            out.write(bufferOut, 0, len);
            if (request.flag.userCancel) {
                break;
            } else {
                progress(curBytes, request.flag.endPoit);
            }
        }
        in.close();
        out.flush();
        out.close();

        if (request.flag.userCancel) {
            fail("用户取消上传");
            return;
        }

        // 读取返回数据
        StringBuffer buffer = new StringBuffer("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), mCharset));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        String result = buffer.toString();
        EasyLibLog.d(TAG + mUrlStr + " 服务器完成，返回数据：" + result);
        success(result);
    }

    @Override
    protected void success(String result) {
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (request.getUploadListener() != null) {
            HttpParseManager parseManager = new HttpParseManager();
            parseManager.setResponseObjectType(request.responseObjectType);
            Object parseObject = parseManager.parseJson(mResponseClass, result);
            if (parseObject == null) {
                fail("数据解析异常");
            } else {
                request.getUploadListener().success(request.flag.code, request.flag.str, parseObject);
            }
        }
    }

    @Override
    protected void fail(String msg) {
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (request.getUploadListener() != null)
            request.getUploadListener().fail(request.flag.code, request.flag.str, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (request.getUploadListener() != null)
            request.getUploadListener().progress(request.flag.code, request.flag.str, currentPoint, endPoint);
    }
}