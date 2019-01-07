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
        EasyLog.d(TAG.EasyHttp, mUrlStr + "  Http请求返回码：" + mResponseCode);
        if (mResponseCode == 200) {
            long curBytes = request.startPoint;
            request.endPoint = connection.getContentLength();
            InputStream inStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            RandomAccessFile randFile = new RandomAccessFile(request.filePath, "rwd");
            randFile.seek(request.startPoint);
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                randFile.write(buffer, 0, len);
                curBytes += len;
                if (mRequest.status != HttpRequestStatus.RUN) {
                    break;
                } else {
                    progress(curBytes, request.endPoint);
                }
            }
            if (mRequest.status != HttpRequestStatus.RUN) {
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            request.status = HttpRequestStatus.FINISH;
            if (curBytes == request.endPoint) {
                success(null, null);
            } else {
                fail("下载失败：" + mResponseCode + " curBytes = " + curBytes + " endPoint = " + request.endPoint);
            }
        } else {
            if (mRequest.status != HttpRequestStatus.RUN) {
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            request.status = HttpRequestStatus.FINISH;
            fail("下载失败：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        EasyLog.d(TAG.EasyHttp, mUrlStr + " 下载成功！");
        if (downloadListener != null) {
            HttpDownloadRequest request = (HttpDownloadRequest) mRequest;
            downloadListener.success(mRequest.flagCode, mRequest.flagStr, request.filePath);
        }
    }

    @Override
    protected void fail(String msg) {
        EasyLog.w(TAG.EasyHttp, mUrlStr + " " + msg);
        if (downloadListener != null && mRequest.status == HttpRequestStatus.RUN) {
            downloadListener.fail(mRequest.flagCode, mRequest.flagStr, msg);
        }
    }

    private void progress(long currentPoint, long endPoint) {
        if (downloadListener != null && mRequest.status == HttpRequestStatus.RUN) {
            downloadListener.progress(mRequest.flagCode, mRequest.flagStr, currentPoint, endPoint);
        }
    }
}