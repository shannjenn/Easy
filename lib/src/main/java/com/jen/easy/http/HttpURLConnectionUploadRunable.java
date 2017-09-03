package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyFactory;
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
    private final String TAG = "HttpURLConnectionUploadRunable : ";
    private EasyFactory.HTTP.UploadParamRequest param;

    HttpURLConnectionUploadRunable(EasyFactory.HTTP.UploadParamRequest param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.http.url)) {
            EasyLog.e(TAG + "URL地址错误");
            fail("URL参数错误");
            return;
        } else if (TextUtils.isEmpty(param.request.filePath)) {
            fail("文件地址不能为空");
            return;
        }
        File file = new File(param.request.filePath);
        if (!file.isFile()) {
            fail("文件地址参数错误");
            return;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(param.http.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.http.doInput);
            connection.setDoOutput(param.http.doOutput);
            connection.setUseCaches(param.http.useCaches);
            connection.setConnectTimeout(param.http.timeout);
            connection.setReadTimeout(param.http.readTimeout);
            connection.setRequestProperty("Charset", param.http.charset);
            connection.setRequestProperty("Content-Type", param.http.contentType);
            connection.setRequestProperty("Connection", param.http.connection);
            connection.setRequestMethod(param.http.method);
            if (param.request.isBreak && param.request.endPoit > param.request.startPoit + 100) {
                connection.setRequestProperty("Range", "bytes=" + param.request.startPoit + "-" + param.request.endPoit);
            }
            EasyLog.d("Http 请求地址：" + url.getPath() + "  " + param.http.method);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1 && !param.request.userCancel) {
                out.write(bufferOut, 0, bytes);
                if (param.request.userCancel) {
                    break;
                }
            }
            in.close();
            out.flush();
            out.close();

            if (param.request.userCancel) {
                fail("用户取消上传");
                return;
            }

            // 读取返回数据
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), param.http.charset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            EasyLog.d(buffer.toString());
            success(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "IOException");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void success(String result) {
        if (param.getUploadListener() != null) {
            if (param.request.resopseBaseClass != null) {
                Object object = HttpParseManager.parseJson(param.request.resopseBaseClass, param.request.resopseBaseClassObject, result);
                if (object == null) {
                    fail("数据j解析异常");
                } else {
                    param.getUploadListener().success(param.request.flagCode, param.request.flag, object);
                }
            } else {
                param.getUploadListener().success(param.request.flagCode, param.request.flag, result);
            }
        }
    }

    private void fail(String msg) {
        if (param.getUploadListener() != null)
            param.getUploadListener().fail(param.request.flagCode, param.request.flag, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getUploadListener() != null)
            param.getUploadListener().progress(param.request.flagCode, param.request.flag, currentPoint, endPoint);
    }
}