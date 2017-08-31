package com.jen.easy.http;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class HttpParam {
    public HTTP http = new HTTP();

    public final class HTTP {
        public String url;
        public String method;
        public String charset;
        public String contentType;
        public String connection;

        public int timeout = -1;
        public int readTimeout = -1;

        public boolean doOutput = false;
        public boolean doInput = true;
        public boolean useCaches = false;
    }
}
