package com.jen.easy.http;

import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpRequest;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
public class EasyHttp extends EasyHttpManager {

    /**
     * 新建实例
     * 默认线程池数量
     */
    public EasyHttp() {
        super();
    }

    /**
     * 新建实例
     *
     * @param maxThreadSize 设置线程池最大数量
     */
    public EasyHttp(int maxThreadSize) {
        super(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request 请求参数
     */
    @Override
    public void start(EasyHttpRequest request) {
        super.start(request);
    }

    @Override
    public void start(EasyHttpRequest request, int flagCode) {
        super.start(request, flagCode);
    }

    @Override
    public void start(EasyHttpRequest request, String flagStr) {
        super.start(request, flagStr);
    }

    @Override
    public void start(EasyHttpRequest request, int flagCode, String flagStr) {
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
    public void setDataListener(EasyHttpListener httpBaseListener) {
        super.setDataListener(httpBaseListener);
    }

}
