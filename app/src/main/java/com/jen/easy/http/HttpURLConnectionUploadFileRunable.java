package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.http.param.EasyHttpUploadFileParam;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpURLConnectionUploadFileRunable implements Runnable {
    private EasyHttpUploadFileParam param;

    HttpURLConnectionUploadFileRunable(EasyHttpUploadFileParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        int result = EasyHttpCode.FAIL;
        if (TextUtils.isEmpty(param.getUrl())) {
            HttpLog.e("URL地址错误");
            fail(EasyHttpCode.FAIL, "参数错误");
            return;
        }
        File file = new File(param.getFilePath());
        if (!file.isFile()) {
            fail(EasyHttpCode.FAIL, "文件地址参数错误");
            return;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(param.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.isDoInput());
            connection.setDoOutput(param.isDoOutput());
            connection.setUseCaches(param.isUseCaches());
            connection.setConnectTimeout(param.getTimeout());
            connection.setReadTimeout(param.getReadTimeout());
            connection.setRequestProperty("Charset", param.getCharset());
            connection.setRequestProperty("Content-Type", "multipart/form-data;file=" + param.getContentType());
            connection.setRequestProperty("Connection", param.getConnection());
            connection.setRequestMethod(param.getMethod());
            if (param.isBreak() && param.getEndPoit() > param.getStartPoit() + 100) {
                connection.setRequestProperty("Range", "bytes=" + param.getStartPoit() + "-" + param.getEndPoit());
            }

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1 && !param.isUserCancel()) {
                out.write(bufferOut, 0, bytes);
                if (param.isUserCancel()) {
                    break;
                }
            }
            in.close();
            out.flush();
            out.close();

            if (param.isUserCancel()) {
                fail(EasyHttpCode.FAIL, "用户取消");
                return;
            }

            // 读取返回数据
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            reader = null;
            success(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void success(String result) {
        if (param.getEasyHttpUploadFileListener() != null) {
            Object object = null;
            if (param.isParseJson()) {
                object = HttpParse.parseJson(param.getClass(), result);
                if (object == null) {
                    fail(EasyHttpCode.FAIL, "数据异常");
                    return;
                }
            }
            param.getEasyHttpUploadFileListener().success(param.getFlagCode(), param.getFlag(), object);
        }
    }

    private void fail(int easyHttpCode, String tag) {
        if (param.getEasyHttpUploadFileListener() != null)
            param.getEasyHttpUploadFileListener().fail(param.getFlagCode(), param.getFlag(), easyHttpCode, tag);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getEasyHttpUploadFileListener() != null)
            param.getEasyHttpUploadFileListener().progress(param.getFlagCode(), param.getFlag(), currentPoint, endPoint);
    }
}