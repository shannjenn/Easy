package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyMain;
import com.jen.easy.EasyFinal;
import com.jen.easy.EasyFactory;

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
    private EasyFactory.HTTP.UploadParam param;

    HttpURLConnectionUploadRunable(EasyFactory.HTTP.UploadParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.httpBase.url)) {
            HttpLog.e("URL地址错误");
            fail(EasyFinal.HTTP.Code.FAIL, "参数错误");
            return;
        } else if (TextUtils.isEmpty(param.fileParam.filePath)) {
            fail(EasyFinal.HTTP.Code.FAIL, "文件地址不能为空");
            return;
        }
        File file = new File(param.fileParam.filePath);
        if (!file.isFile()) {
            fail(EasyFinal.HTTP.Code.FAIL, "文件地址参数错误");
            return;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(param.httpBase.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.httpBase.doInput);
            connection.setDoOutput(param.httpBase.doOutput);
            connection.setUseCaches(param.httpBase.useCaches);
            connection.setConnectTimeout(param.httpBase.timeout);
            connection.setReadTimeout(param.httpBase.readTimeout);
            connection.setRequestProperty("Charset", param.httpBase.charset);
            connection.setRequestProperty("Content-Type", param.httpBase.contentType);
            connection.setRequestProperty("Connection", param.httpBase.connection);
            connection.setRequestMethod(param.httpBase.method);
            if (param.fileParam.isBreak && param.fileParam.endPoit > param.fileParam.startPoit + 100) {
                connection.setRequestProperty("Range", "bytes=" + param.fileParam.startPoit + "-" + param.fileParam.endPoit);
            }

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1 && !param.httpBase.userCancel) {
                out.write(bufferOut, 0, bytes);
                if (param.httpBase.userCancel) {
                    break;
                }
            }
            in.close();
            out.flush();
            out.close();

            if (param.httpBase.userCancel) {
                fail(EasyFinal.HTTP.Code.FAIL, "用户取消");
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
        if (param.getUploadListener() != null) {
            Object object = null;
            if (param.httpBase.parseJson) {
                object = EasyMain.HPARSE.parseJson(param.getClass(), result);
                if (object == null) {
                    fail(EasyFinal.HTTP.Code.FAIL, "数据异常");
                    return;
                }
            }
            param.getUploadListener().success(param.httpBase.flagCode, param.httpBase.flag, object);
        }
    }

    private void fail(int easyHttpCode, String tag) {
        if (param.getUploadListener() != null)
            param.getUploadListener().fail(param.httpBase.flagCode, param.httpBase.flag, easyHttpCode, tag);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getUploadListener() != null)
            param.getUploadListener().progress(param.httpBase.flagCode, param.httpBase.flag, currentPoint, endPoint);
    }
}