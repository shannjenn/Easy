package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyMouse;
import com.jen.easy.constant.Constant;
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
    static String RSP_TYPE = "rsp_type";
    static String RSP_FIELD = "rsp_field";
    static String RSP_HEAD = "rsp_head";

    static String REQ_PARAMS = "req_params";
    static String REQ_HEADS = "req_heads";

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
    static Map<String, Map<String, String>> getRequestParams(Object obj) {
        Map<String, Map<String, String>> objectMap = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        Map<String, String> heads = new HashMap<>();

        if (obj == null || obj instanceof Class) {
            EasyLibLog.e(TAG + "getRequestParams getRequestParams obj is null");
            return objectMap;
        }

        Class clazz = obj.getClass();
        while (!clazz.getName().equals(HttpBaseRequest.class.getName())) {
            addSuperRequestParams(clazz, obj, params, heads);
            clazz = clazz.getSuperclass();
        }
        objectMap.put(REQ_PARAMS, params);
        objectMap.put(REQ_HEADS, heads);
        return objectMap;
    }

    /**
     * 增加父类请求参数
     *
     * @param clazz
     * @param obj
     * @param params
     * @param heads
     */
    private static void addSuperRequestParams(Class clazz, Object obj, Map<String, String> params, Map<String, String> heads) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.HTTP.RequestParam.class);
            /*if (!isAnno)
                continue;*/
            String paramName = "";
            boolean isHead = false;
            if (isAnno) {
                EasyMouse.HTTP.RequestParam param = field.getAnnotation(EasyMouse.HTTP.RequestParam.class);
                boolean noReq = param.noReq();
                if (noReq) {//不做参数传递
                    continue;
                }
                isHead = param.isHeadReq();
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
                        if (isHead) {
                            heads.put(paramName, value);
                        } else {
                            params.put(paramName, value);
                        }
                        break;
                    }
                    case Constant.FieldType.INTEGER: {//int类型
                        int value = field.getInt(obj);
                        if (isHead) {
                            heads.put(paramName, value + "");
                        } else {
                            params.put(paramName, value + "");
                        }
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
        while (!myClass.getName().equals(Object.class.getName())) {
            addSuperResponseParams(myClass, param_type, param_field, head_field);
            myClass = myClass.getSuperclass();
        }
        return objectMap;
    }

    /**
     * 增加父类返回参数
     *
     * @param clazz
     * @param param_type
     * @param param_field
     */
    private static void addSuperResponseParams(Class clazz, Map<String, String> param_type,
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
