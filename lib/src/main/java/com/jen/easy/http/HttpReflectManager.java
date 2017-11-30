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
    static Object[] getUrl(Object obj) {
        Object[] values = new Object[3];
        if (obj == null) {
            EasyLibLog.e(TAG + "getTableName obj is null");
            return null;
        }
        boolean isAnnoGet = obj.getClass().isAnnotationPresent(EasyMouse.HTTP.GET.class);
        if (isAnnoGet) {
            EasyMouse.HTTP.GET url =  obj.getClass().getAnnotation(EasyMouse.HTTP.GET.class);
            values[0] = "GET";
            values[1] = url.URL();
            values[2] = url.Response();
            return values;
        }
        boolean isAnnoPost = obj.getClass().isAnnotationPresent(EasyMouse.HTTP.POST.class);
        if (isAnnoPost) {
            EasyMouse.HTTP.POST url =  obj.getClass().getAnnotation(EasyMouse.HTTP.POST.class);
            values[0] = "POST";
            values[1] = url.URL();
            values[2] = url.Response();
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
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.RequestParam.class);
            if (!isAnno)
                continue;
            EasyMouse.HTTP.RequestParam param = field.getAnnotation(EasyMouse.HTTP.RequestParam.class);
            String paramName = param.value().trim();
            if (paramName.length() == 0) {
                continue;
            }
            field.setAccessible(true);
            try {
                String value = field.get(obj) + "";
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
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.ResponseParam.class);
            if (!isAnno)
                continue;
            EasyMouse.HTTP.ResponseParam param = field.getAnnotation(EasyMouse.HTTP.ResponseParam.class);
            String paramName = param.value().trim();
            if (paramName.length() == 0) {
                continue;
            }
            String type = field.getGenericType().toString();
            param_type.put(paramName, type);
            param_field.put(paramName, field);
        }
        return objectMap;
    }

    static Class getObjClass(Field field) {
        Class objClass = Object.class;
        boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.ResponseParam.class);
        if (isAnno) {
            EasyMouse.HTTP.ResponseParam param = field.getAnnotation(EasyMouse.HTTP.ResponseParam.class);
            objClass = param.clazz();
        }
        return objClass;
    }
}
