package com.jen.easy.http.param.factory;

/**
 * Created by Jen on 2017/7/26.
 */

public final class FinalHttpPram {
    FinalHttpPram() {
    }

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
