package com.jen.easy.http;

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

        public int timeout = -1;
        public int readTimeout = -1;

        public boolean doOutput = false;
        public boolean doInput = true;
        public boolean useCaches = false;
    }


}
