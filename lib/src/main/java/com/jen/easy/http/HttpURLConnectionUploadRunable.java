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

    HttpURLConnectionUploadRunable(HttpUploadRequest param) {
        super();
        this.request = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(request.http.url)) {
            EasyLibLog.e(TAG + request.http.url + " URL地址错误");
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
            EasyLibLog.d(TAG + "Http 请求地址：" + url + "  " + request.http.method);

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
            Type type = request.getClass().getGenericSuperclass();
            if (!(type instanceof ParameterizedType)) {
                EasyLibLog.e(TAG + request.http.url + " 请求参数未指定泛型返回类型");
                fail("请求参数未指定返回类型");
                return;
            }
            Type classType = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (!(classType instanceof Class)) {
                EasyLibLog.e(TAG + request.http.url + classType + " 泛型不是Class类型");
                fail(classType + "不是Class类型");
                return;
            }
            HttpResponse response;
            try {
                Class clazz = Class.forName(((Class) classType).getName());
                Object object = clazz.newInstance();
                if (!(object instanceof HttpResponse)) {
                    EasyLibLog.e(TAG + request.http.url + classType + " 泛型不是HttpResponse类型");
                    fail(classType + "HttpResponse类型");
                    return;
                }
                response = (HttpResponse) object;
            } catch (InstantiationException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + request.http.url + " InstantiationException");
                fail("不存在泛型：" + ((Class) classType).getName());
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + request.http.url + " IllegalAccessException");
                fail("不存在泛型：" + ((Class) classType).getName());
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + request.http.url + " ClassNotFoundException");
                fail("不存在泛型：" + ((Class) classType).getName());
                return;
            }

            response.objClass = request.ResponseObjClass;
            Object parseObject = HttpParseManager.parseJson(response, result);
            if (parseObject == null) {
                fail("数据解析异常");
            } else {
                request.getUploadListener().success(request.request.flagCode, request.request.flag, response);
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
}