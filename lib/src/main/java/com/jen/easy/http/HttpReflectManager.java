package com.jen.easy.http;

import com.jen.easy.Easy;
import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            request.urlBase = request.urlBase != null ? request.urlBase : get.URLBASE();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : get.URLAPPEND();
            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            values[1] = request.url;
            values[2] = get.Response();
            return values;
        }
        boolean isPost = request.getClass().isAnnotationPresent(Easy.HTTP.POST.class);
        if (isPost) {
            Easy.HTTP.POST post = request.getClass().getAnnotation(Easy.HTTP.POST.class);
            values[0] = "POST";
            request.urlBase = request.urlBase != null ? request.urlBase : post.URLBASE();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : post.URLAPPEND();
            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            values[1] = request.url;
            values[2] = post.Response();
            return values;
        }
        boolean isPut = request.getClass().isAnnotationPresent(Easy.HTTP.PUT.class);
        if (isPut) {
            Easy.HTTP.PUT put = request.getClass().getAnnotation(Easy.HTTP.PUT.class);
            values[0] = "PUT";
            request.urlBase = request.urlBase != null ? request.urlBase : put.URLBASE();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : put.URLAPPEND();
            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            values[1] = request.url;
            values[2] = put.Response();
            return values;
        }
        return values;
    }

    /**
     * 获取网络请求参数
     *
     * @param loopClass 避免死循环
     * @param request   对象数据
     * @param urls      拼接请求地址参数
     * @param jsonParam 请求参数
     * @param heads     请求头参数
     */
    static void getRequestParams(List<String> loopClass, Object request, Map<String, String> urls, JSONObject jsonParam, Map<String, String> heads) {
        if (request == null) {
            EasyLog.w(TAG.EasyHttp, "getRequestParams getRequestParams obj is null");
            return;
        }
        if (loopClass == null) {
            loopClass = new ArrayList<>();
        }

        Class clazz = request.getClass();
        String clazzName = clazz.getName();
        if (loopClass.contains(clazzName)) {
            EasyLog.w(TAG.EasyHttp, "不解析死循环引用：" + clazzName);
            return;
        } else {
            loopClass.add(clazzName);
        }
//        String reqName = HttpBaseRequest.class.getName();
        String objName = Object.class.getName();
        while (/*!clazzName.equals(reqName) &&*/ !clazzName.equals(objName)) {
            /*if (request.state == HttpState.STOP) {
                break;
            }*/
            boolean isNoRequestParam = clazz.isAnnotationPresent(Easy.HTTP.NoRequestParam.class);
            if (isNoRequestParam) {
                clazz = clazz.getSuperclass();//获取父类
                clazzName = clazz.getName();
                continue;//不获取请求参数
            }
            getRequestParam(loopClass, clazz, request, urls, jsonParam, heads);
            clazz = clazz.getSuperclass();//获取父类
            clazzName = clazz.getName();
        }
    }


    /**
     * 获取单个类请求参数
     *
     * @param loopClass 避免死循环
     * @param clazz     类
     * @param obj       对象数据
     * @param urls      拼接请求地址参数
     * @param jsonParam 请求参数
     * @param heads     请求头参数
     */
    private static void getRequestParam(List<String> loopClass, Class clazz, Object obj, Map<String, String> urls, JSONObject jsonParam, Map<String, String> heads) {
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
                    if (listObj.size() <= 0) {
                        continue;
                    }
                    Object item0 = listObj.get(0);
                    boolean isBasic = item0 instanceof String || item0 instanceof Integer || item0 instanceof Float
                            || item0 instanceof Long || item0 instanceof Double || item0 instanceof Boolean;
                    JSONArray jsonArray = new JSONArray();
                    if (isBasic) {
                        for (int i = 0; i < listObj.size(); i++) {
                            Object itemObj = listObj.get(i);
                            if (itemObj == null) {
                                continue;
                            }
                            jsonArray.put(itemObj);
                        }
                    } else {
                        for (int i = 0; i < listObj.size(); i++) {
                            Object itemObj = listObj.get(i);
                            if (itemObj == null) {
                                continue;
                            }
                            JSONObject item = new JSONObject();
                            getRequestParams(loopClass, itemObj, urls, item, heads);
                            jsonArray.put(item);
                        }
                    }
                    if (jsonArray.length() > 0) {
                        jsonParam.put(key, jsonArray);
                    }
                } else if (FieldType.isClass(field.getType())) {
                    JSONObject item = new JSONObject();
                    getRequestParams(loopClass, value, urls, item, heads);
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
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    static void getResponseParams(Class clazz, Map<String, Field> param_field, Map<String, Field> head_field) {
        if (clazz == null) {
            EasyLog.w(TAG.EasyHttp, "getResponseParams clazz is not null");
            return;
        }

        Class myClass = clazz;
        String clazzName = myClass.getName();
        String respName = HttpHeadResponse.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(respName) && !clazzName.equals(objName)) {
            boolean isNoResponseParam = myClass.isAnnotationPresent(Easy.HTTP.NoResponseParam.class);
            if (isNoResponseParam) {
                myClass = myClass.getSuperclass();//获取父类
                clazzName = myClass.getName();
                continue;//不获取请求参数
            }
            getResponseParam(myClass, param_field, head_field);
            myClass = myClass.getSuperclass();
            clazzName = myClass.getName();
        }
    }

    /**
     * 获取单个返回参数
     *
     * @param clazz       类
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    private static void getResponseParam(Class clazz, Map<String, Field> param_field, Map<String, Field> head_field) {
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
            Class fieldClass = field.getType();
            switch (paramType) {
                case PARAM: {
                    if (param_field.containsKey(paramName)) {//子类已经有不再重复增加
                        continue;
                    }
                    param_field.put(paramName, field);
                    break;
                }
                case HEAD: {
                    if (!FieldType.isString(fieldClass)) {
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

    /**
     * 请求参数转Json
     *
     * @param object 请求参数
     * @return JSONObject
     */
    public static JSONObject paramToJson(Object object) {
        JSONObject jsonParam = new JSONObject();
        if (object == null) {
            EasyLog.w(TAG.EasyHttp, "toJson error, obj is not null");
            return jsonParam;
        }
        Map<String, String> urls = new HashMap<>();
        Map<String, String> heads = new HashMap<>();

        getRequestParams(null, object, urls, jsonParam, heads);
        return jsonParam;
    }
}
