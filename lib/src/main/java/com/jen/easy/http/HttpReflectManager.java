package com.jen.easy.http;

import com.jen.easy.EasyHttpGet;
import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyHttpPut;
import com.jen.easy.EasyRequest;
import com.jen.easy.EasyRequestInvalid;
import com.jen.easy.EasyResponse;
import com.jen.easy.EasyResponseInvalid;
import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
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
        /*if (request == null) {
            Throw.exception(ExceptionType.NullPointerException, "getUrl 请求地址不能为空");
            return values;
        }*/
        boolean isGet = request.getClass().isAnnotationPresent(EasyHttpGet.class);
        if (isGet) {
            EasyHttpGet get = request.getClass().getAnnotation(EasyHttpGet.class);
            values[0] = "GET";
            request.urlBase = request.urlBase != null ? request.urlBase : get.UrlBase();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : get.UrlAppand();
            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            values[1] = request.url;
            values[2] = get.Response();
            return values;
        }
        boolean isPost = request.getClass().isAnnotationPresent(EasyHttpPost.class);
        if (isPost) {
            EasyHttpPost post = request.getClass().getAnnotation(EasyHttpPost.class);
            values[0] = "POST";
            request.urlBase = request.urlBase != null ? request.urlBase : post.UrlBase();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : post.UrlAppand();
            request.url = request.url != null ? request.url : request.urlBase + request.urlAppend;
            values[1] = request.url;
            values[2] = post.Response();
            return values;
        }
        boolean isPut = request.getClass().isAnnotationPresent(EasyHttpPut.class);
        if (isPut) {
            EasyHttpPut put = request.getClass().getAnnotation(EasyHttpPut.class);
            values[0] = "PUT";
            request.urlBase = request.urlBase != null ? request.urlBase : put.UrlBase();
            request.urlAppend = request.urlAppend != null ? request.urlAppend : put.UrlAppand();
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
        /*if (request == null) {
            Throw.exception(ExceptionType.NullPointerException, "getRequestParams 参数不能为空");
            return;
        }*/
        /*if (loopClass == null) {
            loopClass = new ArrayList<>();
        }*/

        Class clazz = request.getClass();
        String clazzName = clazz.getName();
        if (loopClass.contains(clazzName)) {
            Throw.exception(ExceptionType.RuntimeException, "请求对象不能循环引用：" + clazzName);
            return;
        } else {
            loopClass.add(clazzName);
        }
//        String reqName = HttpBasicRequest.class.getName();
        String objName = Object.class.getName();
        while (/*!clazzName.equals(reqName) &&*/ !clazzName.equals(objName)) {
            /*if (request.state == HttpState.STOP) {
                break;
            }*/
            boolean isNoRequestParam = clazz.isAnnotationPresent(EasyRequestInvalid.class);
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
            boolean isAnnotation = field.isAnnotationPresent(EasyRequest.class);
            String key = "";
            EasyRequest.Type paramType = EasyRequest.Type.Param;
            if (isAnnotation) {
                EasyRequest param = field.getAnnotation(EasyRequest.class);
                boolean noReq = param.invalid();
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
                        case Param: {
                            jsonParam.put(key, value);
                            break;
                        }
                        case Head: {
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
                    Throw.exception(ExceptionType.ClassCastException, "不支持该类型参数请求：" + field.getName());
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
        /*if (clazz == null) {
            Throw.exception(ExceptionType.NullPointerException, "getResponseParams clazz 空指针异常");
            return;
        }*/

        Class myClass = clazz;
        String clazzName = myClass.getName();
        String respName = HttpHeadResponse.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(respName) && !clazzName.equals(objName)) {
            boolean isNoResponseParam = myClass.isAnnotationPresent(EasyResponseInvalid.class);
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
            boolean isAnnotation = field.isAnnotationPresent(EasyResponse.class);
            String paramName = "";
            EasyResponse.Type paramType = EasyResponse.Type.Param;
            if (isAnnotation) {
                EasyResponse param = field.getAnnotation(EasyResponse.class);
                boolean noResp = param.invalid();
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

    /**
     * 请求参数转Json
     *
     * @param object 请求参数
     * @return JSONObject
     */
    static JSONObject requestToJson(Object object) {
        JSONObject jsonParam = new JSONObject();
        if (object == null) {
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
            return jsonParam;
        }
        Map<String, String> urls = new HashMap<>();
        Map<String, String> heads = new HashMap<>();

        getRequestParams(new ArrayList<String>(), object, urls, jsonParam, heads);
        return jsonParam;
    }
}
