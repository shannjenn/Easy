package com.jen.easy.http.response;

import com.jen.easy.invalid.EasyInvalid;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：http返回参数 用于解析优化
 */
@EasyInvalid
public abstract class EasyHttpFileResponse extends EasyHttpResponse {
    private String filePath;

    public String getFilePath() {
        return filePath == null ? "" : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
