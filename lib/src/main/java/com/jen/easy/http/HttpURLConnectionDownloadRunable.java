package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

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
import java.util.Set;

class HttpURLConnectionDownloadRunable implements Runnable {
    private final String TAG = "HttpDownload : ";
    private HttpDownloadPRequest request;

    HttpURLConnectionDownloadRunable(HttpDownloadPRequest param) {
        super();
        this.request = param;
    }

    @Override
    public void run() {
        Object[] method_url = HttpReflectManager.getUrl(request);
        if (method_url != null) {
            request.http.method = (String) method_url[0];
            request.http.url = (String) method_url[1];
        }
        if (TextUtils.isEmpty(request.http.url)) {
            EasyLibLog.w(TAG + request.http.url + " URL地址错误");
            fail("请求URL参数错误");
            return;
        } else if (TextUtils.isEmpty(request.flag.filePath)) {
            fail("文件地址不能为空");
            return;
        }

        File fileFolder = new File(request.flag.filePath.substring(0, request.flag.filePath.lastIndexOf("/")));
        if (!fileFolder.exists()) {
            boolean ret = fileFolder.mkdirs();
            if (!ret) {
                EasyLibLog.w(TAG + request.http.url + " 创建文件夹失败");
                fail("保存文件失败，请检查文件路径是否正确");
                return;
            }
        }
        if (request.flag.deleteOldFile) {
            File file = new File(request.flag.filePath);
            if (file.exists()) {
                boolean ret = file.delete();
                if (!ret) {
                    EasyLibLog.w(TAG + request.http.url + " 删除旧文件失败");
                    fail("删除旧文件失败，请检查文件路径是否正确");
                    return;
                }
            }
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

        if (request.flag.startPoit <= 1024 * 2) {
            request.flag.startPoit = 0;
        } else if (request.flag.startPoit > 1024 * 2) {
            request.flag.startPoit = request.flag.startPoit - 1024 * 2;
        }

        long curbytes = request.flag.startPoit;
        HttpURLConnection connection = null;
        RandomAccessFile randFile = null;
        InputStream inStream = null;

        Map<String, String> requestParams = HttpReflectManager.getRequestParams(request);
        String exception = null;
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
                requestBuf.append(URLEncoder.encode(value, charset));
                isNotFirst = true;
                hasParam = true;
            }

            String urlStr = request.http.url;
            if (request.http.method.toUpperCase().equals("GET") && hasParam) {
                urlStr = urlStr + "?" + requestBuf.toString();
            }

            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(request.http.doInput);
            connection.setDoOutput(request.http.doOutput);
            connection.setUseCaches(request.http.useCaches);
            connection.setConnectTimeout(request.http.timeout);
            connection.setReadTimeout(request.http.readTimeout);
            for (String key : request.http.propertys.keySet()) {//设置Property
                connection.setRequestProperty(key, request.http.propertys.get(key));
            }

            if (request.flag.isBreak && request.flag.endPoit > request.flag.startPoit + 100) {
                connection.setRequestProperty("Range", "bytes=" + request.flag.startPoit + "-" + request.flag.endPoit);
            }

            if (request.http.method.toUpperCase().equals("POST") && hasParam) {
                connection.connect();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(requestBuf.toString());
                out.flush();
                out.close();
            }

            resposeCode = connection.getResponseCode();
            EasyLibLog.d(TAG + "Http 请求地址：" + url.toString() + "  请求方法：" + request.http.method
                    + " Http请求返回码：" + resposeCode);
            if ((resposeCode == 200)) {
                request.flag.endPoit = connection.getContentLength();
                inStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                randFile = new RandomAccessFile(request.flag.filePath, "rwd");
                randFile.seek(request.flag.startPoit);
                int len = 0;
                while ((len = inStream.read(buffer)) != -1 && !request.flag.userCancel) {
                    randFile.write(buffer, 0, len);
                    curbytes += len;
                    if (!request.flag.userCancel) {
                        progress(curbytes, request.flag.endPoit);
                    } else {
                        break;
                    }
                }
            }
            if (request.flag.userCancel) {
                fail("用户取消下载");
                return;
            } else if (curbytes == request.flag.endPoit) {
                success();
                return;
            }
        } catch (MalformedURLException e) {
            EasyLibLog.e(TAG + request.http.url + " MalformedURLException error --------");
            e.printStackTrace();
            exception = "MalformedURLException error";
        } catch (ProtocolException e) {
            EasyLibLog.e(TAG + request.http.url + " ProtocolException error --------");
            e.printStackTrace();
            exception = "ProtocolException error";
        } catch (IOException e) {
            EasyLibLog.e(TAG + request.http.url + " IOException error --------");
            e.printStackTrace();
            exception = "IOException error";
        } catch (IllegalStateException e) {
            EasyLibLog.e(TAG + request.http.url + " IllegalStateException error --------");
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
        if (request.getDownloadListener() != null)
            request.getDownloadListener().success(request.flag.code, request.flag.str, request.flag.filePath);
    }

    private void fail(String msg) {
        if (request.getDownloadListener() != null)
            request.getDownloadListener().fail(request.flag.code, request.flag.str, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        if (request.getDownloadListener() != null)
            request.getDownloadListener().progress(request.flag.code, request.flag.str, currentPoint, endPoint);
    }
}