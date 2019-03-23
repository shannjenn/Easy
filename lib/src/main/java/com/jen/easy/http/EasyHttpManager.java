package com.jen.easy.http;

import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpFullListener;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyHttpDownloadRequest;
import com.jen.easy.http.request.EasyHttpRequest;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
abstract class EasyHttpManager {
    private ExecutorService pool;
    private int maxThreadSize;
    private boolean isShutdown;
    private EasyHttpListener httpBaseListener;

    EasyHttpManager() {
        maxThreadSize = 9;
        init(maxThreadSize);
    }

    EasyHttpManager(int maxThreadSize) {
        init(maxThreadSize);
    }

    /**
     * @param maxThreadSize 最大同时请求数量
     */
    private void init(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    public void start(EasyHttpRequest request) {
        start(request, 0, null);
    }

    public void start(EasyHttpRequest request, int flagCode) {
        start(request, flagCode, null);
    }

    public void start(EasyHttpRequest request, String flagStr) {
        start(request, 0, flagStr);
    }

    /**
     * 开始
     *
     * @param request  请求对象
     * @param flagCode 请求标识
     * @param flagStr  请求标识
     */
    public void start(EasyHttpRequest request, int flagCode, String flagStr) {
        if (isShutdown) {
            HttpLog.w("线程池已经关闭，不可以再操作 start");
            httpBaseListener.fail(flagCode, flagStr, "");
        } else if (request instanceof EasyHttpDataRequest) {
            request.setRequestStatus(EasyRequestStatus.running);
            URLConnectionDataRunnable base = new URLConnectionDataRunnable((EasyHttpDataRequest) request, httpBaseListener, flagCode, flagStr);
            pool.execute(base);
        } else if (request instanceof EasyHttpDownloadRequest) {
            EasyHttpFullListener fullListener = null;
            if (httpBaseListener instanceof EasyHttpFullListener) {
                fullListener = (EasyHttpFullListener) httpBaseListener;
            }
            URLConnectionDownloadRunnable download = new URLConnectionDownloadRunnable((EasyHttpDownloadRequest) request, fullListener, flagCode, flagStr);
            pool.execute(download);
        } else if (request instanceof EasyHttpUploadRequest) {
            EasyHttpFullListener fullListener = null;
            if (httpBaseListener instanceof EasyHttpFullListener) {
                fullListener = (EasyHttpFullListener) httpBaseListener;
            }
            URLConnectionUploadRunnable upload = new URLConnectionUploadRunnable((EasyHttpUploadRequest) request, fullListener, flagCode, flagStr);
            pool.execute(upload);
        } else {
            if (request == null) {
                HttpLog.w("参数不能为空");
            } else {
                HttpLog.w("请求参数类型错误，请继承:" + EasyHttpRequest.class.getName() + " 或者子类");
            }
            httpBaseListener.fail(flagCode, flagStr, "");
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

    public void setListener(EasyHttpListener httpBaseListener) {
        if (isShutdown) {
            HttpLog.w("线程池已经关闭，不可以再操作 setListener");
            return;
        }
        this.httpBaseListener = httpBaseListener;
    }

}
