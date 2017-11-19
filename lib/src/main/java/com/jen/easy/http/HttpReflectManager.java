package com.jen.easy.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectManager {
    private final static String TAG = "HttpReflectManager : ";
    static String PARAM_TYPE = "param_type";
    static String PARAM_FIELD = "param_field";

    /**
     * 获取地址
     *
     * @return
     */
    static String[] getUrl(Object obj) {
        String[] values = new String[2];
        if (obj == null) {
            EasyLibLog.e(TAG + "getTableName obj is null");
            return null;
        }
        boolean isAnnoGet = obj.getClass().isAnnotationPresent(EasyMouse.HTTP.GET.class);
        if (isAnnoGet) {
            EasyMouse.HTTP.GET url =  obj.getClass().getAnnotation(EasyMouse.HTTP.GET.class);
            values[0] = "GET";
            values[1] = url.value();
            return values;
        }
        boolean isAnnoPost = obj.getClass().isAnnotationPresent(EasyMouse.HTTP.POST.class);
        if (isAnnoPost) {
            EasyMouse.HTTP.POST url =  obj.getClass().getAnnotation(EasyMouse.HTTP.POST.class);
            values[0] = "POST";
            values[1] = url.value();
            return values;
        }
        return null;
    }

    /**
     * 获取网络请求参数
     *
     * @param obj
     * @return
     */
    static Map<String, String> getRequestParams(Object obj) {
        Map<String, String> params = new HashMap<>();
        if (obj == null || obj instanceof Class) {
            EasyLibLog.e(TAG + "getRequestParams getRequestParams obj is null");
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
            fields[i].setAccessible(true);
            try {
                String value = fields[i].get(obj) + "";
                params.put(paramName, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "getRequestParams IllegalAccessException");
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
            EasyLibLog.e(TAG + "getResponseParams clazz is not null");
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
