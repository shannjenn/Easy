package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
import com.jen.easy.log.EasyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class HttpURLConnectionRunnable implements Runnable {
    HttpRequest mRequest;
    Class mResponse;

    String mUrlStr;
    int mResponseCode = -1;//返回码
    //    boolean mHasParam = false;//是否有参数
    String mCharset;//编码
    boolean mIsGet = true;
    //    String mParamStr;
    final JSONObject mJsonParam = new JSONObject();

    HttpURLConnectionRunnable(HttpRequest param) {
        super();
        this.mRequest = param;
    }

    @Override
    public void run() {
        Object[] method_url = HttpReflectManager.getUrl(mRequest);
        String method = (String) method_url[0];
        mRequest.url = (String) method_url[1];
        Object response = method_url[2];
        mUrlStr = mRequest.url;
        if (TextUtils.isEmpty(mUrlStr)) {
            mUrlStr = "";
            Throw.exception(ExceptionType.NullPointerException, "URL请求地址空");
            fail("URL地址为空");
            return;
        }

        if (mRequest instanceof HttpBasicRequest) {//===============基本数据处理
            if (response instanceof Class) {
                mResponse = (Class) response;
            } else {
                Throw.exception(ExceptionType.NullPointerException, "返回对象不能为空");
                fail("返回对象不能为空");
                return;
            }
        } else if (mRequest instanceof HttpUploadRequest) {//===============上传请求处理
            if (response instanceof Class) {
                mResponse = (Class) response;
            } else {
                Throw.exception(ExceptionType.NullPointerException, "返回对象不能为空");
                fail("返回对象不能为空");
                return;
            }
            HttpUploadRequest uploadRequest = (HttpUploadRequest) mRequest;
            if (TextUtils.isEmpty(uploadRequest.filePath)) {
                Throw.exception(ExceptionType.NullPointerException, "文件地址不能为空");
                fail("文件地址不能为空");
                return;
            }
            File file = new File(uploadRequest.filePath);
            if (!file.isFile()) {
                Throw.exception(ExceptionType.IllegalArgumentException, "文件地址参数错误");
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
                        Throw.exception(ExceptionType.IllegalArgumentException, "删除旧文件失败，请检查文件路径是否正确");
                        fail("删除旧文件失败，请检查文件路径是否正确");
                        return;
                    }
                }
            }

            if (TextUtils.isEmpty(downloadRequest.filePath)) {
                Throw.exception(ExceptionType.NullPointerException, "文件地址不能为空");
                fail("文件地址不能为空");
                return;
            }

            File fileFolder = new File(downloadRequest.filePath.substring(0, downloadRequest.filePath.lastIndexOf("/")));
            if (!fileFolder.exists()) {
                boolean ret = fileFolder.mkdirs();
                if (!ret) {
                    Throw.exception(ExceptionType.IllegalArgumentException, "保存文件失败，请检查文件路径是否正确");
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
            Map<String, String> urls = new HashMap<>();
//            JSONObject mJsonParam = new JSONObject();
            Map<String, String> heads = new HashMap<>();
            HttpReflectManager.getRequestParams(new ArrayList<String>(), mRequest, urls, mJsonParam, heads);
            if (mRequest.state == HttpState.STOP) {
                return;
            }
            if (mIsGet) {
                Iterator<String> paramKeys = mJsonParam.keys();
                StringBuilder requestBuf = new StringBuilder();
                boolean isFirst = true;
                while (paramKeys.hasNext()) {
                    String key = paramKeys.next();
                    Object value = mJsonParam.get(key);
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
            if (mRequest.state == HttpState.STOP) {
                return;
            }
            EasyLog.d(TAG.EasyHttp, "网络请求：" + method + " " + mUrlStr + " 请求头部：" + headBuilder.toString() + " 请求参数：" + mJsonParam.toString());
            childRun(connection);
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException 网络请求异常：" + mResponseCode);
        } catch (JSONException e) {
            e.printStackTrace();
            fail("JSONException 网络请求异常：" + mResponseCode);
        }
    }

    protected abstract void childRun(HttpURLConnection connection) throws IOException;

    protected abstract void success(String result, Map<String, List<String>> headMap);

    protected abstract void fail(String msg);
}