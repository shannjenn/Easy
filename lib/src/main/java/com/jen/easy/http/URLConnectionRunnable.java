package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class URLConnectionRunnable implements Runnable {
    int flagCode;//请求标识
    String flagStr;//请求标识
    HttpRequest mRequest;
    Class mResponse;

    String mUrlStr;
    int mResponseCode = -1;//返回码
    String mCharset;//编码
    boolean mIsGet = true;
    JSONObject mBody;

    URLConnectionRunnable(HttpRequest param, int flagCode, String flagStr) {
        super();
        this.mRequest = param;
        this.flagCode = flagCode;
        this.flagStr = flagStr;
    }

    @Override
    public void run() {
        HttpReflectManager.RequestType requestType = HttpReflectManager.getRequestType(mRequest);
        if (requestType == null) {
            HttpLog.exception(ExceptionType.RuntimeException, "请求参数未加注释");
            fail("请求参数未加注释");
            return;
        }
        String method = requestType.method;
//        mRequest.url = requestType.url;
        Class response = requestType.response;
        mUrlStr = requestType.url;
        if (TextUtils.isEmpty(mUrlStr)) {
            mUrlStr = "";
            HttpLog.exception(ExceptionType.NullPointerException, "URL请求地址空");
            fail("URL地址为空");
            return;
        }

        if (mRequest instanceof HttpBaseRequest) {//===============基本数据处理
            mResponse = response;
        } else if (mRequest instanceof HttpUploadRequest) {//===============上传请求处理
            mResponse = response;
            HttpUploadRequest uploadRequest = (HttpUploadRequest) mRequest;
            if (TextUtils.isEmpty(uploadRequest.filePath)) {
                HttpLog.exception(ExceptionType.NullPointerException, "文件地址不能为空");
                fail("文件地址不能为空");
                return;
            }
            File file = new File(uploadRequest.filePath);
            if (!file.isFile()) {
                HttpLog.exception(ExceptionType.IllegalArgumentException, "文件地址参数错误");
                fail("文件地址参数错误");
                return;
            }
        } else if (mRequest instanceof HttpDownloadRequest) {//===============下载请求处理
            HttpDownloadRequest downloadRequest = (HttpDownloadRequest) mRequest;
            if (downloadRequest.deleteOldFile) {
                File file = new File(downloadRequest.filePath);
                if (file.exists()) {
                    boolean ret = file.delete();
                    if (!ret) {
                        HttpLog.exception(ExceptionType.IllegalArgumentException, "删除旧文件失败，请检查文件路径是否正确");
                        fail("删除旧文件失败，请检查文件路径是否正确");
                        return;
                    }
                }
            }

            if (TextUtils.isEmpty(downloadRequest.filePath)) {
                HttpLog.exception(ExceptionType.NullPointerException, "文件地址不能为空");
                fail("文件地址不能为空");
                return;
            }

            File fileFolder = new File(downloadRequest.filePath.substring(0, downloadRequest.filePath.lastIndexOf("/")));
            if (!fileFolder.exists()) {
                boolean ret = fileFolder.mkdirs();
                if (!ret) {
                    HttpLog.exception(ExceptionType.IllegalArgumentException, "保存文件失败，请检查文件路径是否正确");
                    fail("保存文件失败，请检查文件路径是否正确");
                    return;
                }
            }
        }

        mResponseCode = -1;//返回码
//        mHasParam = false;//是否有参数
        mCharset = mRequest.charset;//编码
        mIsGet = method.toUpperCase().equals("GET");
        try {
            HttpReflectManager.RequestObject requestObject = HttpReflectManager.getRequestHeadAndBody(mRequest);
            Map<String, String> urls = requestObject.urls;
            Map<String, String> heads = requestObject.heads;
            mBody = requestObject.body;
            if (mIsGet) {
                Iterator<String> paramKeys = mBody.keys();
                StringBuilder requestBuf = new StringBuilder();
                boolean isFirst = true;
                while (paramKeys.hasNext()) {
                    String key = paramKeys.next();
                    Object value = mBody.get(key);
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        requestBuf.append("&");
                    }
                    requestBuf.append(key);
                    requestBuf.append("=");
                    requestBuf.append(URLEncoder.encode(value + "", mCharset));
                }
                if (requestBuf.length() > 0) {
                    mUrlStr = mUrlStr + "?" + requestBuf.toString();
                }
            }

            Set<String> urlKeys = urls.keySet();
            for (String key : urlKeys) {
                String value = urls.get(key);
                mUrlStr = mUrlStr.replace("\\{" + key + "}", value);
            }

            URL url = new URL(mUrlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(!mIsGet);
            connection.setUseCaches(mRequest.useCaches);
            connection.setConnectTimeout(mRequest.timeout);
            connection.setReadTimeout(mRequest.readTimeout);
            connection.setRequestMethod(method);

            Set<String> headKeys = heads.keySet();
            StringBuilder headBuilder = new StringBuilder();
            for (String key : headKeys) {//设置请求头
                String value = heads.get(key);
                connection.setRequestProperty(key, value);
                headBuilder.append(key);
                headBuilder.append("=");
                headBuilder.append(value);
                headBuilder.append(" ");
            }
            if (mRequest.requestStatus == RequestStatus.interrupt) {
                return;
            }
            HttpLog.i("网络请求：" + method + " " + mUrlStr + " 请求头部：" + headBuilder.toString() + " 请求参数：" + mBody.toString());
            childRun(connection);
            connection.disconnect();
        } catch (IOException e) {
            HttpLog.exception(ExceptionType.IOException, "IOException 网络请求异常");
            fail("IOException 网络请求异常：" + mResponseCode);
        } catch (JSONException e) {
            HttpLog.exception(ExceptionType.JSONException, "IOException 网络请求异常");
            fail("JSONException 网络请求异常：" + mResponseCode);
        }
    }

    /**
     * 返回数据时替换字符
     *
     * @param response not null
     * @return String
     */
    String replaceStringBeforeParseResponse(String response) {
        Set<String> oldChars = mRequest.replaceHttpResultMap.keySet();
        for (String oldChar : oldChars) {
            String replacement = mRequest.replaceHttpResultMap.get(oldChar);
            response = response.replace(oldChar, replacement);
        }
        return response;
    }

    protected abstract void childRun(HttpURLConnection connection) throws IOException;

    protected abstract void success(String result, Map<String, List<String>> headMap);

    protected abstract void fail(String msg);
}