package com.jen.easy.http;

import com.jen.easy.EasyHttpGet;
import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyHttpPut;
import com.jen.easy.EasyRequest;
import com.jen.easy.EasyResponse;
import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
import com.jen.easy.invalid.EasyInvalid;
import com.jen.easy.invalid.Invalid;
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
     * 返回实体
     */
    static class ResponseObject {
        final Map<String, Field> body = new HashMap<>();
        final Map<String, Field> heads = new HashMap<>();
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
        parseRequest(new ArrayList<String>(), request, requestObject.urls, requestObject.body, requestObject.heads);
        return requestObject;
    }

    /**
     * 获取网络请求参数
     *
     * @param loopList 避免死循环
     * @param request  对象数据
     * @param urls     拼接请求地址参数
     * @param body     返回的 请求参数
     * @param heads    返回的 请求头参数
     */
    private static void parseRequest(List<String> loopList, Object request, Map<String, String> urls, JSONObject body, Map<String, String> heads) {
        /*if (request == null) {
            Throw.exception(ExceptionType.NullPointerException, "parseRequest 参数不能为空");
            return;
        }*/
        /*if (loopClass == null) {
            loopClass = new ArrayList<>();
        }*/

        Class clazz = request.getClass();
        String clazzName = clazz.getName();
        if (loopList.contains(clazzName)) {
            Throw.exception(ExceptionType.RuntimeException, "无限死循环引用错误：" + clazzName);
            return;
        } else {
            loopList.add(clazzName);
        }
//        String reqName = HttpBasicRequest.class.getName();
        String objName = Object.class.getName();
        while (/*!clazzName.equals(reqName) &&*/ !clazzName.equals(objName)) {
            /*if (request.status == HttpState.STOP) {
                break;
            }*/
            boolean isInvalid = Invalid.isEasyInvalid(clazz, EasyInvalid.Type.Request);
            if (!isInvalid) {
                parseRequestEntity(loopList, clazz, request, urls, body, heads);
            }
            clazz = clazz.getSuperclass();//获取父类
            clazzName = clazz.getName();
        }
    }


    /**
     * 获取单个类请求参数
     *
     * @param loopList 避免死循环
     * @param clazz    类
     * @param obj      对象数据
     * @param urls     拼接请求地址参数
     * @param body     请求参数
     * @param heads    请求头参数
     */
    private static void parseRequestEntity(List<String> loopList, Class clazz, Object obj, Map<String, String> urls, JSONObject body, Map<String, String> heads) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalid.Type.Request);
            if (isInvalid) {
                continue;
            }
            boolean isAnnotation = field.isAnnotationPresent(EasyRequest.class);
            String key = "";
            EasyRequest.Type paramType = EasyRequest.Type.Param;
            if (isAnnotation) {
                EasyRequest param = field.getAnnotation(EasyRequest.class);
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
                            parseRequest(loopList, itemObj, urls, item, heads);
                            jsonArray.put(item);
                        }
                    }
                    if (jsonArray.length() > 0) {
                        body.put(key, jsonArray);
                    }
                } else if (FieldType.isClass(field.getType())) {
                    JSONObject item = new JSONObject();
                    parseRequest(loopList, value, urls, item, heads);
                    if (item.length() != 0) {//有值才加入
                        body.put(key, item);
                    }
                } else {
                    Throw.exception(ExceptionType.IllegalArgumentException, "不支持该类型参数：" + field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLog.w(TAG.EasyHttp, "parseRequest IllegalAccessException");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析返回实体类参数
     *
     * @param clazz HttpResponse
     * @return ResponseObject
     */
    static ResponseObject getResponseHeadAndBody(Class clazz) {
        ResponseObject responseObject = new ResponseObject();
        parseResponse(clazz, responseObject.body, responseObject.heads);
        return responseObject;
    }

    /**
     * 获取返回参数
     *
     * @param clazz       类
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    private static void parseResponse(Class clazz, Map<String, Field> param_field, Map<String, Field> head_field) {
        /*if (clazz == null) {
            Throw.exception(ExceptionType.NullPointerException, "parseResponse clazz 空指针异常");
            return;
        }*/

        Class myClass = clazz;
        String clazzName = myClass.getName();
        String respName = HttpHeadResponse.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(respName) && !clazzName.equals(objName)) {
            boolean isInvalid = Invalid.isEasyInvalid(myClass, EasyInvalid.Type.Response);
            if (!isInvalid) {
                parseResponseEntity(myClass, param_field, head_field);
            }
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
    private static void parseResponseEntity(Class clazz, Map<String, Field> param_field, Map<String, Field> head_field) {
        Field[] fieldsSuper = clazz.getDeclaredFields();
        for (Field field : fieldsSuper) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalid.Type.Response);
            if (isInvalid) {
                continue;
            }
            boolean isAnnotation = field.isAnnotationPresent(EasyResponse.class);
            String paramName = "";
            EasyResponse.Type paramType = EasyResponse.Type.Param;
            if (isAnnotation) {
                EasyResponse param = field.getAnnotation(EasyResponse.class);
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
                case Param: {
                    if (param_field.containsKey(paramName)) {//子类已经有不再重复增加
                        continue;
                    }
                    param_field.put(paramName, field);
                    break;
                }
                case Head: {
                    if (!FieldType.isString(fieldClass)) {
                        Throw.exception(ExceptionType.ClassCastException, "请求头返回变量必须为String类型:" + paramName);
                        continue;
                    }
                    head_field.put(paramName, field);
                    break;
                }
            }
        }
    }
}
