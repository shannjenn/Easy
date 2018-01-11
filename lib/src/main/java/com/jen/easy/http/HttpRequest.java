package com.jen.easy.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.constant.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http请求参数
 */
abstract class HttpRequest {

    @EasyMouse.HTTP.RequestParam(noReq = true)
    public HttpParam httpParam = new HttpParam();

    /**
     * http请求参数
     */
    public final class HttpParam {
        /**
         * 可以动态设置
         */
        public String url;
//        public String method;

        /**
         * 可以动态设置地址拼接（注意：不可以单独使用，必须有前面地址）
         */
        public String urlAppend;

        /**
         * 请求参数设置，包括请求头
         */
        public final Map<String, String> propertys = new HashMap<>();

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
        public String charset = Constant.Unicode.DEFAULT;

        /**
         * 是否使用caches(默认false)
         */
        public boolean useCaches = false;
    }

}
