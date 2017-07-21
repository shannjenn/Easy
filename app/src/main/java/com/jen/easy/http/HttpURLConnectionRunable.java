package com.jen.easy.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpURLConnectionRunable implements Runnable {
    private final String TAG = "HttpURLConnectionRunable";
    private EasyHttpParam param;
    private EasyHttpListener easyHttpListener;

    HttpURLConnectionRunable(EasyHttpParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (param == null || param.url == null) {
            HttpLog.e("param or url is null");
            fail(EasyHttpCode.FAIL, "参数错误");
            return;
        }

        StringBuffer result = new StringBuffer("");
        try {
            URL url = new URL(param.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // connection.setDoInput(true);
            // connection.setDoOutput(true);
            //connection.setUseCaches(false);
            connection.setRequestProperty("Charset", param.charset);
            connection.setConnectTimeout(param.timeout);
            connection.setReadTimeout(param.timeout);
            connection.setRequestProperty("Content-Type", "text/html");
            connection.setRequestMethod("GET");
            //	connection.setRequestProperty("Range", "bytes=" + param.start + "-" + param.end);

            if ((connection.getResponseCode() == 200)) {
                InputStream inStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                String s = null;
                while ((s = reader.readLine()) != null) {
                    result.append(s);
                }
                reader.close();
                inStream.close();
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail(EasyHttpCode.FAIL, "读取流异常");
        }
        success(result);
    }

    EasyHttpParam getParam() {
        return param;
    }

    public void setEasyHttpListener(EasyHttpListener easyHttpListener) {
        this.easyHttpListener = easyHttpListener;
    }

    private void success(StringBuffer result) {
        if (easyHttpListener != null)
            easyHttpListener.success(param.flagCode, param.flag, result);
    }

    private void fail(int easyHttpCode, String tag) {
        if (easyHttpListener != null)
            easyHttpListener.fail(param.flagCode, param.flag, easyHttpCode, tag);
    }
}