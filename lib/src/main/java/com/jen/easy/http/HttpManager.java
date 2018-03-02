package com.jen.easy.http;

import com.jen.easy.constant.TAG;
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
