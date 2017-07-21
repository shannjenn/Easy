package com.jen.easy.http.param;

import com.jen.easy.http.listener.EasyHttpListener;

/**
 * Created by Jen on 2017/7/21.
 */

public abstract class EasyHttpParam {
    private EasyHttpListener easyHttpListener;

    private int flagCode;
    private String flag;

    private String url;
    private String method;
    private String charset;
    private String contentType;

    private int timeout = -1;
    private int readTimeout = -1;

    private boolean doOutput = false;
    private boolean doInput = true;
    private boolean useCaches = false;

    public int getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(int flagCode) {
        this.flagCode = flagCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public boolean isDoOutput() {
        return doOutput;
    }

    public void setDoOutput(boolean doOutput) {
        this.doOutput = doOutput;
    }

    public boolean isDoInput() {
        return doInput;
    }

    public void setDoInput(boolean doInput) {
        this.doInput = doInput;
    }

    public boolean isUseCaches() {
        return useCaches;
    }

    public void setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
    }

    public EasyHttpListener getEasyHttpListener() {
        return easyHttpListener;
    }

    public void setEasyHttpListener(EasyHttpListener easyHttpListener) {
        this.easyHttpListener = easyHttpListener;
    }
}
