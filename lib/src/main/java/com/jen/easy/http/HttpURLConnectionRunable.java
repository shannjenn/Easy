package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

class HttpURLConnectionRunable implements Runnable {
    private final String TAG = "HttpBase : ";
    private HttpBaseRequest request;
    private Class responseClass;

    HttpURLConnectionRunable(HttpBaseRequest param) {
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
            fail("URL地址为空");
            return;
        }

        //设置基本Property参数
        String CHARSET_KEY = "Charset";
        String charset = request.http.propertys.get(CHARSET_KEY);
        if (TextUtils.isEmpty(charset)){
            charset = Constant.Unicode.DEFAULT;
            request.http.propertys.put(CHARSET_KEY, charset);
        }
        String CONTENT_TYPE_KEY = "Content-Type";
        String contentType = request.http.propertys.get(CONTENT_TYPE_KEY);
        if (TextUtils.isEmpty(contentType)){
            contentType = "text/html";
            request.http.propertys.put(CONTENT_TYPE_KEY, contentType);
        }
        String CONNECTION_KEY = "Connection";
        String connectionType = request.http.propertys.get(CONNECTION_KEY);
        if (TextUtils.isEmpty(connectionType)){
            connectionType = "Keep-Alive";
            request.http.propertys.put(CONNECTION_KEY, connectionType);
        }

        Map<String, String> requestParams = HttpReflectManager.getRequestParams(request);
        int resposeCode = -1;
        try {
            boolean hasParam = false;
            boolean isNotFirst = false;
            StringBuffer requestBuf = new StringBuffer("");
            Set<String> sets = requestParams.keySet();
            for (String name : sets) {
                String value = requestParams.get(name);
                if (isNotFirst) {
                    requestBuf.append("&");
                }
                requestBuf.append(name);
                requestBuf.append("=");
                requestBuf.append("\"");
                requestBuf.append(URLEncoder.encode(value, charset));
                requestBuf.append("\"");
                isNotFirst = true;
                hasParam = true;
            }

            String urlStr = request.http.url;
            if (request.http.method.toUpperCase().equals("GET") && hasParam) {
                urlStr = urlStr + "?" + requestBuf.toString();
            }

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(request.http.doInput);
            connection.setDoOutput(request.http.doOutput);
            connection.setUseCaches(request.http.useCaches);
            connection.setConnectTimeout(request.http.timeout);
            connection.setReadTimeout(request.http.readTimeout);
            connection.setRequestMethod(request.http.method);
            for (String key : request.http.propertys.keySet()) {//设置Property
                connection.setRequestProperty(key, request.http.propertys.get(key));
            }

            if (request.http.method.toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            if (request.http.method.toUpperCase().equals("GET")) {
                EasyLibLog.d(TAG + "Http请求地址：" + url + "  请求方式：" + request.http.method);
            } else {
                EasyLibLog.d(TAG + "Http请求地址：" + url + "  请求方式：" + request.http.method
                        + " 请求参数：" + requestBuf.toString());
            }
            resposeCode = connection.getResponseCode();
            EasyLibLog.d(TAG + url + "  Http请求返回码：" + resposeCode);
            if ((resposeCode == 200)) {
                StringBuffer result = new StringBuffer("");
                InputStream inStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, charset));
                String s = null;
                while ((s = reader.readLine()) != null) {
                    result.append(s);
                }
                reader.close();
                inStream.close();
                connection.disconnect();
                EasyLibLog.d(TAG + url + " 返回数据：" + result.toString());
                success(result.toString());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + request.http.url + " IOException");
        }
        fail(" 网络请求异常：" + resposeCode);
    }

    private void success(String result) {
        if (request.getBseListener() != null) {
            HttpParseManager parseManager = new HttpParseManager();
            parseManager.setResponseObjectType(request.responseObjectType);
            Object parseObject = parseManager.parseJson(responseClass, result);
            if (parseObject == null) {
                fail("数据解析异常");
            } else {
                request.getBseListener().success(request.flag.code, request.flag.str, parseObject);
            }
        }
    }

    private void fail(String result) {
        if (request.getBseListener() != null)
            request.getBseListener().fail(request.flag.code, request.flag.str, result);
    }
}