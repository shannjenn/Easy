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
    private int maxThreadSize = 100;

    protected HttpManager() {
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
            HttpURLConnectionUploadRunable upload = new HttpURLConnectionUploadRunable((HttpUploadRequest) request);
            pool.execute(upload);
        } else if (request instanceof HttpDownloadRequest) {
            HttpURLConnectionDownloadRunable download = new HttpURLConnectionDownloadRunable((HttpDownloadRequest) request);
            pool.execute(download);
        } else {
            HttpURLConnectionBaseRunable base = new HttpURLConnectionBaseRunable((HttpBaseRequest) request);
            pool.execute(base);
        }
    }

    /**
     * 设置线程池最大数量
     *
     * @param maxThreadSize
     */
    protected void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

}
