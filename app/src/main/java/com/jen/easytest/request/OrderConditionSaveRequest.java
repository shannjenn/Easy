package com.jen.easytest.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyRequest;
import com.jen.easy.http.HttpBaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件下单 新增条件
 * 请求
 *
 * @author Created by zs on 2018/10/22.
 */
@EasyHttpPost(UrlAppend = "http://www.baald", Response = OrderConditionSaveResponse.class)
public class OrderConditionSaveRequest extends HttpBaseRequest {
    private String requestSrc = "Android";
    private String sessionId;
    protected String id;
    protected String version;
    protected String sign;

    public void setSign() {
//        sign = super.getSign();
    }

    @EasyRequest("params")
    public List<OrderRuleModel> params;

    public OrderConditionSaveRequest() {
//        id = XGUtils.getRequestId(XGApplication.getApplication());
//        requestSrc = "Android";
//        version = Constant.API_VERSION;

        params = new ArrayList<>();

//        String userCode = XGApplication.getApplication().getMyInfo().getUserCode();
//        try {
//            long code = Long.parseLong(userCode);
//            orderRuleModel.setUserId(code);
//        } catch (NumberFormatException e) {
//            EasyLog.e("NumberFormatException userCode：" + userCode);
//            e.printStackTrace();
//        }

//        orderRuleModel.setUserId(XGApplication.getApplication().getMyInfo().getUserId());
    }


}
