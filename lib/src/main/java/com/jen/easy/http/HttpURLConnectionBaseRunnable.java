package com.jen.easy.http;

import com.jen.easy.log.EasyLibLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class HttpURLConnectionBaseRunnable extends HttpURLConnectionRunnable {

    HttpURLConnectionBaseRunnable(HttpBaseRequest request) {
        super(request, "HttpBase :");
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        if (!mIsGet && mHasParam) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(mRequestParam);
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        EasyLibLog.d(TAG + mUrlStr + " Http请求返回码：" + mResponseCode);
        if ((mResponseCode == 200)) {
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            StringBuffer resultBuffer = new StringBuffer("");
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
            EasyLibLog.d(TAG + mUrlStr + " 返回数据：" + result);
            success(result, headMap);
        } else {
            fail(" 网络请求异常：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        HttpBaseRequest baseRequest = (HttpBaseRequest) mRequest;
        if (baseRequest.getBseListener() != null) {
            HttpParseManager parseManager = new HttpParseManager();
            parseManager.setResponseObjectType(baseRequest.responseObjectType);
            Object parseObject = parseManager.parseJson(mResponseClass, result,headMap);
            if (parseObject == null) {
                fail("解析数据解析出错");
            } else {
                EasyLibLog.d(TAG + mUrlStr + " 成功!");
                baseRequest.getBseListener().success(baseRequest.flag.code, baseRequest.flag.str, parseObject);
            }
        }
    }

    @Override
    protected void fail(String result) {
        EasyLibLog.e(TAG + mUrlStr + " " + result);
        HttpBaseRequest baseRequest = (HttpBaseRequest) mRequest;
        if (baseRequest.getBseListener() != null)
            baseRequest.getBseListener().fail(baseRequest.flag.code, baseRequest.flag.str, result);
    }
}