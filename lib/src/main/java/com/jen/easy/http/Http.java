package com.jen.easy.http;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求
 */
public class Http extends HttpManager {

    /**
     * 新建实例
     */
    public Http() {
        super();
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
     * 设置默认请求方式
     *
     * @param method get/post
     */
    @Override
    public void setMethod(String method) {
        super.setMethod(method);
    }

    /**
     * 设置默认编码
     *
     * @param charset 编码(如：UTF-8)
     */
    /*@Override
    public void setCharset(String charset) {
        super.setCharset(charset);
    }*/

    /**
     * 设置默认
     *
     * @param contentType 传输类型
     */
    /*@Override
    public void setContentType(String contentType) {
        super.setContentType(contentType);
    }*/

    /**
     * 设置默认超时
     *
     * @param timeout 时间
     */
    @Override
    public void setTimeout(int timeout) {
        super.setTimeout(timeout);
    }

    /**
     * 设置默认读取超时
     *
     * @param readTimeout 读取时间
     */
    @Override
    public void setReadTimeout(int readTimeout) {
        super.setReadTimeout(readTimeout);
    }

    /**
     * 设置默认字符编码连接参数
     *
     * @param connection 连接参数
     */
    /*@Override
    public void setConnection(String connection) {
        super.setConnection(connection);
    }*/

    /**
     * 设置线程池最大数量
     *
     * @param maxThreadSize 数量
     */
    @Override
    public void setMaxThreadSize(int maxThreadSize) {
        super.setMaxThreadSize(maxThreadSize);
    }
}
