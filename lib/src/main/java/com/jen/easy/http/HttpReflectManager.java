package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyMouse;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
    static Object[] getUrl(HttpRequest request) {
        Object[] values = new Object[3];
        if (request == null) {
            EasyLibLog.e(TAG + "getTableName obj is null");
            return null;
        }

        boolean isAnnoGet = request.getClass().isAnnotationPresent(EasyMouse.HTTP.GET.class);
        if (isAnnoGet) {
            EasyMouse.HTTP.GET get = request.getClass().getAnnotation(EasyMouse.HTTP.GET.class);
            values[0] = "GET";
            String url = request.httpParam.url != null ? request.httpParam.url : get.URL();
            if (!TextUtils.isEmpty(url) && request.httpParam.urlAppend != null) {
                url = url + request.httpParam.urlAppend;
            }
            values[1] = url;
            values[2] = get.Response();
            return values;
        }
        boolean isAnnoPost = request.getClass().isAnnotationPresent(EasyMouse.HTTP.POST.class);
        if (isAnnoPost) {
            EasyMouse.HTTP.POST post = request.getClass().getAnnotation(EasyMouse.HTTP.POST.class);
            values[0] = "POST";
            String url = request.httpParam.url != null ? request.httpParam.url : post.URL();
            if (!TextUtils.isEmpty(url) && request.httpParam.urlAppend != null) {
                url = url + request.httpParam.urlAppend;
            }
            values[1] = url;
            values[2] = post.Response();
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
            /*if (!isAnno)
                continue;*/
            String paramName = "";
            if (isAnno) {
                EasyMouse.HTTP.RequestParam param = field.getAnnotation(EasyMouse.HTTP.RequestParam.class);
                boolean noReq = param.noReq();
                if (noReq) {//不做参数传递
                    continue;
                }
                paramName = param.value().trim();
            }
            if (paramName.length() == 0) {
                paramName = field.getName();
            }
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {
                switch (type) {
                    case Constant.FieldType.STRING: {//string类型
                        String value = field.get(obj) + "";
                        params.put(paramName, value);
                        break;
                    }
                    case Constant.FieldType.INTEGER: {//int类型
                        int value = field.getInt(obj);
                        params.put(paramName, value + "");
                        break;
                    }
                    default:
                        EasyLibLog.e(TAG + "请求参数必须为int或者string类型：" + field.getName());
                        break;
                }
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

        //父类变量（public、protected修饰）
        Field[] fieldsSuper = clazz.getSuperclass().getDeclaredFields();
        for (Field field : fieldsSuper) {
            int modifierType = field.getModifiers();
            if (modifierType != Modifier.PUBLIC && modifierType != Modifier.PROTECTED)
                continue;
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

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.ResponseParam.class);
            /*if (!isAnno)
                continue;*/
            String paramName = "";
            if (isAnno) {
                EasyMouse.HTTP.ResponseParam param = field.getAnnotation(EasyMouse.HTTP.ResponseParam.class);
                boolean noResp = param.noResp();
                if (noResp) {//不做参数返回
                    continue;
                }
                paramName = param.value().trim();
            }
            if (paramName.length() == 0) {
                paramName = field.getName();
            }
            String type = field.getGenericType().toString();
            param_type.put(paramName, type);
            param_field.put(paramName, field);
        }
        return objectMap;
    }
}
