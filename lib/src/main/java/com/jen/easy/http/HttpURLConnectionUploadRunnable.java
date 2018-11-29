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
        while ((len = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, len);
            if (request.cancel || request.status == HttpState.STOP) {
                break;
            } else {
                progress(curBytes, request.endPoint);
            }
        }
        in.close();
        out.flush();
        out.close();

        if (mRequest.status == HttpState.STOP) {
            EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
            return;
        }
        if (request.cancel) {
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
        if (mRequest.status == HttpState.STOP) {//拦截数据解析
            EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
            return;
        }
        String result = buffer.toString();
        EasyLog.d(TAG.EasyHttp, mUrlStr + " 完成，返回数据：" + result);
        if (mRequest.replaceHttpResultMap != null) {
            result = replaceStringBeforeParseResponse(result);
            EasyLog.d(TAG.EasyHttp, mUrlStr + " 格式化后数据：" + result);
        }
        success(result, null);
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        EasyLog.d(TAG.EasyHttp, mUrlStr + " 上传成功！");
        if (uploadListener != null) {
            HttpParseManager parseManager = new HttpParseManager();
            Object parseObject = parseManager.parseResponseFromJSONString(mResponse, result, headMap);
            if (parseObject == null) {
                fail("返回数据解析异常");
            } else {
                uploadListener.success(mRequest.flagCode, mRequest.flagStr, parseObject);
            }
        }
    }

    @Override
    protected void fail(String msg) {
        EasyLog.w(TAG.EasyHttp, mUrlStr + " " + msg);
        if (uploadListener != null) {
            uploadListener.fail(mRequest.flagCode, mRequest.flagStr, msg);
        }
    }

    private void progress(long currentPoint, long endPoint) {
        if (uploadListener != null) {
            uploadListener.progress(mRequest.flagCode, mRequest.flagStr, currentPoint, endPoint);
        }
    }
}