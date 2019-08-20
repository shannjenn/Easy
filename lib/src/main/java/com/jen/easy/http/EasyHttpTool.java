package com.jen.easy.http;

import org.json.JSONObject;

/**
 * 作者：ShannJenn
 * 时间：2018/10/25.
 * 说明：网络请求工具类
 */
public class EasyHttpTool {

    /**
     * 请求参数转JSONObject
     *
     * @param object 对象数据
     */
    public static JSONObject parseRequstBody(Object object) {
        JSONObject jsonParam = new JSONObject();
        if (object == null) {
            HttpLog.e(" parseRequstBody 参数不能为空");
            return jsonParam;
        }
        HttpReflectRequestManager.RequestObject requestObject = HttpReflectRequestManager.getRequestHeadAndBody(object);
        jsonParam = requestObject.body;
        return jsonParam;
    }

    /**
     * 解析Http返回数据
     *
     * @param tClass 解析的实体
     * @param obj    数据
     * @param <T>    T
     * @return T
     */
    public static <T> T parseResponse(Class<T> tClass, String obj) {
        HttpParseManager parseManager = new HttpParseManager();
        return parseManager.parseResponseBody(tClass, obj);
    }
}
