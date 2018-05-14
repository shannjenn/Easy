package com.jen.easy.http;

import com.jen.easy.constant.TAG;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.http.imp.HttpUploadListener;
import com.jen.easy.log.EasyLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
abstract class HttpManager {
    private ExecutorService pool;
    protected int maxThreadSize;
    private HttpBaseListener httpBaseListener;
    private HttpDownloadListener httpDownloadListener;
    private HttpUploadListener httpUploadListener;

    protected HttpManager(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request 请求对象
     */
    protected void start(HttpRequest request) {
        if (request == null) {
            EasyLog.w(TAG.EasyHttp, "start 参数为空");
            return;
        }
        if (request instanceof HttpBaseRequest) {
            HttpURLConnectionBaseRunnable base = new HttpURLConnectionBaseRunnable((HttpBaseRequest) request, httpBaseListener);
            pool.execute(base);
        } else if (request instanceof HttpDownloadRequest) {
            HttpURLConnectionDownloadRunnable download = new HttpURLConnectionDownloadRunnable((HttpDownloadRequest) request, httpDownloadListener);
            pool.execute(download);
        } else if (request instanceof HttpUploadRequest) {
            HttpURLConnectionUploadRunnable upload = new HttpURLConnectionUploadRunnable((HttpUploadRequest) request, httpUploadListener);
            pool.execute(upload);
        } else {
            EasyLog.w(TAG.EasyHttp, "HttpRequest 错误");
        }
    }

    protected int getMaxThreadSize() {
        return maxThreadSize;
    }

    public HttpBaseListener getHttpBaseListener() {
        return httpBaseListener;
    }

    public void setHttpBaseListener(HttpBaseListener httpBaseListener) {
        this.httpBaseListener = httpBaseListener;
    }

    public HttpDownloadListener getHttpDownloadListener() {
        return httpDownloadListener;
    }

    public void setHttpDownloadListener(HttpDownloadListener httpDownloadListener) {
        this.httpDownloadListener = httpDownloadListener;
    }

    public HttpUploadListener getHttpUploadListener() {
        return httpUploadListener;
    }

    public void setHttpUploadListener(HttpUploadListener httpUploadListener) {
        this.httpUploadListener = httpUploadListener;
    }
}
