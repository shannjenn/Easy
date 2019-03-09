package com.jen.easy.http;

import com.jen.easy.log.EasyLog;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2018/11/28.
 * 说明：
 */
public class TestHttp {

    public static JSONObject httpReflectManager_test(Object request) {
        HttpReflectRequestManager.RequestObject requestObject = HttpReflectRequestManager.getRequestHeadAndBody(request);
        EasyLog.d(requestObject.body.toString());
        return requestObject.body;
    }

    public static <T> T httpParseManager_test(Class<T> tClass, String obj, Map<String, List<String>> headMap) {
        HttpParseManager parseManager = new HttpParseManager();
        T t = parseManager.parseResponseFromJSONString(tClass, obj, headMap);
        return t;
    }
}
