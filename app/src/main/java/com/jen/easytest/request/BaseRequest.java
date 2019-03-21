package com.jen.easytest.request;

import com.jen.easy.http.request.EasyHttpDataRequest;

/**
 * 所有请求继承该类
 *
 * @author Created by zs on 2018/10/22.
 */
class BaseRequest extends EasyHttpDataRequest {
    /*基础参数*/
    protected String id;
    protected String requestSrc;
    protected String version;
    protected String sessionId;

    protected String sign;//签名

//    BaseRequest() {
//        id = "1550219235145000820";
//        requestSrc = "Android";
//        version = Constant.API_VERSION;
//        sessionId = getSessionId();
//    }

//    public void setSign() {
//        JSONObject obj = EasyHttpTool.parseRequst(this);
//        sign = HttpSecurity.getInstance().sign(obj);
//    }
//
//    protected String getId() {
//        return XGUtils.getRequestId(XGApplication.getApplication());
//    }
//
//    protected String getSessionId() {
//        return XGApplication.getApplication().getSessionId();
//    }
//
//    protected String getClientId() {
//        return XGApplication.getApplication().getMyInfo().getTrdAccount();
//    }
//
//    protected String getOpStation() {
//        return CommonUtils.getUUID(XGApplication.getApplication());
//    }
//
//    protected String getChanalId() {
//        return ADFSocketUtil.getInstance(XGApplication.getApplication()).getChannalId();
//    }
}
