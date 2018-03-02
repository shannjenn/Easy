package com.jen.easy.http;

import com.jen.easy.log.EasyLibLog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class HttpURLConnectionDownloadRunnable extends HttpURLConnectionRunnable {

    HttpURLConnectionDownloadRunnable(HttpDownloadRequest request) {
        super(request, "HttpDownload : ");
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (request.flag.startPoint <= 1024 * 2) {
            request.flag.startPoint = 0;
        } else if (request.flag.startPoint > 1024 * 2) {
            request.flag.startPoint = request.flag.startPoint - 1024 * 2;
        }

        if (request.flag.isBreak && request.flag.endPoint > request.flag.startPoint + 100) {
            connection.setRequestProperty("Range", "bytes=" + request.flag.startPoint + "-" + request.flag.endPoint);
        }

        if (!mIsGet && mHasParam) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(mRequestParam);
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        EasyLibLog.d(TAG + mUrlStr + "  Http请求返回码：" + mResponseCode);
        if (mResponseCode == 200) {
            long curBytes = request.flag.startPoint;
            request.flag.endPoint = connection.getContentLength();
            InputStream inStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            RandomAccessFile randFile = new RandomAccessFile(request.flag.filePath, "rwd");
            randFile.seek(request.flag.startPoint);
            int len;
            while ((len = inStream.read(buffer)) != -1 && !request.flag.userCancel) {
                randFile.write(buffer, 0, len);
                curBytes += len;
                if (!request.flag.userCancel) {
                    progress(curBytes, request.flag.endPoint);
                } else {
                    break;
                }
            }
            if (request.flag.userCancel) {
                fail("下载失败：用户取消下载");
            } else if (curBytes == request.flag.endPoint) {
                success(null, null);
            } else {
                fail("下载失败：" + mResponseCode + " curBytes = " + curBytes + " endPoint = " + request.flag.endPoint);
            }
        } else {
            fail("下载失败：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        EasyLibLog.d(TAG + mUrlStr + " 下载成功！");
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (request.getDownloadListener() != null)
            request.getDownloadListener().success(request.flag.code, request.flag.str, request.flag.filePath);
    }

    @Override
    protected void fail(String msg) {
        EasyLibLog.e(TAG + mUrlStr + " " + msg);
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (request.getDownloadListener() != null)
            request.getDownloadListener().fail(request.flag.code, request.flag.str, msg);
    }

    private void progress(long currentPoint, long endPoint) {
        HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
        if (request.getDownloadListener() != null)
            request.getDownloadListener().progress(request.flag.code, request.flag.str, currentPoint, endPoint);
    }
}