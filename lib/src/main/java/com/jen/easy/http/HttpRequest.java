package com.jen.easy.http;

import com.jen.easy.Easy;
import com.jen.easy.constant.Unicode;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http请求参数
 */
@Easy.HTTP.NoRequestParam
public abstract class HttpRequest {

    /**
     * 请求标识
     */
    public int flagCode;

    /**
     * 请求标识
     */
    public String flagStr;

    /**
     * 可以动态设置
     */
    public String url;

    /**
     * 请求超时
     */
    public int timeout = 30000;

    /**
     * 读取超时
     */
    public int readTimeout = 30000;

    /**
     * 默认编码(默认ut-8)
     */
    public String charset = Unicode.DEFAULT;

    /**
     * 是否使用caches(默认false)
     */
    public boolean useCaches = false;

    /**
     * 关闭请求
     */
    boolean closeRequest;
}
