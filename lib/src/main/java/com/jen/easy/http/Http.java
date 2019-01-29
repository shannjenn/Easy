package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.http.imp.HttpUploadListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
public class Http extends HttpManager {

    /**
     * 新建实例
     * 默认线程池数量
     */
    public Http() {
        super();
    }

    /**
     * 新建实例
     *
     * @param maxThreadSize 设置线程池最大数量
     */
    public Http(int maxThreadSize) {
        super(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request 请求参数
     */
    @Override
    public void start(HttpRequest request) {
        super.start(request);
    }

    @Override
    public void start(HttpRequest request, int flagCode) {
        super.start(request, flagCode);
    }

    @Override
    public void start(HttpRequest request, String flagStr) {
        super.start(request, flagStr);
    }

    @Override
    public void start(HttpRequest request, int flagCode, String flagStr) {
        super.start(request, flagCode, flagStr);
    }

    /**
     * 关闭后所有线程都不能再执行
     */
    public void shutdown() {
        super.shutdown();
    }

    /**
     * 所有线程是否关闭
     */
    @Override
    public boolean isShutdown() {
        return super.isShutdown();
    }

    /**
     * 获取线程数
     *
     * @return 线程数
     */
    public int getMaxThreadSize() {
        return super.getMaxThreadSize();
    }

    @Override
    public void setHttpBaseListener(HttpBaseListener httpBaseListener) {
        super.setHttpBaseListener(httpBaseListener);
    }

    @Override
    public void setHttpDownloadListener(HttpDownloadListener httpDownloadListener) {
        super.setHttpDownloadListener(httpDownloadListener);
    }

    @Override
    public void setHttpUploadListener(HttpUploadListener httpUploadListener) {
        super.setHttpUploadListener(httpUploadListener);
    }

}
