package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class URLConnectionDataRunnable extends URLConnectionFactoryRunnable {

    URLConnectionDataRunnable(EasyHttpDataRequest request, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super(request, httpListener, flagCode, flagStr);
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        long startTime = System.currentTimeMillis();
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mRequestObject.body.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }
        runResponse(connection, startTime);
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (checkListener())
            httpListener.success(flagCode, flagStr, createResponseObjectSuccess(Type.data, result), headMap);
    }

    @Override
    protected void fail() {
        if (checkListener())
            httpListener.fail(flagCode, flagStr, createResponseObjectFail(Type.data));
    }

}