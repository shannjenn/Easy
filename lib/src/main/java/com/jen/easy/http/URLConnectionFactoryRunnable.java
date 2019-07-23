package com.jen.easy.http;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyHttpDownloadRequest;
import com.jen.easy.http.request.EasyHttpRequest;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestState;
import com.jen.easy.http.response.EasyHttpFileResponse;
import com.jen.easy.http.response.EasyHttpResponse;
import com.jen.easy.log.JsonLogFormat;

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

abstract class URLConnectionFactoryRunnable implements Runnable {
    EasyHttpListener httpListener;
    int flagCode;//请求标识
    String flagStr;//请求标识
    EasyHttpRequest mRequest;
    private Class mResponse;

    String mUrlStr;
    int mResponseCode = -1;//返回码
    String mCharset;//编码
    boolean mIsGet = true;
    JSONObject mBody;
    String mRequestLogInfo;

    URLConnectionFactoryRunnable(EasyHttpRequest param, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super();
        this.mRequest = param;
        this.httpListener = httpListener;
        this.flagCode = flagCode;
        this.flagStr = flagStr;
    }

    @Override
    public void run() {
        HttpReflectRequestManager.RequestType requestType = HttpReflectRequestManager.getRequestType(mRequest);
        if (requestType == null) {
            HttpLog.exception(ExceptionType.RuntimeException, "请求参数未加注释");
            fail();
            return;
        }
        String method = requestType.method;
//        mRequest.url = requestType.url;
        Class response = requestType.response;
        mUrlStr = requestType.url;
        if (TextUtils.isEmpty(mUrlStr)) {
            mUrlStr = "";
            HttpLog.exception(ExceptionType.NullPointerException, "URL请求地址空");
            fail();
            return;
        }

        mResponse = response;
        if (mRequest instanceof EasyHttpDataRequest) {//===============基本数据处理

        } else if (mRequest instanceof EasyHttpUploadRequest) {//===============上传请求处理
            EasyHttpUploadRequest uploadRequest = (EasyHttpUploadRequest) mRequest;
            if (TextUtils.isEmpty(uploadRequest.filePath)) {
                HttpLog.exception(ExceptionType.NullPointerException, "上传文件地址不能为空");
                fail();
                return;
            }
            File file = new File(uploadRequest.filePath);
            if (!file.isFile()) {
                HttpLog.exception(ExceptionType.IllegalArgumentException, "上传文件地址不可用");
                fail();
                return;
            }
        } else if (mRequest instanceof EasyHttpDownloadRequest) {//===============下载请求处理
            EasyHttpDownloadRequest downloadRequest = (EasyHttpDownloadRequest) mRequest;
            if (downloadRequest.deleteOldFile) {
                if (TextUtils.isEmpty(downloadRequest.filePath)) {
                    HttpLog.exception(ExceptionType.NullPointerException, "要保存文件地址不能为空");
                    fail();
                    return;
                }
                File file = new File(downloadRequest.filePath);
                if (file.exists()) {
                    boolean ret = file.delete();
                    if (!ret) {
                        HttpLog.exception(ExceptionType.IllegalArgumentException, "删除旧文件失败，请检查文件路径是否正确");
                        fail();
                        return;
                    }
                }
            }

            if (TextUtils.isEmpty(downloadRequest.filePath)) {
                HttpLog.exception(ExceptionType.NullPointerException, "文件地址不能为空");
                fail();
                return;
            }

            File fileFolder = new File(downloadRequest.filePath.substring(0, downloadRequest.filePath.lastIndexOf("/")));
            if (!fileFolder.exists()) {
                boolean ret = fileFolder.mkdirs();
                if (!ret) {
                    HttpLog.exception(ExceptionType.IllegalArgumentException, "保存文件失败，请检查文件路径是否正确：" + downloadRequest.filePath);
                    fail();
                    return;
                }
            }
        }

        mResponseCode = -1;//返回码
//        mHasParam = false;//是否有参数
        mCharset = mRequest.charset;//编码
        mIsGet = method.toUpperCase().equals("GET");
        try {
            HttpReflectRequestManager.RequestObject requestObject = HttpReflectRequestManager.getRequestHeadAndBody(mRequest);
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

            StringBuilder mHeadLogBuilder = new StringBuilder();
            Set<String> headKeys = heads.keySet();
            for (String key : headKeys) {//设置请求头
                String value = heads.get(key);
                connection.setRequestProperty(key, value);
                mHeadLogBuilder.append(key);
                mHeadLogBuilder.append("：");
                mHeadLogBuilder.append(value);
                mHeadLogBuilder.append(",");
            }
            if (mHeadLogBuilder.length() > 0) {
                mHeadLogBuilder.insert(0, "{");
                mHeadLogBuilder.replace(mHeadLogBuilder.length() - 1, mHeadLogBuilder.length(), "}");
            }
            mRequestLogInfo = method + " " + mUrlStr + "\nrequest heads：\n" + mHeadLogBuilder.toString() + "\nrequest body：\n" + mBody.toString();
            childRun(connection);
            connection.disconnect();
        } catch (IOException e) {
            String infoFormat = "Http IOException error：\n" + JsonLogFormat.formatJson(mRequestLogInfo);
            HttpLog.e(infoFormat);
            e.printStackTrace();
            fail();
        } catch (JSONException e) {
            String infoFormat = "Http JSONException error：\n" + JsonLogFormat.formatJson(mRequestLogInfo);
            HttpLog.e(infoFormat);
            e.printStackTrace();
            fail();
        }
    }

