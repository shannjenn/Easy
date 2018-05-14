package com.jen.easy.http;

import com.jen.easy.constant.TAG;
import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.log.EasyLog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class HttpURLConnectionDownloadRunnable extends HttpURLConnectionRunnable {
    private HttpDownloadListener downloadListener;

    HttpURLConnectionDownloadRunnable(HttpDownloadRequest request, HttpDownloadListener downloadListener) {
        super(request);
        this.downloadListener = downloadListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (request.startPoint <= 1024 * 2) {
            request.startPoint = 0;
        } else if (request.startPoint > 1024 * 2) {
            request.startPoint = request.startPoint - 1024 * 2;
        }

        if (request.isBreak && request.endPoint > request.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.startPoint + "-" + request.endPoint);
        }

        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mJsonParam.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        EasyLog.d(TAG.EasyHttp, mUrlStr + "  Http请求返回码：" + mResponseCode);
        if (mResponseCode == 200) {
            long curBytes = request.startPoint;
            request.endPoint = connection.getContentLength();
            InputStream inStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            RandomAccessFile randFile = new RandomAccessFile(request.filePath, "rwd");
            randFile.seek(request.startPoint);
            int len;
            while ((len = inStream.read(buffer)) != -1 && !request.userCancel) {
                randFile.write(buffer, 0, len);
                curBytes += len;
                if (!request.userCancel) {
                    progress(curBytes, request.endPoint);
                } else {
                    break;
                }
            }
            if (request.userCancel) {
                fail("下载失败：用户取消下载");
            } else if (curBytes == request.endPoint) {
                success(null, null);
            } else {
                fail("下载失败：" + mResponseCode + " curBytes = " + curBytes + " endPoint = " + request.endPoint);
            }
        } else {
            fail("下载失败：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        EasyLog.d(TAG.EasyHttp, mUrlStr + " 下载成功！");
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (downloadListener != null)
            downloadListener.success(request.flagCode, request.flagStr, request.filePath);
    }

    @Override
    protected void fail(String msg) {
        EasyLog.w(TAG.EasyHttp, mUrlStr + " " + msg);
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (downloadListener != null)
            downloadListener.fail(request.flagCode, request.flagStr, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (downloadListener != null)
            downloadListener.progress(request.flagCode, request.flagStr, currentPoint, endPoint);
    }
}