package com.jen.easy.http.imp;

import com.jen.easy.http.param.factory.ParamFather;

/**
 * Created by Jen on 2017/7/21.
 */

public interface HttpImp {

    /**
     * 开始
     *
     * @param param
     */
    void start(ParamFather param);

    /**
     * 设置默认请求方式
     *
     * @param method
     */
    void setDefaultMethod(String method);

    /**
     * 设置默认编码
     *
     * @param charset
     */
    void setDefaultCharset(String charset);


    /**
     * 设置默认
     *
     * @param contentType
     */
    void setDefaultContentType(String contentType);

    /**
     * 设置默认超时
     *
     * @param timeout
     */
    void setDefaultTimeout(int timeout);

    /**
     * 设置默认读取超时
     *
     * @param readTimeout
     */
    void setDefaultReadTimeout(int readTimeout);

    /**
     * 设置默认字符编码连接参数
     *
     * @param connection
     */
    void setDefaultConnection(String connection);

    /**
     * 设置线程池最大数量
     *
     * @param maxThreadSize
     */
    void setMaxThreadSize(int maxThreadSize);

}