    /**
     * 返回数据时替换字符
     *
     * @param response not null
     * @return String
     */
    String replaceResult(String response) {
        Set<String> oldChars = mRequest.getReplaceResult().keySet();
        for (String oldChar : oldChars) {
            String replacement = mRequest.getReplaceResult().get(oldChar);
            response = response.replace(oldChar, replacement);
        }
        return response;
    }

    @IntDef({Type.data, Type.fileUp, Type.fileDown})
    public @interface Type {
        int data = 0;//基本数据
        int fileUp = 1;//文件上传
        int fileDown = 2;//文件下载
    }

    @IntDef({Response.success, Response.fail, Response.progress})
    public @interface Response {
        int success = 0;//成功
        int fail = 1;//失败
        int progress = 2;//上传、下载进度
    }

    Object createResponseObjectSuccess(@Type int type, String resultOrFilePath) {
        switch (type) {
            case Type.data:
                return createResponseObject(type, Response.success, resultOrFilePath, null);
            case Type.fileDown:
                return createResponseObject(type, Response.success, null, resultOrFilePath);
            case Type.fileUp:
                return createResponseObject(type, Response.success, resultOrFilePath, null);
        }
        return "";
    }

    Object createResponseObjectFail(@Type int type) {
        return createResponseObject(type, Response.fail, null, null);
    }

    Object createResponseObjectProgress(@Type int type) {
        return createResponseObject(type, Response.progress, null, null);
    }

    /**
     * 创建返回实体
     *
     * @param type         类型
     * @param result       返回数据
     * @param responseType 返回类型
     * @return .
     */
    private Object createResponseObject(@Type int type, @Response int responseType, String result, String filePath) {
        Object responseObject = null;
        if (mResponse == null || FieldType.isObject(mResponse) || FieldType.isString(mResponse)) {//Object和String类型不做数据解析
            switch (responseType) {
                case Response.success:
                    responseObject = result;
                    break;
                case Response.fail:
                case Response.progress:
                    responseObject = "";
                    break;
            }
        } else {
            switch (type) {
                case Type.data: {
                    switch (responseType) {
                        case Response.success: {
                            responseObject = new HttpParseManager().parseResponseBody(mResponse, result);
                            break;
                        }
                        case Response.fail: {
                            responseObject = new HttpParseManager().newResponseInstance(mResponse);
                            break;
                        }
                        case Response.progress: {
                            break;
                        }
                    }
                    break;
                }
                case Type.fileDown: {
                    switch (responseType) {
                        case Response.success: {
                            responseObject = new HttpParseManager().newResponseInstance(mResponse);
                            if (responseObject instanceof EasyHttpFileResponse) {
                                EasyHttpFileResponse fileResponse = (EasyHttpFileResponse) responseObject;
                                fileResponse.setFilePath(filePath);
                            }
                            break;
                        }
                        case Response.fail:
                        case Response.progress: {
                            responseObject = new HttpParseManager().newResponseInstance(mResponse);
                            break;
                        }
                    }
                    break;
                }
                case Type.fileUp: {
                    switch (responseType) {
                        case Response.success: {
                            responseObject = new HttpParseManager().parseResponseBody(mResponse, result);
                            break;
                        }
                        case Response.fail:
                        case Response.progress: {
                            responseObject = new HttpParseManager().newResponseInstance(mResponse);
                            break;
                        }
                    }
                    break;
                }
            }
            if (responseObject instanceof EasyHttpResponse) {
                ((EasyHttpResponse) responseObject).setResponseCode(mResponseCode);
            } else if (responseObject == null) {
                responseObject = "";
            }
        }
        switch (responseType) {
            case Response.success:
                mRequest.setRequestState(EasyRequestState.finish);
                HttpLog.i(mUrlStr + " SUCCESS\n \t");
                break;
            case Response.fail:
                mRequest.setRequestState(EasyRequestState.finish);
                HttpLog.i(mUrlStr + " FAIL\n \t");
                break;
            case Response.progress:
                break;
        }
        return responseObject;
    }

    boolean checkListener() {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.i(mUrlStr + " The request was interrupted.\n \t");
            return false;
        }
        return httpListener != null;
    }

    protected abstract void childRun(HttpURLConnection connection) throws IOException;

    protected abstract void success(String result, Map<String, List<String>> headMap);

    protected abstract void fail();
}