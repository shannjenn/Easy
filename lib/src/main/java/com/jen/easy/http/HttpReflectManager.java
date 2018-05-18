package com.jen.easy.http;

import com.jen.easy.Easy;
import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectManager {

    /**
     * 获取请求信息
     *
     * @return 请求信息
     */
    static Object[] getUrl(HttpRequest request) {
        Object[] values = new Object[3];
        if (request == null) {
            EasyLog.w(TAG.EasyHttp, "getTableName obj is null");
            return values;
        }
        boolean isGet = request.getClass().isAnnotationPresent(Easy.HTTP.GET.class);
        if (isGet) {
            Easy.HTTP.GET get = request.getClass().getAnnotation(Easy.HTTP.GET.class);
            values[0] = "GET";
            values[1] = request.url != null ? request.url : get.URL();
            values[2] = get.Response();
            return values;
        }
        boolean isPost = request.getClass().isAnnotationPresent(Easy.HTTP.POST.class);
        if (isPost) {
            Easy.HTTP.POST post = request.getClass().getAnnotation(Easy.HTTP.POST.class);
            values[0] = "POST";
            values[1] = request.url != null ? request.url : post.URL();
            values[2] = post.Response();
            return values;
        }
        boolean isPut = request.getClass().isAnnotationPresent(Easy.HTTP.PUT.class);
        if (isPut) {
            Easy.HTTP.PUT put = request.getClass().getAnnotation(Easy.HTTP.PUT.class);
            values[0] = "PUT";
            values[1] = request.url != null ? request.url : put.URL();
            values[2] = put.Response();
            return values;
        }
        return values;
    }

    /**
     * 获取网络请求参数
     *
     * @param obj       对象数据
     * @param urls      拼接请求地址参数
     * @param jsonParam 请求参数
     * @param heads     请求头参数
     */
    static void getRequestParams(Object obj, Map<String, String> urls, JSONObject jsonParam, Map<String, String> heads) {
        if (obj == null || obj instanceof Class) {
            EasyLog.w(TAG.EasyHttp, "getRequestParams getRequestParams obj is null");
            return;
        }

        Class clazz = obj.getClass();
        String clazzName = clazz.getName();
        String reqName = HttpBaseRequest.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(reqName) && !clazzName.equals(objName)) {
            boolean isNoRequestParam = clazz.isAnnotationPresent(Easy.HTTP.NoRequestParam.class);
            if (isNoRequestParam) {
                clazz = clazz.getSuperclass();//获取父类
                clazzName = clazz.getName();
                continue;//不获取请求参数
            }
            getRequestParam(clazz, obj, urls, jsonParam, heads);
            clazz = clazz.getSuperclass();//获取父类
            clazzName = clazz.getName();
        }
    }


    /**
     * 获取单个类请求参数
     *
     * @param clazz     类
     * @param obj       对象数据
     * @param urls      拼接请求地址参数
     * @param jsonParam 请求参数
     * @param heads     请求头参数
     */
    private static void getRequestParam(Class clazz, Object obj, Map<String, String> urls, JSONObject jsonParam, Map<String, String> heads) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnnotation = field.isAnnotationPresent(Easy.HTTP.RequestParam.class);
            String key = "";
            Easy.HTTP.TYPE paramType = Easy.HTTP.TYPE.PARAM;
            if (isAnnotation) {
                Easy.HTTP.RequestParam param = field.getAnnotation(Easy.HTTP.RequestParam.class);
                boolean noReq = param.noReq();
                if (noReq) {//不做参数传递
                    continue;
                }
                paramType = param.type();
                key = param.value().trim();
            }
            if (key.length() == 0) {
                key = field.getName();
            }

            if (FieldType.isOtherField(key)) {
                continue;
            }
            String fieldType = field.getGenericType().toString();
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value == null) {
                    continue;
                }
                if (value instanceof String || value instanceof Integer || value instanceof Float || value instanceof Long
                        || value instanceof Double || value instanceof Boolean) {
                    switch (paramType) {
                        case PARAM: {
                            jsonParam.put(key, value);
                            break;
                        }
                        case HEAD: {
                            heads.put(key, value + "");
                            break;
                        }
                        case URL: {
                            urls.put(key, value + "");
                            break;
                        }
                    }
                } else if (value instanceof List) {
                    List listObj = (List) value;
                    if (listObj == null || listObj.size() <= 0) {
                        continue;
                    }
                    Object item0 = listObj.get(0);
                    boolean isBasic = item0 instanceof String || item0 instanceof Integer || item0 instanceof Float
                            || item0 instanceof Long || item0 instanceof Double || item0 instanceof Boolean;
                    if (isBasic) {
                        EasyLog.w(TAG.EasyHttp, "不支持该类型");
                    } else {
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < listObj.size(); i++) {
                            Object itemObj = listObj.get(i);
                            if (itemObj == null) {
                                continue;
                            }
                            JSONObject item = new JSONObject();
                            getRequestParam(itemObj.getClass(), itemObj, urls, item, heads);
                            jsonArray.put(item);
                        }
                        if (jsonArray.length() > 0) {
                            jsonParam.put(key, jsonArray);
                        }
                    }
                } else if (FieldType.isClass(fieldType)) {
                    JSONObject item = new JSONObject();
                    getRequestParam(value.getClass(), value, urls, item, heads);
                    jsonParam.put(key, item);
                } else {
                    EasyLog.w(TAG.EasyHttp, "不支持该类型：" + field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLog.w(TAG.EasyHttp, "getRequestParams IllegalAccessException");
            } catch (JSONException e) {
                e.printStackTrace();
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
            EasyLog.w(TAG.EasyHttp, "getResponseParams clazz is not null");
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
            boolean isAnnotation = field.isAnnotationPresent(Easy.HTTP.ResponseParam.class);
            String paramName = "";
            Easy.HTTP.TYPE paramType = Easy.HTTP.TYPE.PARAM;
            if (isAnnotation) {
                Easy.HTTP.ResponseParam param = field.getAnnotation(Easy.HTTP.ResponseParam.class);
                boolean noResp = param.noResp();
                if (noResp) {//不做参数返回
                    continue;
                }
                paramType = param.type();
                paramName = param.value().trim();
            }
            if (paramName.length() == 0) {
                paramName = field.getName();
            }
            if (FieldType.isOtherField(paramName)) {
                continue;
            }
            String type = field.getGenericType().toString();
            switch (paramType) {
                case PARAM: {
                    if (param_type.containsKey(paramName)) {//子类已经有不再重复增加
                        continue;
                    }
                    param_type.put(paramName, type);
                    param_field.put(paramName, field);
                    break;
                }
                case HEAD: {
                    if (!FieldType.isString(type)) {
                        EasyLog.w(TAG.EasyHttp, "请求头返回变量必须为String类型:" + paramName);
                        continue;
                    }
                    head_field.put(paramName, field);
                    break;
                }
                case URL: {
                    break;
                }
            }
        }
    }
}
