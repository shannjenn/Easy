package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.http.param.EasyHttpBaseParam;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class HttpURLConnectionRunable implements Runnable {
    private EasyHttpBaseParam param;

    HttpURLConnectionRunable(EasyHttpBaseParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.httpBase.url)) {
            HttpLog.e("URL地址错误");
            fail(EasyHttpCode.FAIL, "参数错误");
            return;
        }

        try {
            boolean hasParam = false;
            boolean isNotFirst = false;
            StringBuffer requestBuf = new StringBuffer("");
            for (String name : param.httpBase.requestParam.keySet()) {
                String value = param.httpBase.requestParam.get(name);
                if (isNotFirst) {
                    requestBuf.append("&");
                }
                requestBuf.append(name);
                requestBuf.append("=");
                requestBuf.append(URLEncoder.encode(value, param.httpBase.charset));
                isNotFirst = true;
                hasParam = true;
            }

            String urlStr = param.httpBase.url;
            if (param.httpBase.method.toUpperCase().equals("GET") && hasParam) {
                urlStr = urlStr + "?" + requestBuf.toString();
            }

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.httpBase.doInput);
            connection.setDoOutput(param.httpBase.doOutput);
            connection.setUseCaches(param.httpBase.useCaches);
            connection.setConnectTimeout(param.httpBase.timeout);
            connection.setReadTimeout(param.httpBase.readTimeout);
            connection.setRequestProperty("Charset", param.httpBase.charset);
            connection.setRequestProperty("Content-Type", param.httpBase.contentType);
            connection.setRequestProperty("Connection", param.httpBase.connection);
            connection.setRequestMethod(param.httpBase.method);

            if (param.httpBase.method.toUpperCase().equals("POST") && hasParam) {
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

    private void success(String result) {
        if (param.getEasyHttpListener() != null) {
            Object object = null;
            if (param.httpBase.parseJson) {
                object = HttpParse.parseJson(param.getClass(), result);
                if (object == null) {
                    fail(EasyHttpCode.FAIL, "数据异常");
                    return;
                }
            }
            param.getEasyHttpListener().success(param.httpBase.flagCode, param.httpBase.flag, object);
        }
    }

    private void fail(int easyHttpCode, String result) {
        if (param.getEasyHttpListener() != null)
            param.getEasyHttpListener().fail(param.httpBase.flagCode, param.httpBase.flag, easyHttpCode, result);
    }
}