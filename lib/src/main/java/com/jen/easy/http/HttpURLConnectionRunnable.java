package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.log.EasyLibLog;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

abstract class HttpURLConnectionRunnable implements Runnable {
    protected String TAG;
    HttpRequest mRequest;
    Class mResponseClass;

    String mUrlStr;
    int mResponseCode = -1;//返回码
    boolean mHasParam = false;//是否有参数
    String mCharset;//编码
    boolean mIsGet = true;
    String mRequestParam;
    private String mMethod;

    HttpURLConnectionRunnable(HttpRequest param, String TAG) {
        super();
        this.TAG = TAG;
        this.mRequest = param;
    }

    @Override
    public void run() {
        Object[] method_url = HttpReflectManager.getUrl(mRequest);
        if (method_url != null) {
            mMethod = (String) method_url[0];
            mRequest.httpParam.url = (String) method_url[1];
            mResponseClass = (Class) method_url[2];
        }
        mUrlStr = mRequest.httpParam.url;
        if (TextUtils.isEmpty(mUrlStr)) {
            mUrlStr = "";
            fail("URL地址为空");
            return;
        }

        if (mRequest instanceof HttpBaseRequest) {//===============基本数据处理
            if (mResponseClass == null) {
                fail("返回class不能为空");
                return;
            }
        } else if (mRequest instanceof HttpUploadRequest) {//===============上传请求处理
            if (mResponseClass == null) {
                fail("返回class不能为空");
                return;
            }
            HttpUploadRequest uploadRequest = (HttpUploadRequest) mRequest;
            if (TextUtils.isEmpty(uploadRequest.flag.filePath)) {
                fail("文件地址不能为空");
                return;
            }
            File file = new File(uploadRequest.flag.filePath);
            if (!file.isFile()) {
                fail("文件地址参数错误");
                return;
            }
        } else if (mRequest instanceof HttpDownloadRequest) {//===============下载请求处理
            HttpDownloadRequest downloadRequest = (HttpDownloadRequest) mRequest;
            if (downloadRequest.flag.deleteOldFile) {
                File file = new File(downloadRequest.flag.filePath);
                if (file.exists()) {
                    boolean ret = file.delete();
                    if (!ret) {
                        fail("删除旧文件失败，请检查文件路径是否正确");
                        return;
                    }
                }
            }

            if (TextUtils.isEmpty(downloadRequest.flag.filePath)) {
                fail("文件地址不能为空");
                return;
            }

            File fileFolder = new File(downloadRequest.flag.filePath.substring(0, downloadRequest.flag.filePath.lastIndexOf("/")));
            if (!fileFolder.exists()) {
                boolean ret = fileFolder.mkdirs();
                if (!ret) {
                    fail("保存文件失败，请检查文件路径是否正确");
                    return;
                }
            }
        }

        mResponseCode = -1;//返回码
        mHasParam = false;//是否有参数
        mCharset = mRequest.httpParam.charset;//编码
        mIsGet = mMethod.toUpperCase().equals("GET");
        try {
            Map<String, List<String>> requests = HttpReflectManager.getRequestParams(mRequest);
            List<String> requestParamKeys = requests.get(HttpReflectManager.REQ_PARAM_KEYS);
            List<String> requestParamValues = requests.get(HttpReflectManager.REQ_PARAM_VALUES);
            List<String> requestHeadKeys = requests.get(HttpReflectManager.REQ_HEAD_KEYS);
            List<String> requestHeadValues = requests.get(HttpReflectManager.REQ_HEAD_VALUES);

            boolean isNotFirst = false;
            StringBuffer requestBuf = new StringBuffer("");
            int paramSize = requestParamKeys.size();
            for (int i = 0; i < paramSize; i++) {
                String key = requestParamKeys.get(i);
                String value = requestParamValues.get(i);
                if (isNotFirst) {
                    requestBuf.append("&");
                }
                requestBuf.append(key);
                requestBuf.append("=");
//                if (mIsGet)//get方式加转译符
//                    requestBuf.append("\"");
                requestBuf.append(URLEncoder.encode(value, mCharset));
//                if (mIsGet)//get方式加转译符
//                    requestBuf.append("\"");
                isNotFirst = true;
            }
            mRequestParam = requestBuf.toString();
            mHasParam = mRequestParam.length() > 0;

            EasyLibLog.d(TAG + "网络请求：" + mMethod + " " + mUrlStr + " 请求参数：" + mRequestParam);
            if (mIsGet && mHasParam) {//get请求参数拼接
                mUrlStr = mUrlStr + "?" + mRequestParam;
            }

            URL url = new URL(mUrlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(!mIsGet);
            connection.setUseCaches(mRequest.httpParam.useCaches);
            connection.setConnectTimeout(mRequest.httpParam.timeout);
            connection.setReadTimeout(mRequest.httpParam.readTimeout);
            connection.setRequestMethod(mMethod);
            int headSize = requestHeadKeys.size();
            for (int i = 0; i < headSize; i++) {//设置请求头
                String key = requestHeadKeys.get(i);
                String value = requestHeadValues.get(i);
                connection.setRequestProperty(key, value);
            }
            childRun(connection);
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException 网络请求异常：" + mResponseCode);
        }
    }

    protected abstract void childRun(HttpURLConnection connection) throws IOException;

    protected abstract void success(String result, Map<String, List<String>> headMap);

    protected abstract void fail(String msg);
}