package com.jen.easy.http;

import com.jen.easy.Easy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public abstract class HttpBaseRequest extends HttpRequest {

    @Easy.HTTP.RequestParam(value = "Content-Type", type = Easy.HTTP.TYPE.HEAD)
    protected String HEAD_CONTENT_TYPE = "application/Json;charset=UTF-8";

}
