package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.http.param.EasyHttpParam;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class HttpURLConnectionRunable implements Runnable {
    private EasyHttpParam param;

    HttpURLConnectionRunable(EasyHttpParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.getUrl())) {
            HttpLog.e("URL地址错误");
            fail(EasyHttpCode.FAIL, "参数错误");
            return;
        }

        try {
            boolean hasParam = false;
            boolean isNotFirst = false;
            StringBuffer requestBuf = new StringBuffer("");
            for (String name : param.getRequestParam().keySet()) {
                String value = param.getRequestParam().get(name);
                if (isNotFirst) {
                    requestBuf.append("&");
                }
                requestBuf.append(name);
                requestBuf.append("=");
                requestBuf.append(URLEncoder.encode(value, param.getCharset()));
                isNotFirst = true;
                hasParam =true;
            }

            String urlStr = param.getUrl();
            if (param.getMethod().toUpperCase().equals("GET") && hasParam) {
                urlStr = urlStr + "?" + requestBuf.toString();
            }

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.isDoInput());
            connection.setDoOutput(param.isDoOutput());
            connection.setUseCaches(param.isUseCaches());
            connection.setConnectTimeout(param.getTimeout());
            connection.setReadTimeout(param.getReadTimeout());
            connection.setRequestProperty("Charset", param.getCharset());
            connection.setRequestProperty("Content-Type", param.getContentType());
            connection.setRequestProperty("Connection", param.getConnection());
            connection.setRequestMethod(param.getMethod());

            if (param.getMethod().toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            if ((connection.getResponseCode() == 200)) {
                StringBuffer result = new StringBuffer("");
                InputStream inStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                String s = null;
                while ((s = reader.readLine()) != null) {
                    result.append(s);
                }
                reader.close();
                inStream.close();
                connection.disconnect();
                success(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fail(EasyHttpCode.FAIL, "获取数据异常");
    }

    EasyHttpParam getParam() {
        return param;
    }

    private void success(String result) {
        if (param.getEasyHttpListener() != null) {
            Object object = null;
            if (param.isParseJson()) {
                object = HttpParse.parseJson(param.getClass(), result);
                if (object == null) {
                    fail(EasyHttpCode.FAIL, "数据异常");
                    return;
                }
            }
            param.getEasyHttpListener().success(param.getFlagCode(), param.getFlag(), object);
        }
    }

    private void fail(int easyHttpCode, String result) {
        if (param.getEasyHttpListener() != null)
            param.getEasyHttpListener().fail(param.getFlagCode(), param.getFlag(), easyHttpCode, result);
    }
}