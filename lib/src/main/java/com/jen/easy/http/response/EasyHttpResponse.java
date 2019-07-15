package com.jen.easy.http.response;

import com.jen.easy.invalid.EasyInvalid;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：http返回参数 用于解析优化
 */
@EasyInvalid
public abstract class EasyHttpResponse {
    private String errorMsg;

    /**
     * 解析状态
     */
    private EasyResponseState responseState = EasyResponseState.ready;

    public EasyResponseState getResponseState() {
        return responseState;
    }

    public void setResponseState(EasyResponseState responseState) {
        this.responseState = responseState;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
