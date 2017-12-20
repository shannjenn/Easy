package com.jen.easy.http;

import com.jen.easy.log.EasyLibLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

class HttpURLConnectionBaseRunable extends HttpURLConnectionRunable {

    HttpURLConnectionBaseRunable(HttpBaseRequest request) {
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

        mResposeCode = connection.getResponseCode();
        EasyLibLog.d(TAG + mUrlStr + "  Http请求返回码：" + mResposeCode);
        if ((mResposeCode == 200)) {
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
            success(result);
        } else {
            fail(" 网络请求异常：" + mResposeCode);
        }
    }

    @Override
    protected void success(String result) {
        HttpBaseRequest baseRequest = (HttpBaseRequest) mRequest;
        if (baseRequest.getBseListener() != null) {
            HttpParseManager parseManager = new HttpParseManager();
            parseManager.setResponseObjectType(baseRequest.responseObjectType);
            Object parseObject = parseManager.parseJson(mResponseClass, result);
            if (parseObject == null) {
                fail("数据解析异常");
            } else {
                baseRequest.getBseListener().success(baseRequest.flag.code, baseRequest.flag.str, parseObject);
            }
        }
    }

    @Override
    protected void fail(String result) {
        HttpBaseRequest baseRequest = (HttpBaseRequest) mRequest;
        if (baseRequest.getBseListener() != null)
            baseRequest.getBseListener().fail(baseRequest.flag.code, baseRequest.flag.str, result);
    }
}