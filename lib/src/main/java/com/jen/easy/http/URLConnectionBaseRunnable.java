package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpDataListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyRequestStatus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class URLConnectionBaseRunnable extends URLConnectionFactoryRunnable {
    private EasyHttpDataListener baseListener;

    URLConnectionBaseRunnable(EasyHttpDataRequest request, EasyHttpDataListener baseListener, int flagCode, String flagStr) {
        super(request, flagCode, flagStr);
        this.baseListener = baseListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mBody.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        HttpLog.d(mUrlStr + " Http请求返回码：" + mResponseCode);
        if ((mResponseCode == 200)) {
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            StringBuilder resultBuffer = new StringBuilder();
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, mCharset));
            String s;
            while ((s = reader.readLine()) != null) {
                resultBuffer.append(s);
            }
            reader.close();
            inStream.close();
            connection.disconnect();
            if (mRequest.getRequestStatus() == EasyRequestStatus.interrupt) {//拦截数据解析
                HttpLog.d(mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            String result = resultBuffer.toString();
            HttpLog.i(mUrlStr + " 返回原始数据：" + result);
            result = replaceResult(result);
            mRequest.setRequestStatus(EasyRequestStatus.finish);
            success(result, headMap);
        } else {
            if (mRequest.getRequestStatus() == EasyRequestStatus.interrupt) {//拦截数据解析
                HttpLog.d(mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            mRequest.setRequestStatus(EasyRequestStatus.finish);
            fail(" 网络请求异常：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (baseListener != null) {
            if (FieldType.isObject(mResponse) || FieldType.isString(mResponse)) {//Object和String类型不做数据解析
                HttpLog.d(mUrlStr + " 成功!\n   ");
                baseListener.success(flagCode, flagStr, result, headMap);
            } else {
                HttpParseManager parseManager = new HttpParseManager();
                Object parseObject = parseManager.parseResponseBody(mResponse, result);
                if (parseObject == null) {
                    fail("解析数据解析出错\n   ");
                } else {
                    HttpLog.d(mUrlStr + " 成功!\n   ");
                    baseListener.success(flagCode, flagStr, parseObject, headMap);
                }
            }
        }
    }

    @Override
    protected void fail(String result) {
        HttpLog.w(mUrlStr + " " + result + "\n   ");
        if (baseListener != null)
            baseListener.fail(flagCode, flagStr, result);
    }
}