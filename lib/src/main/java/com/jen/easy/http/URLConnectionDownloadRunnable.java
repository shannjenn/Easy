package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDownloadRequest;
import com.jen.easy.http.request.EasyRequestState;

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
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        responseObjectProgress = createResponseObjectProgress(Type.fileDown);
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
            out.write(mBody.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        HttpLog.d(mUrlStr + "  Http请求返回码：" + mResponseCode);
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
                if (mRequest.getRequestState() == EasyRequestState.interrupt) {
                    break;
                } else {
                    progress(curBytes, request.endPoint);
                }
            }
            if (curBytes == request.endPoint) {
                success(request.filePath, headMap);
            } else {
                fail("下载失败：" + mResponseCode + " curBytes = " + curBytes + " endPoint = " + request.endPoint);
            }
        } else {
            fail("下载失败：" + mResponseCode);
        }
    }

    @Override
    protected void success(String filePath, Map<String, List<String>> headMap) {
        if (checkListener())
            httpListener.success(flagCode, flagStr, createResponseObjectSuccess(Type.fileDown, filePath), headMap);
    }

    @Override
    protected void fail(String errorMsg) {
        if (checkListener())
            httpListener.fail(flagCode, flagStr, createResponseObjectFail(Type.fileDown, errorMsg));
    }

    private void progress(long currentPoint, long endPoint) {
        HttpLog.d(mUrlStr + " 下载进度：currentPoint = " + currentPoint + " endPoint = " + endPoint);
        if (checkListener())
            httpListener.progress(flagCode, flagStr, responseObjectProgress, currentPoint, endPoint);
    }

}