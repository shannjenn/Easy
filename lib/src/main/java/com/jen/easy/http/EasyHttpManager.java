package com.jen.easy.http;

import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyHttpDownloadRequest;
import com.jen.easy.http.request.EasyHttpRequest;
import com.jen.easy.http.request.EasyHttpUploadRequest;
import com.jen.easy.http.request.EasyRequestState;

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
    private EasyHttpListener httpListener;

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
            HttpLog.w("你已经调用shutdown()关闭线程池，不可以再操作请求，错误信息位置：EasyHttp.start");
        } else if (request instanceof EasyHttpDataRequest) {
            request.setRequestState(EasyRequestState.running);
            URLConnectionDataRunnable base = new URLConnectionDataRunnable((EasyHttpDataRequest) request, httpListener, flagCode, flagStr);
            pool.execute(base);
        } else if (request instanceof EasyHttpDownloadRequest) {
            request.setRequestState(EasyRequestState.running);
            URLConnectionDownloadRunnable download = new URLConnectionDownloadRunnable((EasyHttpDownloadRequest) request, httpListener, flagCode, flagStr);
            pool.execute(download);
        } else if (request instanceof EasyHttpUploadRequest) {
            request.setRequestState(EasyRequestState.running);
            URLConnectionUploadRunnable upload = new URLConnectionUploadRunnable((EasyHttpUploadRequest) request, httpListener, flagCode, flagStr);
            pool.execute(upload);
        } else {
            String errorMsg;
            if (request == null) {
                errorMsg = "请求对象不能为空";
            } else {
                errorMsg = "请求对象类型错误，请继承:" + EasyHttpRequest.class.getName() + "子类:数据"
                        + EasyHttpDataRequest.class.getName() + "或上传" + EasyHttpUploadRequest.class.getName()
                        + "或下载" + EasyHttpDownloadRequest.class.getName();
            }
            HttpLog.e(errorMsg);
            if (httpListener != null) {
                httpListener.fail(flagCode, flagStr, request, errorMsg);
            }
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
        httpListener = null;
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

    public void setListener(EasyHttpListener httpListener) {
        if (isShutdown) {
            HttpLog.w("你已经调用shutdown()关闭线程池，不可以再操作请求，错误信息位置：EasyHttp.setListener");
            return;
        }
        this.httpListener = httpListener;
    }

}
