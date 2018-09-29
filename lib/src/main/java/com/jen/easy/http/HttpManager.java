package com.jen.easy.http;

import com.jen.easy.constant.TAG;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.http.imp.HttpUploadListener;
import com.jen.easy.log.EasyLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
abstract class HttpManager {
    private ExecutorService pool;
    private int maxThreadSize;
    private HttpState state;
    private HttpBaseListener httpBaseListener;
    private HttpDownloadListener httpDownloadListener;
    private HttpUploadListener httpUploadListener;

    protected HttpManager(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        state = HttpState.RUN;
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request 请求对象
     */
    protected void start(HttpRequest request) {
        if (state == HttpState.STOP) {
            EasyLog.w("线程池已经关闭，不可以再操作 start");
            return;
        }
        if (request == null) {
            EasyLog.w(TAG.EasyHttp, "start 参数不能为空值");
            return;
        }
        request.state = state;//默认引用HttpManager state地址
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

    /**
     * 停止
     *
     * @param request 停止对象
     */
    protected void stop(HttpRequest request) {
        if (request == null) {
            EasyLog.w(TAG.EasyHttp, "参数不能为空值");
        } else {
            request.state = HttpState.STOP;
        }
    }

    /**
     * 关闭后所有线程都不能再执行
     */
    protected void shutdown() {
        if (state == HttpState.STOP) {
            return;
        }
        state = HttpState.STOP;
        httpBaseListener = null;
        httpDownloadListener = null;
        httpUploadListener = null;
        pool.shutdown();//禁用提交的新任务
        try {
            if (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();//（重新）取消当前线程是否中断
            Thread.currentThread().interrupt();//保持中断状态
        }
    }

    protected int getMaxThreadSize() {
        return maxThreadSize;
    }

    public HttpBaseListener getHttpBaseListener() {
        return httpBaseListener;
    }

    public void setHttpBaseListener(HttpBaseListener httpBaseListener) {
        if (state == HttpState.STOP) {
            EasyLog.w("线程池已经关闭，不可以再操作 setHttpBaseListener");
            return;
        }
        this.httpBaseListener = httpBaseListener;
    }

    public HttpDownloadListener getHttpDownloadListener() {
        return httpDownloadListener;
    }

    public void setHttpDownloadListener(HttpDownloadListener httpDownloadListener) {
        if (state == HttpState.STOP) {
            EasyLog.w("线程池已经关闭，不可以再操作 setHttpDownloadListener");
            return;
        }
        this.httpDownloadListener = httpDownloadListener;
    }

    public HttpUploadListener getHttpUploadListener() {
        return httpUploadListener;
    }

    public void setHttpUploadListener(HttpUploadListener httpUploadListener) {
        if (state == HttpState.STOP) {
            EasyLog.w("线程池已经关闭，不可以再操作 setHttpUploadListener");
            return;
        }
        this.httpUploadListener = httpUploadListener;
    }
}
