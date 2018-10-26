package com.jen.easy.http;

import com.jen.easy.constant.TAG;
import com.jen.easy.http.imp.HttpUploadListener;
import com.jen.easy.log.EasyLog;

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

class HttpURLConnectionUploadRunnable extends HttpURLConnectionRunnable {
    private HttpUploadListener uploadListener;

    HttpURLConnectionUploadRunnable(HttpUploadRequest request, HttpUploadListener uploadListener) {
        super(request);
        this.uploadListener = uploadListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (request.isBreak && request.endPoint > request.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.startPoint + "-" + request.endPoint);
        }

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        File file = new File(request.filePath);
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        long curBytes = request.startPoint;
        int len;
        byte[] bufferOut = new byte[1024];
        while ((len = in.read(bufferOut)) != -1 && !request.userCancel) {
            out.write(bufferOut, 0, len);
            if (request.userCancel) {
                break;
            } else {
                progress(curBytes, request.endPoint);
            }
        }
        in.close();
        out.flush();
        out.close();

        if (request.userCancel) {
            fail("上传失败：用户取消上传");
            return;
        }

        // 读取返回数据
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), mCharset));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        String result = buffer.toString();
        EasyLog.d(TAG.EasyHttp, mUrlStr + " 完成，返回数据：" + result);
        if (mRequest.responseReplaceMap != null) {
            result = HttpTools.replaceResponse(mRequest, result);
            EasyLog.d(TAG.EasyHttp, mUrlStr + " 格式化后数据：" + result);
        }
        success(result, null);
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        EasyLog.d(TAG.EasyHttp, mUrlStr + " 上传成功！");
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (uploadListener != null && request.state == HttpState.RUN) {
            HttpParseManager parseManager = new HttpParseManager();
            parseManager.setHttpState(request.state);
            Object parseObject = parseManager.parseJson(mResponse, result, headMap);
            if (request.state == HttpState.STOP) {
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
            } else if (parseObject == null) {
                fail("返回数据解析异常");
            } else {
                uploadListener.success(request.flagCode, request.flagStr, parseObject);
            }
        }
    }

    @Override
    protected void fail(String msg) {
        EasyLog.w(TAG.EasyHttp, mUrlStr + " " + msg);
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (uploadListener != null && request.state == HttpState.RUN)
            uploadListener.fail(request.flagCode, request.flagStr, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        HttpUploadRequest request = (HttpUploadRequest) mRequest;
        if (uploadListener != null && request.state == HttpState.RUN)
            uploadListener.progress(request.flagCode, request.flagStr, currentPoint, endPoint);
    }
}