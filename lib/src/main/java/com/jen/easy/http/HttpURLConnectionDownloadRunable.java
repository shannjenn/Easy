package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyFactory;
import com.jen.easy.EasyFinal;
import com.jen.easy.log.EasyLog;

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
import java.util.Map;

class HttpURLConnectionDownloadRunable implements Runnable {
    private EasyFactory.HTTP.DownloadParamRequest param;

    HttpURLConnectionDownloadRunable(EasyFactory.HTTP.DownloadParamRequest param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        int result = EasyFinal.HTTP.Code.FAIL;
        if (TextUtils.isEmpty(param.http.url)) {
            EasyLog.e("URL地址错误");
            fail(EasyFinal.HTTP.Code.FAIL, "参数错误");
            return;
        } else if (TextUtils.isEmpty(param.request.filePath)) {
            fail(EasyFinal.HTTP.Code.FAIL, "文件地址不能为空");
            return;
        }

        File fileFolder = new File(param.request.filePath.substring(0, param.request.filePath.lastIndexOf("/")));
        if (!fileFolder.exists()) {
            boolean ret = fileFolder.mkdirs();
            if (!ret)
                EasyLog.w("创建文件夹失败");
        }
        if (param.request.deleteOldFile) {
            File file = new File(param.request.filePath);
            if (file.exists()) {
                boolean ret = file.delete();
                if (!ret)
                    EasyLog.w("删除文件失败");
            }
        }

        if (param.request.startPoit <= 1024 * 2) {
            param.request.startPoit = 0;
        } else if (param.request.startPoit > 1024 * 2) {
            param.request.startPoit = param.request.startPoit - 1024 * 2;
        }

        long curbytes = param.request.startPoit;
        HttpURLConnection connection = null;
        RandomAccessFile randFile = null;
        InputStream inStream = null;

        Map<String, String> requestParams = HttpReflectManager.getParams(param);

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
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(param.http.doInput);
            connection.setDoOutput(param.http.doOutput);
            connection.setUseCaches(param.http.useCaches);
            connection.setConnectTimeout(param.http.timeout);
            connection.setReadTimeout(param.http.readTimeout);
            connection.setRequestProperty("Charset", param.http.charset);
            connection.setRequestProperty("Content-Type", param.http.contentType);
            connection.setRequestProperty("Connection", param.http.connection);
            connection.setRequestMethod(param.http.method);
            if (param.request.isBreak && param.request.endPoit > param.request.startPoit + 100) {
                connection.setRequestProperty("Range", "bytes=" + param.request.startPoit + "-" + param.request.endPoit);
            }

            if (param.http.method.toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            EasyLog.d("Http 请求地址：" + url.getPath() + "  " + param.http.method);
            if ((connection.getResponseCode() == 200)) {
                param.request.endPoit = connection.getContentLength();
                inStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                randFile = new RandomAccessFile(param.request.filePath, "rwd");
                randFile.seek(param.request.startPoit);
                int len = 0;
                while ((len = inStream.read(buffer)) != -1 && !param.request.userCancel) {
                    randFile.write(buffer, 0, len);
                    curbytes += len;
                    if (!param.request.userCancel) {
                        progress(curbytes, param.request.endPoit);
                    } else {
                        break;
                    }
                }
            }
            if (param.request.userCancel) {
                result = EasyFinal.HTTP.Code.USER_CANCEL;
            } else if (curbytes == param.request.endPoit) {
                result = EasyFinal.HTTP.Code.SUCCESS;
            }
        } catch (MalformedURLException e) {
            EasyLog.e("MalformedURLException error --------");
            e.printStackTrace();
        } catch (ProtocolException e) {
            EasyLog.e("ProtocolException error --------");
            e.printStackTrace();
        } catch (IOException e) {
            EasyLog.e("IOException error --------");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            EasyLog.e("IllegalStateException error --------");
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
            param.getDownloadListener().success(param.request.flagCode, param.request.flag);
    }

    private void fail(int code, String tag) {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().fail(param.request.flagCode, param.request.flag, code, tag);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().progress(param.request.flagCode, param.request.flag, currentPoint, endPoint);
    }
}