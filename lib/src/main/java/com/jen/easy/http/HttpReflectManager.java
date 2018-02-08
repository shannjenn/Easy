package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyMouse;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectManager {
    private final static String TAG = "HttpReflectManager : ";
    static String RSP_TYPE = "rsp_type";
    static String RSP_FIELD = "rsp_field";
    static String RSP_HEAD = "rsp_head";

    static String REQ_PARAM_KEYS = "req_param_keys";
    static String REQ_PARAM_VALUES = "req_param_values";
    static String REQ_HEAD_KEYS = "req_head_keys";
    static String REQ_HEAD_VALUES = "req_head_values";

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
        boolean isAnnoPut = request.getClass().isAnnotationPresent(EasyMouse.HTTP.PUT.class);
        if (isAnnoPut) {
            EasyMouse.HTTP.PUT put = request.getClass().getAnnotation(EasyMouse.HTTP.PUT.class);
            values[0] = "PUT";
            String url = request.httpParam.url != null ? request.httpParam.url : put.URL();
            if (!TextUtils.isEmpty(url) && request.httpParam.urlAppend != null) {
                url = url + request.httpParam.urlAppend;
            }
            values[1] = url;
            values[2] = put.Response();
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
    static Map<String, List<String>> getRequestParams(Object obj) {
        Map<String, List<String>> objectMap = new HashMap<>();
        List<String> param_keys = new ArrayList<>();
        List<String> param_values = new ArrayList<>();
        List<String> head_keys = new ArrayList<>();
        List<String> head_values = new ArrayList<>();

        if (obj == null || obj instanceof Class) {
            EasyLibLog.e(TAG + "getRequestParams getRequestParams obj is null");
            return objectMap;
        }

        Class clazz = obj.getClass();
        String clazzName = clazz.getName();
        String reqName = HttpBaseRequest.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(reqName) && !clazzName.equals(objName)) {
            getRequestParam(clazz, obj, param_keys, param_values, head_keys, head_values);
            clazz = clazz.getSuperclass();
            clazzName = clazz.getName();
        }
        objectMap.put(REQ_PARAM_KEYS, param_keys);
        objectMap.put(REQ_PARAM_VALUES, param_values);
        objectMap.put(REQ_HEAD_KEYS, head_keys);
        objectMap.put(REQ_HEAD_VALUES, head_values);
        return objectMap;
    }

    /**
     * 获取单个类请求参数
     *
     * @param clazz
     * @param obj
     * @param param_keys
     * @param param_values
     * @param head_keys
     * @param head_values
     */
    private static void getRequestParam(Class clazz, Object obj,
                                        List<String> param_keys, List<String> param_values, List<String> head_keys, List<String> head_values) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.RequestParam.class);
            /*if (!isAnno)
                continue;*/
            String key = "";
            boolean isHead = false;
            if (isAnno) {
                EasyMouse.HTTP.RequestParam param = field.getAnnotation(EasyMouse.HTTP.RequestParam.class);
                boolean noReq = param.noReq();
                if (noReq) {//不做参数传递
                    continue;
                }
                isHead = param.isHeadReq();
                key = param.value().trim();
            }
            if (key.length() == 0) {
                key = field.getName();
            }
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {
                if (type.equals(Constant.FieldType.STRING) || type.equals(Constant.FieldType.INTEGER)) {
                    Object value = field.get(obj);
                    if (isHead) {
                        head_keys.add(key);
                        head_values.add(value + "");
                    } else {
                        param_keys.add(key);
                        param_values.add(value + "");
                    }
                } else if (type.contains(Constant.FieldType.LIST)) {
                    List values = (List) field.get(obj);
                    if (values == null || values.size() <= 0) {
                        break;
                    }
                    int size = values.size();
                    for (int i = 0; i < size; i++) {
                        Object value = values.get(i);
                        if (value instanceof String ||
                                value instanceof Integer || value instanceof Float
                                || value instanceof Long || value instanceof Double) {
                            if (isHead) {
                                head_keys.add(key);
                                head_values.add(value + "");
                            } else {
                                param_keys.add(key);
                                param_values.add(value + "");
                            }
                        } else {
                            EasyLibLog.e(TAG + "不支持该类型（001）：" + field.getName());
                        }
                    }
                } else {
                    EasyLibLog.e(TAG + "不支持该类型（002）：" + field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "getRequestParams IllegalAccessException");
            }
        }
    }

    /**
     * 获取返回参数
     *
     * @param clazz
     * @return
     */
    static Map<String, Object> getResponseParams(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, String> param_type = new HashMap<>();//param：json要解析的参数名，type：类型
        Map<String, Field> param_field = new HashMap<>();
        Map<String, Field> head_field = new HashMap<>();//head：head要解析的参数名；
        objectMap.put(RSP_TYPE, param_type);
        objectMap.put(RSP_FIELD, param_field);
        objectMap.put(RSP_HEAD, head_field);
        if (clazz == null) {
            EasyLibLog.e(TAG + "getResponseParams clazz is not null");
            return objectMap;
        }

        Class myClass = clazz;
        String clazzName = myClass.getName();
        String respName = HttpHeadResponse.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(respName) && !clazzName.equals(objName)) {
            getResponseParam(myClass, param_type, param_field, head_field);
            myClass = myClass.getSuperclass();
            clazzName = myClass.getName();
        }
        return objectMap;
    }

    /**
     * 获取单个类返回参数
     *
     * @param clazz
     * @param param_type
     * @param param_field
     */
    private static void getResponseParam(Class clazz, Map<String, String> param_type,
                                         Map<String, Field> param_field, Map<String, Field> head_field) {
        Field[] fieldsSuper = clazz.getDeclaredFields();
        for (Field field : fieldsSuper) {
            /*//只获取public、protect类型
            int modifierType = field.getModifiers();
            if (modifierType != Modifier.PUBLIC && modifierType != Modifier.PROTECTED)
                continue;*/
            boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.ResponseParam.class);
            /*if (!isAnno)
                continue;*/
            String paramName = "";
            boolean isHead = false;
            if (isAnno) {
                EasyMouse.HTTP.ResponseParam param = field.getAnnotation(EasyMouse.HTTP.ResponseParam.class);
                boolean noResp = param.noResp();
                if (noResp) {//不做参数返回
                    continue;
                }
                isHead = param.isHeadRsp();
                paramName = param.value().trim();
            }
            if (paramName.length() == 0) {
                paramName = field.getName();
            }
            String type = field.getGenericType().toString();

            if (isHead) {
                if (!type.equals(Constant.FieldType.STRING)) {
                    EasyLibLog.e(TAG + "请求头返回变量必须为String类型");
                    continue;
                }
                head_field.put(paramName, field);
            } else {
                if (param_type.containsKey(paramName)) {//子类已经有不再重复增加
                    continue;
                }
                param_type.put(paramName, type);
                param_field.put(paramName, field);
            }

        }
    }
}
