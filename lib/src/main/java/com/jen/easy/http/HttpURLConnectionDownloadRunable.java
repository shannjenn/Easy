package com.jen.easy.http;

import android.text.TextUtils;

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
    private final String TAG = HttpURLConnectionDownloadRunable.class.getSimpleName() + " : ";
    private HttpDownloadPRequest param;

    HttpURLConnectionDownloadRunable(HttpDownloadPRequest param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(param.http.url)) {
            EasyLog.w(TAG + "URL地址错误");
            fail("请求URL参数错误");
            return;
        } else if (TextUtils.isEmpty(param.request.filePath)) {
            fail("文件地址不能为空");
            return;
        }

        File fileFolder = new File(param.request.filePath.substring(0, param.request.filePath.lastIndexOf("/")));
        if (!fileFolder.exists()) {
            boolean ret = fileFolder.mkdirs();
            if (!ret)
                EasyLog.w(TAG + "创建文件夹失败");
            fail("保存文件失败，请检查是否有文件权限");
            return;
        }
        if (param.request.deleteOldFile) {
            File file = new File(param.request.filePath);
            if (file.exists()) {
                boolean ret = file.delete();
                if (!ret) {
                    EasyLog.w(TAG + "删除旧文件失败");
                    fail("删除旧文件失败，请检查是否有文件权限");
                    return;
                }
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

        Map<String, String> requestParams = HttpReflectManager.getRequestParams(param);
        String exception = null;
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

            EasyLog.d("Http 请求地址：" + url.toString() + "  " + param.http.method);
            resposeCode = connection.getResponseCode();
            EasyLog.d("Http请求返回码：" + resposeCode);
            if ((resposeCode == 200)) {
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
                fail("用户取消下载");
                return;
            } else if (curbytes == param.request.endPoit) {
                success();
                return;
            }
        } catch (MalformedURLException e) {
            EasyLog.e(TAG + "MalformedURLException error --------");
            e.printStackTrace();
            exception = "MalformedURLException error";
        } catch (ProtocolException e) {
            EasyLog.e(TAG + "ProtocolException error --------");
            e.printStackTrace();
            exception = "ProtocolException error";
        } catch (IOException e) {
            EasyLog.e(TAG + "IOException error --------");
            e.printStackTrace();
            exception = "IOException error";
        } catch (IllegalStateException e) {
            EasyLog.e(TAG + "IllegalStateException error --------");
            e.printStackTrace();
            exception = "IllegalStateException error";
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
        fail("下载异常:" + exception);
    }

    private void success() {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().success(param.request.flagCode, param.request.flag);
    }

    private void fail(String msg) {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().fail(param.request.flagCode, param.request.flag, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        if (param.getDownloadListener() != null)
            param.getDownloadListener().progress(param.request.flagCode, param.request.flag, currentPoint, endPoint);
    }
}