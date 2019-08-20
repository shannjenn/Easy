package com.jen.easy.http.response;

import com.jen.easy.invalid.EasyInvalid;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：http返回参数 用于解析优化
 */
@EasyInvalid
public abstract class EasyHttpResponse {
    private int responseCode;
    private String requestLog;//请求log日志

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getRequestLog() {
        return requestLog == null ? "" : requestLog;
    }

    public void setRequestLog(String requestLog) {
        this.requestLog = requestLog;
    }
}
