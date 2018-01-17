package com.jen.easytest.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.HttpHeadResponse;

/**
 * 作者：ShannJenn
 * 时间：2018/1/17:19:39
 * 说明：
 */

public class AirBaseResponse extends HttpHeadResponse{

    @EasyMouse.HTTP.ResponseParam("message")
    protected String message;
}
