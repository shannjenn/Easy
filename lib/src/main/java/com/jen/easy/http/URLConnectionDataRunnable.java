package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

class URLConnectionDataRunnable extends URLConnectionFactoryRunnable {

    URLConnectionDataRunnable(EasyHttpDataRequest request, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super(request, httpListener, flagCode, flagStr);
        mType = Type.data;
    }

    @Override
    protected boolean childRun(HttpURLConnection connection) throws IOException {
        long startTime = System.currentTimeMillis();
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mRequestObject.body.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }
        return runResponse(connection, startTime);
    }

}