package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyFinal;
import com.jen.easy.EasyFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

class HttpURLConnectionDownloadRunable implements Runnable {
    private EasyFactory.HTTP.DownloadParam param;

    HttpURLConnectionDownloadRunable(EasyFactory.HTTP.DownloadParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        int result = EasyFinal.HTTP.Code.FAIL;
        if (TextUtils.isEmpty(param.httpBase.url)) {
            HttpLog.e("URL地址错误");
            fail(EasyFinal.HTTP.Code.FAIL, "参数错误");
            return;
        } else if (TextUtils.isEmpty(param.fileParam.filePath)) {
            fail(EasyFinal.HTTP.Code.FAIL, "文件地址不能为空");
            return;
        }

        File fileFolder = new File(param.fileParam.filePath.substring(0, param.fileParam.filePath.lastIndexOf("/")));
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        if (param.fileParam.deleteOldFile) {
            File file = new File(param.fileParam.filePath);
            if (file.exists()) {
                file.delete();
            }
        }

        if (param.fileParam.startPoit <= 1024 * 2) {
            param.fileParam.startPoit = 0;
        } else if (param.fileParam.startPoit > 1024 * 2) {
            param.fileParam.startPoit = param.fileParam.startPoit - 1024 * 2;
        }

        long curbytes = param.fileParam.startPoit;
        HttpURLConnection connection = null;
        RandomAccessFile randFile = null;
        InputStream inStream = null;

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

            if (param.httpBase.method.toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            if ((connection.getResponseCode() == 200)) {
                param.fileParam.endPoit = connection.getContentLength();
                inStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                randFile = new RandomAccessFile(param.fileParam.filePath, "rwd");
                randFile.seek(param.fileParam.startPoit);
                int len = 0;
                while ((len = inStream.read(buffer)) != -1 && !param.httpBase.userCancel) {
                    randFile.write(buffer, 0, len);
                    curbytes += len;
                    if (!param.httpBase.userCancel) {
                        progress(curbytes, param.fileParam.endPoit);
                    } else {
                        break;
                    }
                }
            }
            if (param.httpBase.userCancel) {
                result = EasyFinal.HTTP.Code.USER_CANCEL;
            } else if (curbytes == param.fileParam.endPoit) {
                result = EasyFinal.HTTP.Code.SUCCESS;
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
        if (result == EasyFinal.HTTP.Code.SUCCESS) {
            success();
        } else {
            fail(result, result == EasyFinal.HTTP.Code.USER_CANCEL ? "用户取消" : "下载异常");
        }
    }

    private void success() {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().success(param.httpBase.flagCode, param.httpBase.flag);
    }

    private void fail(int code, String tag) {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().fail(param.httpBase.flagCode, param.httpBase.flag, code, tag);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().progress(param.httpBase.flagCode, param.httpBase.flag, currentPoint, endPoint);
    }
}