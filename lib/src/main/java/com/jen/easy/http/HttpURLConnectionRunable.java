package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.log.EasyLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

class HttpURLConnectionRunable implements Runnable {
    private final String TAG = "HttpURLConnectionDownloadRunable : ";
    private BaseParamRequest param;

    HttpURLConnectionRunable(BaseParamRequest param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.http.url)) {
            EasyLog.e(TAG + "URL地址错误");
            fail("URL地址为空");
            return;
        }
        Map<String, String> requestParams = HttpReflectManager.getRequestParams(param);
        int resposeCode = -1;
        try {
            boolean hasParam = false;
            boolean isNotFirst = false;
            StringBuffer requestBuf = new StringBuffer("");
            for (String name : requestParams.keySet()) {
                String value = requestParams.get(name);
                if (isNotFirst) {
                    requestBuf.append("&");
                }
                requestBuf.append(name);
                requestBuf.append("=");
                requestBuf.append(URLEncoder.encode(value, param.http.charset));
                isNotFirst = true;
                hasParam = true;
            }

            String urlStr = param.http.url;
            if (param.http.method.toUpperCase().equals("GET") && hasParam) {
                urlStr = urlStr + "?" + requestBuf.toString();
            }

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.http.doInput);
            connection.setDoOutput(param.http.doOutput);
            connection.setUseCaches(param.http.useCaches);
            connection.setConnectTimeout(param.http.timeout);
            connection.setReadTimeout(param.http.readTimeout);
            connection.setRequestProperty("Charset", param.http.charset);
            connection.setRequestProperty("Content-Type", param.http.contentType);
            connection.setRequestProperty("Connection", param.http.connection);
            connection.setRequestMethod(param.http.method);

            if (param.http.method.toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            EasyLog.d("Http 请求地址：" + url.getPath() + "  " + param.http.method);
            resposeCode = connection.getResponseCode();
            if ((resposeCode == 200)) {
                StringBuffer result = new StringBuffer("");
                InputStream inStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, param.http.charset));
                String s = null;
                while ((s = reader.readLine()) != null) {
                    result.append(s);
                }
                reader.close();
                inStream.close();
                connection.disconnect();
                EasyLog.d(result.toString());
                success(result.toString());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "IOException");
        }
        fail("获取数据异常：" + resposeCode);
    }

    private void success(String result) {
        if (param.getBseListener() != null) {
            if (param.request.resopseBaseClass != null) {
                Object object = HttpParseManager.parseJson(param.request.resopseBaseClass, param.request.resopseBaseClassObject, result);
                if (object == null) {
                    fail("数据解析异常");
                } else {
                    param.getBseListener().success(param.request.flagCode, param.request.flag, object);
                }
            } else {
                param.getBseListener().success(param.request.flagCode, param.request.flag, result);
            }
        }
    }

    private void fail(String result) {
        if (param.getBseListener() != null)
            param.getBseListener().fail(param.request.flagCode, param.request.flag, result);
    }
}