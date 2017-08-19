package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyFactory;
import com.jen.easy.EasyFinal;
import com.jen.easy.EasyMain;
import com.jen.easy.log.EasyLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class HttpURLConnectionRunable implements Runnable {
    private EasyFactory.HTTP.BaseParam param;

    HttpURLConnectionRunable(EasyFactory.HTTP.BaseParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.httpParam.url)) {
            EasyLog.e("URL地址错误");
            fail(EasyFinal.HTTP.Code.FAIL, "参数错误");
            return;
        }

        try {
            boolean hasParam = false;
            boolean isNotFirst = false;
            StringBuffer requestBuf = new StringBuffer("");
            for (String name : param.baseParam.requestParam.keySet()) {
                String value = param.baseParam.requestParam.get(name);
                if (isNotFirst) {
                    requestBuf.append("&");
                }
                requestBuf.append(name);
                requestBuf.append("=");
                requestBuf.append(URLEncoder.encode(value, param.httpParam.charset));
                isNotFirst = true;
                hasParam = true;
            }

            String urlStr = param.httpParam.url;
            if (param.httpParam.method.toUpperCase().equals("GET") && hasParam) {
                urlStr = urlStr + "?" + requestBuf.toString();
            }

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.httpParam.doInput);
            connection.setDoOutput(param.httpParam.doOutput);
            connection.setUseCaches(param.httpParam.useCaches);
            connection.setConnectTimeout(param.httpParam.timeout);
            connection.setReadTimeout(param.httpParam.readTimeout);
            connection.setRequestProperty("Charset", param.httpParam.charset);
            connection.setRequestProperty("Content-Type", param.httpParam.contentType);
            connection.setRequestProperty("Connection", param.httpParam.connection);
            connection.setRequestMethod(param.httpParam.method);

            if (param.httpParam.method.toUpperCase().equals("POST") && hasParam) {
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
        fail(EasyFinal.HTTP.Code.FAIL, "获取数据异常");
    }

    private void success(String result) {
        if (param.getBseListener() != null) {
            Object object = null;
            if (param.baseParam.parse) {
                object = EasyMain.HPARSE.parseJson(param.getClass(), result);
                if (object == null) {
                    fail(EasyFinal.HTTP.Code.FAIL, "数据异常");
                    return;
                }
            }
            param.getBseListener().success(param.baseParam.flagCode, param.baseParam.flag, object);
        }
    }

    private void fail(int code, String result) {
        if (param.getBseListener() != null)
            param.getBseListener().fail(param.baseParam.flagCode, param.baseParam.flag, code, result);
    }
}