package com.jen.easy.http;

import com.jen.easy.EasyRequest;
import com.jen.easy.EasyRequestType;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public abstract class HttpBasicRequest extends HttpRequest {

    @EasyRequest(value = "Content-Type", type = EasyRequestType.Head)
    protected String head_content_type = "application/json";

}
