package com.jen.easy.http;

import com.jen.easy.Easy;

import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http返回参数
 */
public abstract class HttpHeadResponse extends HttpResponse {

    @Easy.HTTP.ResponseParam(noResp = true)
    protected Map<String, List<String>> heads;

    public Map<String, List<String>> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, List<String>> heads) {
        this.heads = heads;
    }
}
