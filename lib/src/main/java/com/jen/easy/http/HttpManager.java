package com.jen.easy.http;

import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
import com.jen.easy.http.imp.HttpBasicListener;
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
    private boolean isShutdown;
    private HttpBasicListener httpBaseListener;
    private HttpDownloadListener httpDownloadListener;
    private HttpUploadListener httpUploadListener;

    public HttpManager(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request 请求对象
     */
    public void start(HttpRequest request) {
        if (isShutdown) {
            EasyLog.i("线程池已经关闭，不可以再操作 start");
            return;
        }
        if (request == null) {
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
            return;
        }
        request.status = HttpRequestStatus.RUN;
        if (request instanceof HttpBasicRequest) {
            HttpURLConnectionBasicRunnable base = new HttpURLConnectionBasicRunnable((HttpBasicRequest) request, httpBaseListener);
            pool.execute(base);
        } else if (request instanceof HttpDownloadRequest) {
            HttpURLConnectionDownloadRunnable download = new HttpURLConnectionDownloadRunnable((HttpDownloadRequest) request, httpDownloadListener);
            pool.execute(download);
        } else if (request instanceof HttpUploadRequest) {
            HttpURLConnectionUploadRunnable upload = new HttpURLConnectionUploadRunnable((HttpUploadRequest) request, httpUploadListener);
            pool.execute(upload);
        } else {
            EasyLog.w(TAG.EasyHttp, "HttpRequest 错误");
            Throw.exception(ExceptionType.IllegalArgumentException, "请求参数类型错误，请继承正确:" + request.getClass().getName());
        }
    }

    /**
     * 关闭后所有线程都不能再执行
     */
    public void shutdown() {
        if (isShutdown) {
            return;
        }
        isShutdown = true;
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

    public boolean isShutdown() {
        return isShutdown;
    }

    public int getMaxThreadSize() {
        return maxThreadSize;
    }

    public HttpBasicListener getHttpBaseListener() {
        return httpBaseListener;
    }

    public void setHttpBaseListener(HttpBasicListener httpBaseListener) {
        if (isShutdown) {
            EasyLog.i("线程池已经关闭，不可以再操作 setHttpBaseListener");
            return;
        }
        this.httpBaseListener = httpBaseListener;
    }

    public HttpDownloadListener getHttpDownloadListener() {
        return httpDownloadListener;
    }

    public void setHttpDownloadListener(HttpDownloadListener httpDownloadListener) {
        if (isShutdown) {
            EasyLog.i("线程池已经关闭，不可以再操作 setHttpDownloadListener");
            return;
        }
        this.httpDownloadListener = httpDownloadListener;
    }

    public HttpUploadListener getHttpUploadListener() {
        return httpUploadListener;
    }

    public void setHttpUploadListener(HttpUploadListener httpUploadListener) {
        if (isShutdown) {
            EasyLog.i("线程池已经关闭，不可以再操作 setHttpUploadListener");
            return;
        }
        this.httpUploadListener = httpUploadListener;
    }
}
