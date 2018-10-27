package com.jen.easy.http;

import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2018/10/27.
 * 说明：Json工具类
 */
public class JsonUtil {

    /**
     * 转Json
     *
     * @param object 对象数据
     */
    public static JSONObject requstToJson(Object object) {
        JSONObject jsonParam = new JSONObject();
        if (object == null) {
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
            return jsonParam;
        }
        Map<String, String> urls = new HashMap<>();
        Map<String, String> heads = new HashMap<>();

        HttpReflectManager.getRequestParams(new ArrayList<String>(), object, urls, jsonParam, heads);
        return jsonParam;
    }

}
