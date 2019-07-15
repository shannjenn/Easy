package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyRequestState;
import com.jen.easy.http.response.EasyHttpResponse;
import com.jen.easy.http.response.EasyResponseState;
import com.jen.easy.log.JsonLogFormat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class URLConnectionDataRunnable extends URLConnectionFactoryRunnable {
    private EasyHttpListener baseListener;

    URLConnectionDataRunnable(EasyHttpDataRequest request, EasyHttpListener baseListener, int flagCode, String flagStr) {
        super(request, flagCode, flagStr);
        this.baseListener = baseListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        long startTime = System.currentTimeMillis();
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mBody.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

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
            String bodyFormat = null;
            if (mRequest.getReplaceResult().size() > 0) {
                bodyFormat = replaceResult(body);
            }
            success(bodyFormat != null ? bodyFormat : body, headMap);

            StringBuilder returnLogBuild = new StringBuilder();
            returnLogBuild.append(mRequestLogInfo).append("\nreturn heads：\n{");
            for (Map.Entry<String, List<String>> entry : headMap.entrySet()) {
                returnLogBuild.append(entry.getKey());
                returnLogBuild.append(":");
                returnLogBuild.append(entry.getValue());
            }
            returnLogBuild.append("}");
            returnLogBuild.append("\nreturn body：\n").append(body);
            if (bodyFormat != null) {
                returnLogBuild.append("\nformat return body：\n").append(bodyFormat);
            }
            returnLogBuild.append("\nreturn code：").append(mResponseCode).append(" response time:").append(timeSec).append("second");
            HttpLog.i(JsonLogFormat.formatJson(returnLogBuild.toString()));
        } else {
            fail("http request error：" + mResponseCode);
            double timeSec = (System.currentTimeMillis() - startTime) / 1000d;
            HttpLog.e(JsonLogFormat.formatJson(mRequestLogInfo + "\nreturn code：" + mResponseCode + " response:" + timeSec + "second"));
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.d(mRequestLogInfo + " The request was interrupted.\n \t");//\n \t打印才出现空行
            return;
        }
        if (baseListener == null) {
            return;
        }
        Object parseObject;
        if (FieldType.isObject(mResponse) || FieldType.isString(mResponse)) {//Object和String类型不做数据解析
            parseObject = result;
        } else {
            parseObject = new HttpParseManager().parseResponseBody(mResponse, result);
        }
        if (parseObject == null) {
            fail("");
        } else {
            if (parseObject instanceof EasyHttpResponse) {
                ((EasyHttpResponse) parseObject).setResponseState(EasyResponseState.finish);
            }
            mRequest.setRequestState(EasyRequestState.finish);
            HttpLog.d(mUrlStr + " return SUCCESS\n \t");
            baseListener.success(flagCode, flagStr, parseObject, headMap);
        }
    }

    @Override
    protected void fail(String result) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.d(mRequestLogInfo + " The request was interrupted。\n \t");
            return;
        }
        if (baseListener == null) {
            return;
        }
        mRequest.setRequestState(EasyRequestState.finish);
        HttpLog.w(mUrlStr + " return FAIL。\n \t");
        baseListener.fail(flagCode, flagStr, result);
    }
}