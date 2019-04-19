package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpFullListener;
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
    private EasyHttpFullListener fullListener;

    URLConnectionDownloadRunnable(EasyHttpDownloadRequest request, EasyHttpFullListener fullListener, int flagCode, String flagStr) {
        super(request, flagCode, flagStr);
        this.fullListener = fullListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
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
            if (mRequest.getRequestState() == EasyRequestState.interrupt) {
                HttpLog.d(mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            request.setRequestState(EasyRequestState.finish);
            if (curBytes == request.endPoint) {
                success(request.filePath, headMap);
            } else {
                fail("下载失败：" + mResponseCode + " curBytes = " + curBytes + " endPoint = " + request.endPoint);
            }
        } else {
            if (mRequest.getRequestState() == EasyRequestState.interrupt) {
                HttpLog.d(mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            request.setRequestState(EasyRequestState.finish);
            fail("下载失败：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        HttpLog.d(mUrlStr + " 下载成功！");
        if (fullListener != null) {
            fullListener.success(flagCode, flagStr, result, headMap);
        }
    }

    @Override
    protected void fail(String msg) {
        HttpLog.w(mUrlStr + " " + msg);
        if (fullListener != null) {
            fullListener.fail(flagCode, flagStr, msg);
        }
    }

    private void progress(long currentPoint, long endPoint) {
        if (fullListener != null) {
            fullListener.progress(flagCode, flagStr, currentPoint, endPoint);
        }
    }
}