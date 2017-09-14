package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.log.EasyLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

class HttpURLConnectionRunable implements Runnable {
    private final String TAG = HttpURLConnectionDownloadRunable.class.getSimpleName() + " : ";
    private HttpBaseRequest request;

    HttpURLConnectionRunable(HttpBaseRequest param) {
        super();
        this.request = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(request.http.url)) {
            EasyLog.e(TAG + "URL地址错误");
            fail("URL地址为空");
            return;
        }
        Map<String, String> requestParams = HttpReflectManager.getRequestParams(request);
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
                requestBuf.append("\"");
                requestBuf.append(URLEncoder.encode(value, request.http.charset));
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
            connection.setRequestProperty("Charset", request.http.charset);
            connection.setRequestProperty("Content-Type", request.http.contentType);
            connection.setRequestProperty("Connection", request.http.connection);
            connection.setRequestMethod(request.http.method);

            if (request.http.method.toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            if (request.http.method.toUpperCase().equals("GET")) {
                EasyLog.d("Http请求地址：" + url.toString() + "  请求方式：" + request.http.method);
            } else {
                EasyLog.d("Http请求地址：" + url.toString() + "  请求方式：" + request.http.method);
                EasyLog.d("Http请求参数：" + requestBuf.toString());
            }
            resposeCode = connection.getResponseCode();
            EasyLog.d("Http请求返回码：" + resposeCode);
            if ((resposeCode == 200)) {
                StringBuffer result = new StringBuffer("");
                InputStream inStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, request.http.charset));
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
        fail("网络请求异常：" + resposeCode);
    }

    private void success(String result) {
        if (request.getBseListener() != null) {
            Type type = request.getClass().getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                EasyLog.e(TAG + "请求参数未指定泛型返回类型");
                fail("请求参数未指定返回类型");
                return;
            }
            Type classType = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (!(classType instanceof Class)) {
                EasyLog.e(TAG + classType + "泛型不是Class类型");
                fail(classType + "不是Class类型");
                return;
            }
            HttpResponse response;
            try {
                Class clazz = Class.forName(((Class) classType).getName());
                Object object = clazz.newInstance();
                if (!(object instanceof HttpResponse)) {
                    EasyLog.e(TAG + classType + "泛型不是HttpResponse类型");
                    fail(classType + "HttpResponse类型");
                    return;
                }
                response = (HttpResponse) object;
            } catch (InstantiationException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "InstantiationException");
                fail("不存在泛型：" + ((Class) classType).getName());
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "IllegalAccessException");
                fail("不存在泛型：" + ((Class) classType).getName());
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "ClassNotFoundException");
                fail("不存在泛型：" + ((Class) classType).getName());
                return;
            }

            response.objClass = request.ResponseObjClass;
            Object parseObject = HttpParseManager.parseJson(response, result);
            if (parseObject == null) {
                fail("数据解析异常");
            } else {
                request.getBseListener().success(request.request.flagCode, request.request.flag, response);
            }
        }
    }

    private void fail(String result) {
        if (request.getBseListener() != null)
            request.getBseListener().fail(request.request.flagCode, request.request.flag, result);
    }
}