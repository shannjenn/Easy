package com.jen.easy.http;

import com.jen.easy.EasyUtil;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jen.easy.http.HttpReflectManager.PARAM_FIELD;
import static com.jen.easy.http.HttpReflectManager.PARAM_TYPE;

/**
 * Created by Jen on 2017/7/24.
 */
class HttpParseManager {
    private final static String TAG = "HttpParseManager : ";

    /**
     * json解析
     *
     * @param tObj
     * @param obj
     * @return
     */
    static <T> T parseJson(T tObj, String obj) {
        if (obj == null) {
            EasyLibLog.e(TAG + "parseJson obj is null");
        }
        String className = tObj.getClass().getName();
        if (tObj instanceof HttpResponse) {
            className = className + " 通用实体类：" + ((HttpResponse) tObj).objClass.getName();
        }
        EasyLibLog.d(TAG + "解析：" + className);

        try {
            JSONObject object = new JSONObject(obj);
            tObj = parseJsonObject(tObj, object);
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "parseJson JSONException error");
        }
        return tObj;
    }

    /**
     * 解析接口返回json格式值
     *
     * @param tObj
     * @param jsonObject
     * @return
     */
    private static <T> T parseJsonObject(T tObj, JSONObject jsonObject) {
        Map<String, Object> objectMap = HttpReflectManager.getResponseParams(tObj.getClass());
        Map<String, String> param_type = (Map<String, String>) objectMap.get(PARAM_TYPE);
        Map<String, Field> param_field = (Map<String, Field>) objectMap.get(PARAM_FIELD);
        if (param_type.size() == 0) {
            EasyLibLog.e(TAG + "parseJsonObject 网络请求返回参数请用@EasyMouse.mHttp.ResponseParam备注正确");
            return null;
        }
//        EasyLibLog.d(TAG + "clazz=" + clazz.getSimpleName() + " objClass=" + objClass + " jsonObject=" + jsonObject);

        /*T tObj;
        try {
            tObj = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "parseJsonObject InstantiationException error object");
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "parseJsonObject IllegalAccessException error object");
            return null;
        }*/

        for (String param : param_type.keySet()) {
            String type = null;
            try {
                if (jsonObject.has(param)) {
                    Object value = jsonObject.get(param);
                    if (value == null || value.toString().trim().length() == 0
                            || (!(value instanceof String) && value.toString().equals("null"))) {
                        continue;
                    }
                    Field field = param_field.get(param);
                    field.setAccessible(true);
                    type = param_type.get(param);

                    if (type.equals(Constant.FieldType.STRING)) {
                        if (value instanceof String) {
                            field.set(tObj, value);
                        } else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
                            field.set(tObj, value + "");
                        } else {
                            EasyLibLog.w(TAG + "value=" + value + " is not String、Integer、Long、Float、Double type");
                        }
                    } else if (type.equals(Constant.FieldType.INTEGER)) {
                        if (value instanceof String) {
                            value = Integer.parseInt((String) value);
                        }
                        if (!(value instanceof Integer)) {
                            EasyLibLog.w(TAG + "param=" + param + " is not Integer ");
                            continue;
                        }
                        field.setInt(tObj, (Integer) value);
                    } else if (type.equals(Constant.FieldType.FLOAT)) {
                        if (value instanceof String) {
                            value = Float.parseFloat((String) value);
                        }
                        if (!(value instanceof Float)) {
                            EasyLibLog.w(TAG + "param=" + param + " is not Float ");
                            continue;
                        }
                        field.setFloat(tObj, (Float) value);
                    } else if (type.equals(Constant.FieldType.DOUBLE)) {
                        if (value instanceof String) {
                            value = Double.parseDouble((String) value);
                        }
                        if (!(value instanceof Double)) {
                            EasyLibLog.w(TAG + "param=" + param + " is not Double ");
                            continue;
                        }
                        field.setDouble(tObj, (Double) value);
                    } else if (type.equals(Constant.FieldType.LONG)) {
                        if (value instanceof String) {
                            value = Long.parseLong((String) value);
                        }
                        if (!(value instanceof Long)) {
                            EasyLibLog.w(TAG + "param=" + param + " is not Long ");
                            continue;
                        }
                        field.setLong(tObj, (Long) value);
                    } else if (type.equals(Constant.FieldType.BOOLEAN)) {
                        if (value instanceof String) {
                            value = Boolean.parseBoolean((String) value);
                        }
                        if (!(value instanceof Boolean)) {
                            EasyLibLog.w(TAG + "param=" + param + " is not Boolean ");
                            continue;
                        }
                        field.setBoolean(tObj, (Boolean) value);
                    } else if (type.equals(Constant.FieldType.DATE)) {
                        if (value instanceof String) {
                            Date date = EasyUtil.mDateFormat.parser((String) value);
                            if (date != null) {
                                field.set(tObj, date);
                            }
                        }
                    } else if (type.contains(Constant.FieldType.MAP)) {
                        EasyLibLog.e(TAG + "parseJsonObject Constant.FieldType.MAP 不支持Map类型");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {
                        EasyLibLog.e(TAG + "parseJsonObject Constant.FieldType.ARRAY 不支持数组类型");
                    } else if (type.contains(Constant.FieldType.OBJECT)) {
                        if (tObj instanceof HttpResponse) {
                            if (value instanceof JSONArray) {
//                                Class clazz2 = Class.forName(((HttpResponse) tObj).objClass.getName());
                                List objList = parseJsonArray(((HttpResponse) tObj).objClass, (JSONArray) value);
                                field.set(tObj, objList);
                            } else if (value instanceof JSONObject) {
//                                Class clazz2 = Class.forName(((HttpResponse) tObj).objClass.getName());
                                Object object = parseJsonObject(((HttpResponse) tObj).objClass.newInstance(), (JSONObject) value);
                                field.set(tObj, object);
                            } else {
                                EasyLibLog.e(TAG + "parseJsonObject Constant.FieldType.OBJECT error");
                            }
                        } else {
                            EasyLibLog.e(TAG + "要解析object类型请继承HttpResponse");
                        }
                    } else if (type.contains(Constant.FieldType.LIST)) {
                        if (value instanceof JSONArray) {
                            String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                            Class clazz2 = Class.forName(clazzName);
                            List objList = parseJsonArray(clazz2, (JSONArray) value);
                            field.set(tObj, objList);
                        } else {
                            EasyLibLog.e(TAG + "parseJsonObject Constant.FieldType.LIST error");
                        }
                    } else if (type.contains(Constant.FieldType.CLASS)) {
                        if (value instanceof JSONObject) {
                            /*String clazzName = type.replace("class ", "").trim();
                            Class clazz2 = Class.forName(clazzName);*/
                            Object obj = parseJsonObject(field.getDeclaringClass(), (JSONObject) value);
                            field.set(tObj, obj);
                        } else {
                            EasyLibLog.e(TAG + "parseJsonObject Constant.FieldType.CLASS error");
                        }
                    } else {
                        EasyLibLog.e(TAG + "parseJsonObject 不支持该类型：" + type);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonObject JSONException：type=" + type + " param=" + param);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonObject IllegalAccessException：type=" + type + " param=" + param);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonObject ClassNotFoundException：type=" + type + " param=" + param);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonObject NumberFormatException：type=" + type + " param=" + param);
            } catch (InstantiationException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonObject InstantiationException：type=" + type + " param=" + param);
            }
        }
        return tObj;
    }

    /**
     * 解析jsonArray
     *
     * @param TClass
     * @param jsonArray
     * @return
     */
    private static <T> List<T> parseJsonArray(Class<T> TClass, JSONArray jsonArray) {
        List<T> list = new ArrayList<>();
        if (jsonArray == null) {
            EasyLibLog.e(TAG + "parseJsonArray clazz or jsonArray is null");
            return list;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            T TObj;
            try {
                TObj = TClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonArray InstantiationException");
                continue;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonArray IllegalAccessException");
                continue;
            }
            Object jsonObj;
            try {
                jsonObj = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonArray JSONException jsonObj");
                continue;
            }
            if (jsonObj instanceof JSONObject) {
                TObj = parseJsonObject(TObj, (JSONObject) jsonObj);
            } else {
                EasyLibLog.e(TAG + "parseJsonArray jsonArray.get(i) is not JSONObject");
            }
            if (TObj != null)
                list.add(TObj);
        }
        return list;
    }

}
