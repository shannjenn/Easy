package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.log.EasyLibLog;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpURLConnectionUploadRunable implements Runnable {
    private final String TAG = "HttpUpload : ";
    private HttpUploadRequest request;
    private Class responseClass;

    HttpURLConnectionUploadRunable(HttpUploadRequest param) {
        super();
        this.request = param;
    }

    @Override
    public void run() {
        Object[] method_url = HttpReflectManager.getUrl(request);
        if (method_url != null) {
            request.http.method = (String) method_url[0];
            request.http.url = (String) method_url[1];
            responseClass = (Class) method_url[2];
        }
        if (TextUtils.isEmpty(request.http.url)) {
            EasyLibLog.e(TAG + request.http.url + " URL地址错误");
            fail("URL参数错误");
            return;
        } else if (TextUtils.isEmpty(request.flag.filePath)) {
            fail("文件地址不能为空");
            return;
        }
        File file = new File(request.flag.filePath);
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
            if (request.flag.isBreak && request.flag.endPoit > request.flag.startPoit + 100) {
                connection.setRequestProperty("Range", "bytes=" + request.flag.startPoit + "-" + request.flag.endPoit);
            }
            EasyLibLog.d(TAG + "Http 请求地址：" + url + "  " + request.http.method);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            long curbytes = request.flag.startPoit;
            int len = 0;
            byte[] bufferOut = new byte[1024];
            while ((len = in.read(bufferOut)) != -1 && !request.flag.userCancel) {
                out.write(bufferOut, 0, len);
                if (request.flag.userCancel) {
                    break;
                } else {
                    progress(curbytes, request.flag.endPoit);
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
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), request.http.charset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            EasyLibLog.d(TAG + url + " 服务器完成，返回数据：" + buffer.toString());
            success(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + request.http.url + " 上传失败：IOException");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void success(String result) {
        if (request.getUploadListener() != null) {
            Object parseObject = HttpParseManager.parseJson(responseClass, result);
            if (parseObject == null) {
                fail("数据解析异常");
            } else {
                request.getUploadListener().success(request.flag.code, request.flag.str, parseObject);
            }
        }
    }

    private void fail(String msg) {
        if (request.getUploadListener() != null)
            request.getUploadListener().fail(request.flag.code, request.flag.str, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        if (request.getUploadListener() != null)
            request.getUploadListener().progress(request.flag.code, request.flag.str, currentPoint, endPoint);
    }
}