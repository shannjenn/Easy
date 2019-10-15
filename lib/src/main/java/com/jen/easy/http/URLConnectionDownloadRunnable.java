package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDownloadRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class URLConnectionDownloadRunnable extends URLConnectionFactoryRunnable {
    private Object responseObjectProgress;

    URLConnectionDownloadRunnable(EasyHttpDownloadRequest request, EasyHttpListener httpListener, int flagCode, String flagStr) {
        super(request, httpListener, flagCode, flagStr);
        mType = Type.fileDown;
    }

    @Override
    protected boolean childRun(HttpURLConnection connection) throws IOException {
        long startTime = System.currentTimeMillis();
        boolean isSuccess = false;
        responseObjectProgress = createResponseObject(Response.progress, null);
        EasyHttpDownloadRequest request = (EasyHttpDownloadRequest) mRequest;
        if (request.startPoint <= 1024 * 2) {
            request.startPoint = 0;
        } else {
            request.startPoint = request.startPoint - 1024 * 2;
        }

        if (request.isBreak && request.endPoint > request.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.startPoint + "-" + request.endPoint);
        }

        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mRequestObject.body.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        if (mResponseCode == 200) {
            long curBytes = request.startPoint;
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            request.endPoint = connection.getContentLength();
            InputStream inStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            RandomAccessFile randFile = new RandomAccessFile(request.filePath, "rwd");
            randFile.seek(request.startPoint);
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                randFile.write(buffer, 0, len);
                curBytes += len;
                progress(curBytes, request.endPoint);
            }
            randFile.close();
            mResponseTime = (System.currentTimeMillis() - startTime) / 1000d;
            if (curBytes == request.endPoint) {
                success(null, null, headMap);
                isSuccess = true;
            }
        }
        if (!isSuccess) {
            mResponseTime = (System.currentTimeMillis() - startTime) / 1000d;
        }
        return isSuccess;
    }

    private void progress(long currentPoint, long endPoint) {
        if (httpListener != null) {
            httpListener.progress(flagCode, flagStr, mRequest, responseObjectProgress, currentPoint, endPoint);
        }
    }

}