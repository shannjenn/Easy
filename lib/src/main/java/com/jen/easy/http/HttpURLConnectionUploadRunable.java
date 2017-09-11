package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.log.EasyLog;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpURLConnectionUploadRunable implements Runnable {
    private final String TAG = HttpURLConnectionUploadRunable.class.getSimpleName() + " : ";
    private HttpUploadRequest request;
    private HttpResponse response;

    HttpURLConnectionUploadRunable(HttpUploadRequest param) {
        super();
        this.request = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(request.http.url)) {
            EasyLog.e(TAG + "URL地址错误");
            fail("URL参数错误");
            return;
        } else if (TextUtils.isEmpty(request.request.filePath)) {
            fail("文件地址不能为空");
            return;
        }
        File file = new File(request.request.filePath);
        if (!file.isFile()) {
            fail("文件地址参数错误");
            return;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(request.http.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(request.http.doInput);
            connection.setDoOutput(request.http.doOutput);
            connection.setUseCaches(request.http.useCaches);
            connection.setConnectTimeout(request.http.timeout);
            connection.setReadTimeout(request.http.readTimeout);
            connection.setRequestProperty("Charset", request.http.charset);
            connection.setRequestProperty("Content-Type", request.http.contentType);
            connection.setRequestProperty("Connection", request.http.connection);
            connection.setRequestMethod(request.http.method);
            if (request.request.isBreak && request.request.endPoit > request.request.startPoit + 100) {
                connection.setRequestProperty("Range", "bytes=" + request.request.startPoit + "-" + request.request.endPoit);
            }
            EasyLog.d("Http 请求地址：" + url.toString() + "  " + request.http.method);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            long curbytes = request.request.startPoit;
            int len = 0;
            byte[] bufferOut = new byte[1024];
            while ((len = in.read(bufferOut)) != -1 && !request.request.userCancel) {
                out.write(bufferOut, 0, len);
                if (request.request.userCancel) {
                    break;
                } else {
                    progress(curbytes, request.request.endPoit);
                }
            }
            in.close();
            out.flush();
            out.close();

            if (request.request.userCancel) {
                fail("用户取消上传");
                return;
            }

            // 读取返回数据
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), request.http.charset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            EasyLog.d("服务器完成，返回数据：" + buffer.toString());
            success(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.e("上传失败：IOException");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void success(String result) {
        if (request.getUploadListener() != null) {
            if (response != null) {
                HttpResponse object = HttpParseManager.parseJson(response, result);
                if (object == null) {
                    fail("数据j解析异常");
                } else {
                    request.getUploadListener().success(request.request.flagCode, request.request.flag, object);
                }
            } else {
//                request.getUploadListener().success(request.request.flagCode, request.request.flag, result);
            }
        }
    }

    private void fail(String msg) {
        if (request.getUploadListener() != null)
            request.getUploadListener().fail(request.request.flagCode, request.request.flag, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        if (request.getUploadListener() != null)
            request.getUploadListener().progress(request.request.flagCode, request.request.flag, currentPoint, endPoint);
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}