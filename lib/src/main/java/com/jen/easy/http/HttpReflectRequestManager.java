package com.jen.easy.http;

import com.jen.easy.EasyHttpGet;
import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyHttpPut;
import com.jen.easy.EasyRequest;
import com.jen.easy.EasyRequestType;
import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.invalid.EasyInvalidType;
import com.jen.easy.invalid.Invalid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectRequestManager {

    /**
     * 请求实体
     */
    static class RequestObject {
        final Map<String, String> urls = new HashMap<>();
        final JSONObject body = new JSONObject();
        final Map<String, String> heads = new HashMap<>();
    }

    static class RequestType {
        String method;
        String url;
        Class response;
    }

    /**
     * 获取请求信息
     *
     * @return 请求信息
     */
    static RequestType getRequestType(HttpRequest request) {
//        Object[] values = new Object[3];
        /*if (request == null) {
            Throw.exception(ExceptionType.NullPointerException, "getUrl 请求地址不能为空");
            return values;
        }*/
        RequestType requestType = new RequestType();
        boolean isGet = request.getClass().isAnnotationPresent(EasyHttpGet.class);
        if (isGet) {
            EasyHttpGet get = request.getClass().getAnnotation(EasyHttpGet.class);
            requestType.method = "GET";
            request.urlBase = request.urlBase != null ? request.urlBase : get.UrlBase();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : get.UrlAppend();
//            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            requestType.url = request.urlBase + request.urlAppend;
            requestType.response = request.response != null ? request.response : get.Response();
            return requestType;
        }
        boolean isPost = request.getClass().isAnnotationPresent(EasyHttpPost.class);
        if (isPost) {
            EasyHttpPost post = request.getClass().getAnnotation(EasyHttpPost.class);
            requestType.method = "POST";
            request.urlBase = request.urlBase != null ? request.urlBase : post.UrlBase();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : post.UrlAppend();
//            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            requestType.url = request.urlBase + request.urlAppend;
            requestType.response = request.response != null ? request.response : post.Response();
            return requestType;
        }
        boolean isPut = request.getClass().isAnnotationPresent(EasyHttpPut.class);
        if (isPut) {
            EasyHttpPut put = request.getClass().getAnnotation(EasyHttpPut.class);
            requestType.method = "PUT";
            request.urlBase = request.urlBase != null ? request.urlBase : put.UrlBase();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : put.UrlAppend();
//            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            requestType.url = request.urlBase + request.urlAppend;
            requestType.response = request.response != null ? request.response : put.Response();
            return requestType;
        }
        return null;
    }

    /**
     * 获取请求实体
     *
     * @param request 请求参数
     * @return RequestObject
     */
    static RequestObject getRequestHeadAndBody(Object request) {
        RequestObject requestObject = new RequestObject();
        parseRequest(new HashMap<String, Integer>(), request, requestObject.urls, requestObject.body, requestObject.heads);
        return requestObject;
    }

    /**
     * 获取网络请求参数
     *
     * @param loopMap 避免死循环
     * @param request 对象数据
     * @param urls    拼接请求地址参数
     * @param body    返回的 请求参数
     * @param heads   返回的 请求头参数
     */
    private static void parseRequest(HashMap<String, Integer> loopMap, Object request, Map<String, String> urls, JSONObject body, Map<String, String> heads) {
        Class clazz = request.getClass();
        String clazzName = clazz.getName();

        if (loopMap.containsKey(clazzName)) {
            int value = loopMap.get(clazzName);
            if (value == 100) {//超过100默认死循环
                HttpLog.exception(ExceptionType.RuntimeException, "无限死循环引用错误：" + clazzName);
                return;
            } else {
                loopMap.put(clazzName, value + 1);
            }
        } else {
            loopMap.put(clazzName, 1);
        }

        String objName = Object.class.getName();
        while (!clazzName.equals(objName)) {
            boolean isInvalid = Invalid.isEasyInvalid(clazz, EasyInvalidType.Request);
            if (!isInvalid) {
                parseRequestEntity(loopMap, clazz, request, urls, body, heads);
            }
            clazz = clazz.getSuperclass();//获取父类
            clazzName = clazz.getName();
        }
    }


    /**
     * 获取单个类请求参数
     *
     * @param loopMap 避免死循环
     * @param clazz   类
     * @param obj     对象数据
     * @param urls    拼接请求地址参数
     * @param body    请求参数
     * @param heads   请求头参数
     */
    private static void parseRequestEntity(HashMap<String, Integer> loopMap, Class clazz, Object obj, Map<String, String> urls, JSONObject body, Map<String, String> heads) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalidType.Request);
            if (isInvalid) {
                continue;
            }
            boolean isAnnotation = field.isAnnotationPresent(EasyRequest.class);
            String key = "";
            EasyRequestType paramType = EasyRequestType.Param;
            if (isAnnotation) {
                EasyRequest param = field.getAnnotation(EasyRequest.class);
                paramType = param.type();
                key = param.value().trim();
            }
            if (key.length() == 0) {
                key = field.getName();
                if (key.contains("this$")) {//防止内部类自引用
                    continue;
                }
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
                        case Param: {
                            body.put(key, value);
                            break;
                        }
                        case Head: {
                            if (heads.containsKey(key)) {
                                continue;
                            }
                            heads.put(key, value + "");
                            break;
                        }
                        case Url: {
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
                    JSONArray jsonArray = null;
                    boolean haveArray = false;
                    /*
                     * 重复key对象叠加字段 重要标记
                     */
                    if (body.has(key)) {
                        Object object = body.get(key);
                        if (object instanceof JSONArray) {
                            jsonArray = (JSONArray) object;
                            haveArray = true;
                        }
                    }
                    if (jsonArray == null) {
                        jsonArray = new JSONArray();
                    }
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
                            parseRequest(loopMap, itemObj, urls, item, heads);
                            if (item.length() > 0) {
                                jsonArray.put(item);
                            }
                        }
                    }
                    if (!haveArray && jsonArray.length() > 0) {
                        body.put(key, jsonArray);
                    }
                } else if (FieldType.isEntityClass(field.getType())) {
                    JSONObject item = null;
                    boolean haveObject = false;
                    /*
                     * 重复key对象叠加字段 重要标记
                     */
                    if (body.has(key)) {
                        Object object = body.get(key);
                        if (object instanceof JSONObject) {
                            item = (JSONObject) object;
                            haveObject = true;
                        }
                    }
                    if (item == null) {
                        item = new JSONObject();
                    }
                    parseRequest(loopMap, value, urls, item, heads);
                    if (!haveObject && item.length() != 0) {//有值才加入
                        body.put(key, item);
                    }
                } else {
                    HttpLog.exception(ExceptionType.IllegalArgumentException, "不支持该类型参数：" + field.getName());
                }
            } catch (IllegalAccessException e) {
                HttpLog.exception(ExceptionType.IllegalAccessException, "parseRequest IllegalAccessException");
            } catch (JSONException e) {
                HttpLog.exception(ExceptionType.JSONException, "parseRequest JSONException");
            }
        }
    }
}