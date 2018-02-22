package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyMouse;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectManager {
    private final static String TAG = "HttpReflectManager : ";

    /**
     * 获取请求信息
     *
     * @return 请求信息
     */
    static Object[] getUrl(HttpRequest request) {
        Object[] values = new Object[3];
        if (request == null) {
            EasyLibLog.e(TAG + "getTableName obj is null");
            return values;
        }

        boolean isGet = request.getClass().isAnnotationPresent(EasyMouse.HTTP.GET.class);
        if (isGet) {
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
        boolean isPost = request.getClass().isAnnotationPresent(EasyMouse.HTTP.POST.class);
        if (isPost) {
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
        boolean isPut = request.getClass().isAnnotationPresent(EasyMouse.HTTP.PUT.class);
        if (isPut) {
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
        return values;
    }

    /**
     * 获取网络请求参数
     *
     * @param obj 对象
     */
    static void getRequestParams(Object obj, List<String> paramKeys, List<String> paramValues, List<String> headKeys, List<String> headValues) {

        if (obj == null || obj instanceof Class) {
            EasyLibLog.e(TAG + "getRequestParams getRequestParams obj is null");
            return;
        }

        Class clazz = obj.getClass();
        String clazzName = clazz.getName();
        String reqName = HttpBaseRequest.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(reqName) && !clazzName.equals(objName)) {
            getRequestParam(clazz, obj, paramKeys, paramValues, headKeys, headValues);
            clazz = clazz.getSuperclass();
            clazzName = clazz.getName();
        }
    }

    /**
     * 获取单个类请求参数
     *
     * @param clazz       类
     * @param obj         对象数据
     * @param paramKeys   参数名
     * @param paramValues 参数的值
     * @param headKeys    头参数
     * @param headValues  头参值
     */
    private static void getRequestParam(Class clazz, Object obj, List<String> paramKeys, List<String> paramValues, List<String> headKeys, List<String> headValues) {
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
            if (Constant.FieldType.isOtherField(key)) {
                continue;
            }
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {
                if (type.equals(Constant.FieldType.STRING) || type.equals(Constant.FieldType.INTEGER)) {
                    Object value = field.get(obj);
                    if (isHead) {
                        headKeys.add(key);
                        headValues.add(value + "");
                    } else {
                        paramKeys.add(key);
                        paramValues.add(value + "");
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
                                headKeys.add(key);
                                headValues.add(value + "");
                            } else {
                                paramKeys.add(key);
                                paramValues.add(value + "");
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
     * @param clazz       类
     * @param param_type  名称_类型
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    static void getResponseParams(Class clazz, Map<String, String> param_type, Map<String, Field> param_field, Map<String, Field> head_field) {
        if (clazz == null) {
            EasyLibLog.e(TAG + "getResponseParams clazz is not null");
            return;
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
    }

    /**
     * 获取单个返回参数
     *
     * @param clazz       类
     * @param param_type  名称_类型
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    private static void getResponseParam(Class clazz, Map<String, String> param_type, Map<String, Field> param_field, Map<String, Field> head_field) {
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
            if (Constant.FieldType.isOtherField(paramName)) {
                continue;
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
