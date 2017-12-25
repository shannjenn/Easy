package com.jen.easy.http;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
public class Http extends HttpManager {

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

    /**
     * 获取线程数
     *
     * @return 线程数
     */
    protected int getMaxThreadSize() {
        return maxThreadSize;
    }

}
