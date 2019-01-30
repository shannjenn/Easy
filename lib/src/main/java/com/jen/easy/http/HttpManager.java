package com.jen.easy.http;

import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.http.imp.HttpUploadListener;

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
    private HttpBaseListener httpBaseListener;
    private HttpDownloadListener httpDownloadListener;
    private HttpUploadListener httpUploadListener;

    HttpManager() {
        maxThreadSize = 9;
        init(maxThreadSize);
    }

    HttpManager(int maxThreadSize) {
        init(maxThreadSize);
    }

    /**
     * @param maxThreadSize 最大同时请求数量
     */
    private void init(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    public void start(HttpRequest request) {
        start(request, 0, null);
    }

    public void start(HttpRequest request, int flagCode) {
        start(request, flagCode, null);
    }

    public void start(HttpRequest request, String flagStr) {
        start(request, 0, flagStr);
    }

    /**
     * 开始
     *
     * @param request  请求对象
     * @param flagCode 请求标识
     * @param flagStr  请求标识
     */
    public void start(HttpRequest request, int flagCode, String flagStr) {
        if (isShutdown) {
            HttpLog.w("线程池已经关闭，不可以再操作 start");
            return;
        }
        if (request == null) {
            HttpLog.w("参数不能为空");
            return;
        }
        request.requestStatus = RequestStatus.running;
        if (request instanceof HttpBaseRequest) {
            HttpURLConnectionBaseRunnable base = new HttpURLConnectionBaseRunnable((HttpBaseRequest) request, httpBaseListener, flagCode, flagStr);
            pool.execute(base);
        } else if (request instanceof HttpDownloadRequest) {
            HttpURLConnectionDownloadRunnable download = new HttpURLConnectionDownloadRunnable((HttpDownloadRequest) request, httpDownloadListener, flagCode, flagStr);
            pool.execute(download);
        } else if (request instanceof HttpUploadRequest) {
            HttpURLConnectionUploadRunnable upload = new HttpURLConnectionUploadRunnable((HttpUploadRequest) request, httpUploadListener, flagCode, flagStr);
            pool.execute(upload);
        } else {
            HttpLog.e("请求参数类型错误，请继承正确:" + request.getClass().getName());
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

    public void setHttpBaseListener(HttpBaseListener httpBaseListener) {
        if (isShutdown) {
            HttpLog.w("线程池已经关闭，不可以再操作 setHttpBaseListener");
            return;
        }
        this.httpBaseListener = httpBaseListener;
    }

    public void setHttpDownloadListener(HttpDownloadListener httpDownloadListener) {
        if (isShutdown) {
            HttpLog.w("线程池已经关闭，不可以再操作 setHttpDownloadListener");
            return;
        }
        this.httpDownloadListener = httpDownloadListener;
    }

    public void setHttpUploadListener(HttpUploadListener httpUploadListener) {
        if (isShutdown) {
            HttpLog.w("线程池已经关闭，不可以再操作 setHttpUploadListener");
            return;
        }
        this.httpUploadListener = httpUploadListener;
    }

}
