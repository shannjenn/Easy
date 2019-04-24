package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyRequestState;
import com.jen.easy.http.response.EasyHttpResponse;
import com.jen.easy.http.response.EasyResponseState;

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
            String result = resultBuffer.toString();
            HttpLog.i(mRequestLogInfo + "\n 返回码：" + mResponseCode + "\n 返回原始数据：" + result);
            result = replaceResult(result);
            success(result, headMap);
        } else {
            HttpLog.i(mRequestLogInfo + "\n 请求异常，返回码：" + mResponseCode);
            fail(" 网络请求异常：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.d(mRequestLogInfo + " 请求中断。\n \t");//\n \t打印才出现空行
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
            HttpLog.d(mUrlStr + " 返回成功。\n \t");
            baseListener.success(flagCode, flagStr, parseObject, headMap);
        }
    }

    @Override
    protected void fail(String result) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.d(mRequestLogInfo + " 请求中断。\n \t");
            return;
        }
        if (baseListener == null) {
            return;
        }
        mRequest.setRequestState(EasyRequestState.finish);
        HttpLog.w(mUrlStr + " 返回失败。\n \t");
        baseListener.fail(flagCode, flagStr, result);
    }
}