package com.jen.easy.http;

import com.jen.easy.log.EasyLibLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
abstract class HttpManager {
    private final String TAG = "HttpManager : ";
    private ExecutorService pool;
    protected int maxThreadSize;

    protected HttpManager(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request
     */
    protected void start(HttpRequest request) {
        if (request == null) {
            EasyLibLog.w(TAG + "start 参数为空");
            return;
        }
        if (request instanceof HttpUploadRequest) {
            HttpURLConnectionUploadRunnable upload = new HttpURLConnectionUploadRunnable((HttpUploadRequest) request);
            pool.execute(upload);
        } else if (request instanceof HttpDownloadRequest) {
            HttpURLConnectionDownloadRunnable download = new HttpURLConnectionDownloadRunnable((HttpDownloadRequest) request);
            pool.execute(download);
        } else {
            HttpURLConnectionBaseRunnable base = new HttpURLConnectionBaseRunnable((HttpBaseRequest) request);
            pool.execute(base);
        }
    }

    protected int getMaxThreadSize() {
        return maxThreadSize;
    }
}
