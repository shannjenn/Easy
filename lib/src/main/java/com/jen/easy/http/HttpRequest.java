package com.jen.easy.http;

import com.jen.easy.constant.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http请求参数
 */
abstract class HttpRequest {
    public HTTP http = new HTTP();

    public final class HTTP {
        public String url;
        public String method;
        public final Map<String, String> propertys = new HashMap<>();

        public int timeout = 30000;
        public int readTimeout = 30000;
        public String charset = Constant.Unicode.DEFAULT;

        public boolean useCaches = false;
    }


}
