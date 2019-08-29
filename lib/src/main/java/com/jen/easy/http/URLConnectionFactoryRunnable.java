package com.jen.easy.http;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.jen.easy.EasyUploadType;
import com.jen.easy.constant.FieldType;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDownloadRequest;
import com.jen.easy.http.request.EasyHttpRequest;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.response.EasyHttpDownLoadResponse;
import com.jen.easy.http.response.EasyHttpResponse;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.JsonLogFormat;
import com.jen.easy.log.LogLevel;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    String mUrlStr = "";
    int mResponseCode = -1;//返回码
    String mCharset;//编码
    boolean mIsGet = true;
    HttpReflectRequestManager.RequestObject mRequestObject;

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
            HttpLog.e(getLogBeforeRequest("请求参数未加注释"));
            fail();
            return;
        }
        String method = requestType.method;
//        mRequest.url = requestType.url;
        Class response = requestType.response;
        mUrlStr = requestType.url;
        if (TextUtils.isEmpty(mUrlStr)) {
            mUrlStr = "";
            HttpLog.e(getLogBeforeRequest("URL请求地址空"));
            fail();
            return;
        }

        mResponse = response;
        /*if (mRequest instanceof EasyHttpDataRequest) {//===============基本数据处理

        } else */
        if (mRequest instanceof EasyHttpUploadRequest) {//===============上传请求处理
            EasyHttpUploadRequest uploadRequest = (EasyHttpUploadRequest) mRequest;
            if (TextUtils.isEmpty(uploadRequest.filePath)) {
                HttpLog.e(getLogBeforeRequest("上传的文件地址不能为空"));
                fail();
                return;
            }
            File file = new File(uploadRequest.filePath);
            if (!file.isFile()) {
                HttpLog.e(getLogBeforeRequest("上传的文件地址不可用 filePath = " + uploadRequest.filePath));
                fail();
                return;
            }
            if (uploadRequest.uploadType == null) {
                HttpLog.e(getLogBeforeRequest("uploadRequest.uploadType不可设置空值"));
                fail();
                return;
            }
            if (uploadRequest.uploadType == EasyUploadType.ParamFile) {
                if (uploadRequest.fileNameKey == null || uploadRequest.fileNameKey.trim().length() == 0) {
                    HttpLog.e(getLogBeforeRequest("uploadRequest.fileNameKey不可设置空值"));
                    fail();
                    return;
                }
            }
        } else if (mRequest instanceof EasyHttpDownloadRequest) {//===============下载请求处理
            EasyHttpDownloadRequest downloadRequest = (EasyHttpDownloadRequest) mRequest;
            if (downloadRequest.deleteOldFile) {
                if (TextUtils.isEmpty(downloadRequest.filePath)) {
                    HttpLog.e(getLogBeforeRequest("要保存文件地址不能为空"));
                    fail();
                    return;
                }
                File file = new File(downloadRequest.filePath);
                if (file.exists()) {
                    boolean ret = file.delete();
                    if (!ret) {
                        HttpLog.e(getLogBeforeRequest("删除旧文件失败，请检查文件路径是否正确"));
                        fail();
                        return;
                    }
                }
            }

            if (TextUtils.isEmpty(downloadRequest.filePath)) {
                HttpLog.e(getLogBeforeRequest("文件地址不能为空"));
                fail();
                return;
            }

            File fileFolder = new File(downloadRequest.filePath.substring(0, downloadRequest.filePath.lastIndexOf("/")));
            if (!fileFolder.exists()) {
                boolean ret = fileFolder.mkdirs();
                if (!ret) {
                    HttpLog.e(getLogBeforeRequest("保存文件失败，请检查文件路径是否正确：" + downloadRequest.filePath));
                    fail();
                    return;
                }
            }
        }

        boolean isSuccess;
        mResponseCode = -1;//返回码
        mCharset = mRequest.charset;//编码
        mIsGet = method.toUpperCase().equals("GET");
        try {
            mRequestObject = HttpReflectRequestManager.getRequestHeadAndBody(mRequest);
            Map<String, String> urls = mRequestObject.urls;
            Map<String, String> heads = mRequestObject.heads;
            if (mIsGet) {
                Iterator<String> paramKeys = mRequestObject.body.keys();
                StringBuilder requestBuf = new StringBuilder();
                boolean isFirst = true;
                while (paramKeys.hasNext()) {
                    String key = paramKeys.next();
                    Object value = mRequestObject.body.get(key);
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
                mUrlStr = mUrlStr.replace("{" + key + "}", value);//不可以用replaceAll会报错
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
            for (String key : headKeys) {//设置请求头
                String value = heads.get(key);
                connection.setRequestProperty(key, value);
            }
            isSuccess = childRun(connection);
            connection.disconnect();
        } catch (IOException e) {
            HttpLog.e("\nHttp IOException error：\n" + JsonLogFormat.formatJson(getLogRequestInfo()) + "\n");
            e.printStackTrace();
            isSuccess = false;
        } catch (JSONException e) {
            HttpLog.e("\nHttp JSONException error：\n" + JsonLogFormat.formatJson(getLogRequestInfo()) + "\n");
            e.printStackTrace();
            isSuccess = false;
        }
        if (!isSuccess)
            fail();
    }

    /**
     * 运行获取返回参数
     */
    boolean runResponse(HttpURLConnection connection, long startTime) throws IOException {
        boolean isSuccess;
        mResponseCode = connection.getResponseCode();
        if ((mResponseCode == 200)) {
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            StringBuilder bodyBuffer = new StringBuilder();
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, mCharset));
            String s;
            while ((s = reader.readLine()) != null) {
                bodyBuffer.append(s);
            }
            reader.close();
            inStream.close();
            connection.disconnect();

            double timeSec = (System.currentTimeMillis() - startTime) / 1000d;
            String body = bodyBuffer.toString();
            String formatBody = null;
            if (mRequest.getReplaceResult().size() > 0) {
                formatBody = replaceResult(body);
            }
            if (EasyLog.easyHttpPrint && EasyLog.isPrint(LogLevel.I))//先判断是否打印（性能优化）
                HttpLog.i(getLogFormatRequestAndResponse(headMap, body, formatBody, timeSec));
            success(formatBody != null ? formatBody : body, headMap);
            isSuccess = true;
        } else {
            double timeSec = (System.currentTimeMillis() - startTime) / 1000d;
            HttpLog.e("\n" + JsonLogFormat.formatJson(getLogRequestInfo() + "\nresponse code：" + mResponseCode + " time: " + timeSec + " second\n"));
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 返回数据时替换字符
     *
     * @param response not null
     * @return String
     */
    private String replaceResult(String response) {
        Set<String> oldChars = mRequest.getReplaceResult().keySet();
        for (String oldChar : oldChars) {
            String replacement = mRequest.getReplaceResult().get(oldChar);
            response = response.replace(oldChar, replacement);
        }
        return response;
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
                            if (responseObject instanceof EasyHttpDownLoadResponse) {
                                EasyHttpDownLoadResponse fileResponse = (EasyHttpDownLoadResponse) responseObject;
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
                EasyHttpResponse httpResponse = (EasyHttpResponse) responseObject;
                httpResponse.setResponseCode(mResponseCode);
                httpResponse.setRequestLog(getLogRequestInfo());
            } else if (responseObject == null) {
                responseObject = "";
            }
        }
        switch (responseType) {
            case Response.success:
                HttpLog.i(mUrlStr + " SUCCESS\n \t");
                break;
            case Response.fail:
                HttpLog.i(mUrlStr + " FAIL\n \t");
                break;
            case Response.progress:
                break;
        }
        return responseObject;
    }

    protected abstract boolean childRun(HttpURLConnection connection) throws IOException;

    protected abstract void success(String result, Map<String, List<String>> headMap);

    protected abstract void fail();

    /**
     * 开始请求前错误提示Log
     */
    private String getLogBeforeRequest(String log) {
        return mUrlStr + " " + log + "\nclass " + mRequest.getClass().getName();
    }

    /**
     * 获取请求Log
     */
    String getLogRequestInfo() {
        StringBuilder headBuilder = new StringBuilder();
        String body = "";
        String fileRequestInfo = "";
        if (mRequestObject != null) {
            Set<String> headKeys = mRequestObject.heads.keySet();
            for (String key : headKeys) {//设置请求头
                String value = mRequestObject.heads.get(key);
                headBuilder.append(key);
                headBuilder.append("：");
                headBuilder.append(value);
                headBuilder.append(",");
            }
            if (headBuilder.length() > 0) {
                headBuilder.insert(0, "{");
                headBuilder.replace(headBuilder.length() - 1, headBuilder.length(), "}");
            }
            body = mRequestObject.body.toString();
            if (mRequest instanceof EasyHttpUploadRequest) {
                EasyHttpUploadRequest uploadRequest = (EasyHttpUploadRequest) mRequest;
                fileRequestInfo = "\n上传文件 filePath = " + uploadRequest.filePath;
                fileRequestInfo += uploadRequest.fileNameKey != null ? "\nfileNameKey = " + uploadRequest.fileNameKey : "";
                fileRequestInfo += uploadRequest.fileNameValue != null ? "\nfileNameValue = " + uploadRequest.fileNameValue : "";
            } else if (mRequest instanceof EasyHttpDownloadRequest) {
                fileRequestInfo = "\n下载文件 filePath = " + ((EasyHttpDownloadRequest) mRequest).filePath;
            }
        }
        return (mIsGet ? "Get " : "Post ") + mUrlStr
                + "\n请求 class = " + mRequest.getClass().getName()
                + fileRequestInfo
                + "\n请求 Heads：\n" + headBuilder.toString()
                + "\n请求 Body：\n" + body;

    }

    /**
     * 格式化返回数据Log
     */
    private String getLogFormatRequestAndResponse(Map<String, List<String>> ResponseHeadMap, String responseBody, String formatBody, double timeSec) {
        final String COMMA = "\\,带英文逗号的值不做Json换行";
        StringBuilder returnLogBuild = new StringBuilder();
        returnLogBuild.append(getLogRequestInfo()).append("\n返回 Heads：\n{");
        for (Map.Entry<String, List<String>> entry : ResponseHeadMap.entrySet()) {
            returnLogBuild.append(entry.getKey());
            returnLogBuild.append(":");
            StringBuilder valueBuild = new StringBuilder();
            int size = entry.getValue().size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    valueBuild.append(" && ");
                }
                valueBuild.append(entry.getValue().get(i));
            }
            String value = valueBuild.toString().replace(",", COMMA);
            returnLogBuild.append(value);
            returnLogBuild.append(",");
        }
        if (ResponseHeadMap.size() > 0) {
            returnLogBuild.deleteCharAt(returnLogBuild.length() - 1);
        }
        returnLogBuild.append("}");
        returnLogBuild.append("\n返回 Body：\n").append(responseBody);
        if (formatBody != null) {
            returnLogBuild.append("\n格式化 返回 Body：\n").append(formatBody);
        }
        returnLogBuild.append("\n返回 Code：").append(mResponseCode).append(" Time: ").append(timeSec).append(" Second\n");
        return JsonLogFormat.formatJson(returnLogBuild.toString()).replace(COMMA, ",");
    }
}