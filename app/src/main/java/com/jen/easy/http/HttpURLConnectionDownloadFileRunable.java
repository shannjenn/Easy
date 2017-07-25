package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.http.param.EasyHttpDownloadFileParam;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

class HttpURLConnectionDownloadFileRunable implements Runnable {
    private EasyHttpDownloadFileParam param;

    HttpURLConnectionDownloadFileRunable(EasyHttpDownloadFileParam param) {
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

        if (param.getStartPoit() <= 1024 * 2) {
            param.setStartPoit(0);
        } else if (param.getStartPoit() > 1024 * 2) {
            param.setStartPoit(param.getStartPoit() - 1024 * 2);
        }

        long curbytes = param.getStartPoit();
        HttpURLConnection connection = null;
        RandomAccessFile randFile = null;
        InputStream inStream = null;

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
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.isDoInput());
            connection.setDoOutput(param.isDoOutput());
            connection.setUseCaches(param.isUseCaches());
            connection.setConnectTimeout(param.getTimeout());
            connection.setReadTimeout(param.getReadTimeout());
            connection.setRequestProperty("Charset", param.getCharset());
            connection.setRequestProperty("Content-Type", param.getContentType());
            connection.setRequestProperty("Connection", param.getConnection());
            connection.setRequestMethod(param.getMethod());
            if (param.isBreak() && param.getEndPoit() > param.getStartPoit() + 100) {
                connection.setRequestProperty("Range", "bytes=" + param.getStartPoit() + "-" + param.getEndPoit());
            }

            if (param.getMethod().toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            if ((connection.getResponseCode() == 200)) {
                param.setEndPoit(connection.getContentLength());
                inStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                randFile = new RandomAccessFile(param.getFilePath(), "rwd");
                randFile.seek(param.getStartPoit());
                int len = 0;
                while ((len = inStream.read(buffer)) != -1 && !param.isUserCancel()) {
                    randFile.write(buffer, 0, len);
                    curbytes += len;
                    if (!param.isUserCancel()) {
                        progress(curbytes, param.getEndPoit());
                    } else {
                        break;
                    }
                }
            }
            if (param.isUserCancel()) {
                result = EasyHttpCode.USER_CANCEL;
            } else if (curbytes == param.getEndPoit()) {
                result = EasyHttpCode.SUCCESS;
            }
        } catch (MalformedURLException e) {
            HttpLog.e("MalformedURLException error --------");
            e.printStackTrace();
        } catch (ProtocolException e) {
            HttpLog.e("ProtocolException error --------");
            e.printStackTrace();
        } catch (IOException e) {
            HttpLog.e("IOException error --------");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            HttpLog.e("IllegalStateException error --------");
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (null != randFile)
                    randFile.close();
                if (null != inStream)
                    inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (result == EasyHttpCode.SUCCESS) {
            success();
        } else {
            fail(result, result == EasyHttpCode.USER_CANCEL ? "用户取消" : "下载异常");
        }
    }

    private void success() {
        if (param.getEasyHttpDownloadFileListener() != null)
            param.getEasyHttpDownloadFileListener().success(param.getFlagCode(), param.getFlag());
    }

    private void fail(int easyHttpCode, String tag) {
        if (param.getEasyHttpDownloadFileListener() != null)
            param.getEasyHttpDownloadFileListener().fail(param.getFlagCode(), param.getFlag(), easyHttpCode, tag);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getEasyHttpDownloadFileListener() != null)
            param.getEasyHttpDownloadFileListener().progress(param.getFlagCode(), param.getFlag(), currentPoint, endPoint);
    }
}