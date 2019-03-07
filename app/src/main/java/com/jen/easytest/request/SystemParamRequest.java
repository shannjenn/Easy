package com.jen.easytest.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyRequest;
import com.jen.easy.invalid.EasyInvalid;

/**
 * 系统基本参数 请求
 * 协议类型请手动设置SystemParamStrResponse
 *
 * @author Created by zs on 2018/10/22.
 */
@EasyHttpPost(UrlAppend = "http://113.98.203.72:9003/sys/getParam", Response = SystemParamResponse.class)
public class SystemParamRequest extends BaseRequestWeb {
    @EasyInvalid
    public final static String PARAM_ID_1 = "1";//银行卡
    @EasyInvalid
    public final static String PARAM_ID_2 = "2";//职业类型
    @EasyInvalid
    public final static String PARAM_ID_3 = "3";//教育程度
    @EasyInvalid
    public final static String PARAM_ID_4 = "4";//总资产
    @EasyInvalid
    public final static String PARAM_ID_5 = "5";//年收入
    @EasyInvalid
    public final static String PARAM_ID_6 = "6";//投资经验几年
    @EasyInvalid
    public final static String PARAM_ID_7 = "7";//风险承受能力
    @EasyInvalid
    public final static String PARAM_ID_8 = "8";//投资者类型
    @EasyInvalid
    public final static String PARAM_ID_9 = "9";//投资目标
    @EasyInvalid
    public final static String PARAM_ID_10 = "10";//投资经验
    @EasyInvalid
    public final static String PARAM_ID_11 = "11";//衍生品投资评估
    @EasyInvalid
    public final static String PARAM_ID_12 = "12";//行业
    @EasyInvalid
    public final static String PARAM_ID_13 = "13";//其他职业说明
    @EasyInvalid
    public final static String PARAM_ID_14 = "14";//客户协议内容
    @EasyInvalid
    public final static String PARAM_ID_15 = "15";//客户协议书
    @EasyInvalid
    public final static String PARAM_ID_16 = "16";//常设授权书
    @EasyInvalid
    public final static String PARAM_ID_17 = "17";//风险披露书
    @EasyInvalid
    public final static String PARAM_ID_18 = "18";//客户申明
    @EasyInvalid
    public final static String PARAM_ID_19 = "19";//税务自我证明
    @EasyInvalid
    public final static String PARAM_ID_20 = "20";//自我证明表格
    @EasyInvalid
    public final static String PARAM_ID_21 = "21";//p2p内容
    @EasyInvalid
    public final static String PARAM_ID_22 = "22";//播放器页面文本说明 开户
    @EasyInvalid
    public final static String PARAM_ID_23 = "23";//播放器媒体文件地址
    @EasyInvalid
    public final static String PARAM_ID_50 = "50";//客户协议书 开户
    @EasyInvalid
    public final static String PARAM_ID_51 = "51";//客户声明 开户
    @EasyInvalid
    public final static String PARAM_ID_52 = "52";//附件二《常设授权书》 开户
    @EasyInvalid
    public final static String PARAM_ID_53 = "53";//附件三《风险披露声明》 开户
    @EasyInvalid
    public final static String PARAM_ID_54 = "54";//附件五《税务自我证明声明》 开户
    @EasyInvalid
    public final static String PARAM_ID_55 = "55";//附件五《自我证明表格》 开户
    @EasyInvalid
    public final static String PARAM_ID_56 = "56";//衍生品 客户声明
    @EasyInvalid
    public final static String PARAM_ID_57 = "57";//开通账户类型
    @EasyInvalid
    public final static String PARAM_ID_91 = "91";//选股器 协议
    @EasyInvalid
    public final static String PARAM_ID_95 = "95";//市场类型列表
    @EasyInvalid
    public final static String PARAM_ID_96 = "96";//
    @EasyInvalid
    public final static String PARAM_ID_99 = "99";//条件下单 协议

    @EasyRequest("params")
    public Param param;

    public SystemParamRequest() {
        super();
        id = "1550219235145000820";
        requestSrc = "Android";
        version = "1.0";
        sessionId = "9dd2add9116c4010837bee1d8e1f43ca14091";

        param = new Param();
        param.sessionId = sessionId;

        addResponseReplaceString(" ", "");
        addResponseReplaceString("\\t", "");
        addResponseReplaceString("\\n", "");
        addResponseReplaceString("\\r", "");
        addResponseReplaceString("\\", "");
        addResponseReplaceString("\"{", "{");
        addResponseReplaceString("\"[", "[");
        addResponseReplaceString("]\"", "]");
        addResponseReplaceString("}\"", "}");

    }

    public static class Param {
        private String paramId;//多个可用逗号隔开："paramId": "1,2,3,4,5,6,7,8,9,10,11,12,57,13"
        protected String sessionId;

        public String getParamId() {
            return paramId == null ? "" : paramId;
        }

        public void setParamId(String paramId) {
            this.paramId = paramId;
        }

        public String getSessionId() {
            return sessionId == null ? "" : sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}
