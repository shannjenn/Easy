package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyRequestState;

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

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        mRequest.setRequestState(EasyRequestState.finish);
        if (httpListener != null)
            httpListener.success(flagCode, flagStr, mRequest, createResponseObjectSuccess(Type.data, result), headMap);
    }

    @Override
    protected void fail() {
        mRequest.setRequestState(EasyRequestState.finish);
        if (httpListener != null)
            httpListener.fail(flagCode, flagStr, mRequest, createResponseObjectFail(Type.data));
    }

}