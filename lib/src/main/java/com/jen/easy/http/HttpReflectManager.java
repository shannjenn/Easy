package com.jen.easy.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.log.EasyLog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectManager {
    static String PARAM_TYPE = "param_type";
    static String PARAM_FIELD = "param_field";

    /**
     * 获取网络请求参数
     *
     * @param obj
     * @return
     */
    static Map<String, String> getRequestParams(Object obj) {
        Map<String, String> params = new HashMap<>();
        if (obj == null || obj instanceof Class) {
            EasyLog.e("getRequestParams obj is null");
            return params;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyMouse.HTTP.RequestParam.class);
            if (!isAnno)
                continue;
            EasyMouse.HTTP.RequestParam param = fields[i].getAnnotation(EasyMouse.HTTP.RequestParam.class);
            String paramName = param.value().trim();
            if (paramName.length() == 0) {
                continue;
            }
            try {
                String value = fields[i].get(obj) + "";
                params.put(paramName, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return params;
    }

    /**
     * 获取返回参数
     *
     * @param clazz
     * @return
     */
    static Map<String, Object> getResponseParams(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, String> param_type = new HashMap<>();
        Map<String, Field> param_field = new HashMap<>();
        objectMap.put(PARAM_TYPE, param_type);
        objectMap.put(PARAM_FIELD, param_field);
        if (clazz == null) {
            EasyLog.e("clazz is not null");
            return objectMap;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyMouse.HTTP.ResponseParam.class);
            if (!isAnno)
                continue;
            EasyMouse.HTTP.ResponseParam param = fields[i].getAnnotation(EasyMouse.HTTP.ResponseParam.class);
            String paramName = param.value().trim();
            if (paramName.length() == 0) {
                continue;
            }
            String type = fields[i].getGenericType().toString();
            param_type.put(paramName, type);
            param_field.put(paramName, fields[i]);
        }
        return objectMap;
    }
}