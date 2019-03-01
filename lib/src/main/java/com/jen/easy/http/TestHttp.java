package com.jen.easy.http;

import com.jen.easy.log.EasyLog;

/**
 * 作者：ShannJenn
 * 时间：2018/11/28.
 * 说明：
 */
public class TestHttp {

    public static void HttpReflectManager_getRequestParams(Object request) {
        HttpReflectRequestManager.RequestObject requestObject = HttpReflectRequestManager.getRequestHeadAndBody(request);
        EasyLog.d(requestObject.body.toString());
    }
}
