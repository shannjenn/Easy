package com.jen.easy.http;

import com.jen.easy.EasyUtil;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.R.attr.type;
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
     * @param clazz
     * @param obj
     * @return
     */
    static Object parseJson(Class clazz, Class objClass, Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.e(TAG + "parseJson obj is null");
        }
        Object result = null;
        if (obj instanceof String) {
            JSONObject object = null;
            boolean jsonexception = false;
            try {
                object = new JSONObject((String) obj);
            } catch (JSONException e) {
                jsonexception = true;
            }
            if (jsonexception) {
                JSONArray objectArray = null;
                try {
                    objectArray = new JSONArray((String) obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                    EasyLog.e(TAG + "parseJson JSONException String");
                }
                result = parseJsonArray(clazz, objectArray);
            } else {
                result = parseJson(clazz, objClass, object);
            }
        } else if (obj instanceof JSONObject) {
            EasyLog.d(TAG + "parseJson JSONObject");
            result = parseJsonObject(clazz, objClass, (JSONObject) obj);
        } else if (obj instanceof JSONArray) {
            EasyLog.d(TAG + "parseJson parseJsonArray");
            result = parseJsonArray(clazz, (JSONArray) obj);
        } else {
            EasyLog.e(TAG + "parseJson obj is not JSONObject or JSONArray");
        }
        if (result == null) {
            EasyLog.e(TAG + "object == null");
        }
        return result;
    }

    /**
     * 解析接口返回json格式值
     *
     * @param clazz
     * @param jsonObject
     * @return
     */
    private static Object parseJsonObject(Class clazz, Class objClass, JSONObject jsonObject) {
        Map<String, Object> objectMap = HttpReflectManager.getResponseParams(clazz);
        Map<String, String> param_type = (Map<String, String>) objectMap.get(PARAM_TYPE);
        Map<String, Field> param_field = (Map<String, Field>) objectMap.get(PARAM_FIELD);
        if (param_type.size() == 0) {
            EasyLog.e(TAG + "parseJsonObject 网络请求返回参数请用@EasyMouse.HTTP.ResponseParam备注正确");
            return null;
        }
//        EasyLog.d(TAG + "clazz=" + clazz.getSimpleName() + " objClass=" + objClass + " jsonObject=" + jsonObject);

        Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJsonObject InstantiationException error object");
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJsonObject IllegalAccessException error object");
            return null;
        }

        for (String param : param_type.keySet()) {
            try {
                if (jsonObject.has(param)) {
                    Object value = jsonObject.get(param);
                    if (value == null || value.toString().trim().length() == 0
                            || (!(value instanceof String) && value.toString().equals("null"))) {
                        continue;
                    }
                    Field field = param_field.get(param);
                    field.setAccessible(true);
                    String type = param_type.get(param);

                    if (type.equals(Constant.FieldType.STRING)) {
                        if (value instanceof String) {
                            field.set(object, value);
                        } else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
                            field.set(object, value + "");
                        } else {
                            EasyLog.w(TAG + "value=" + value + " is not String、Integer、Long、Float、Double type");
                        }
                    } else if (type.equals(Constant.FieldType.INTEGER)) {
                        if (value instanceof String) {
                            value = Integer.parseInt((String) value);
                        }
                        if (!(value instanceof Integer)) {
                            EasyLog.w(TAG + "param=" + param + " is not Integer ");
                            continue;
                        }
                        field.setInt(object, (Integer) value);
                    } else if (type.equals(Constant.FieldType.FLOAT)) {
                        if (value instanceof String) {
                            value = Float.parseFloat((String) value);
                        }
                        if (!(value instanceof Float)) {
                            EasyLog.w(TAG + "param=" + param + " is not Float ");
                            continue;
                        }
                        field.setFloat(object, (Float) value);
                    } else if (type.equals(Constant.FieldType.DOUBLE)) {
                        if (value instanceof String) {
                            value = Double.parseDouble((String) value);
                        }
                        if (!(value instanceof Double)) {
                            EasyLog.w(TAG + "param=" + param + " is not Double ");
                            continue;
                        }
                        field.setDouble(object, (Double) value);
                    } else if (type.equals(Constant.FieldType.LONG)) {
                        if (value instanceof String) {
                            value = Long.parseLong((String) value);
                        }
                        if (!(value instanceof Long)) {
                            EasyLog.w(TAG + "param=" + param + " is not Long ");
                            continue;
                        }
                        field.setLong(object, (Long) value);
                    } else if (type.equals(Constant.FieldType.BOOLEAN)) {
                        if (value instanceof String) {
                            value = Boolean.parseBoolean((String) value);
                        }
                        if (!(value instanceof Boolean)) {
                            EasyLog.w(TAG + "param=" + param + " is not Boolean ");
                            continue;
                        }
                        field.setBoolean(object, (Boolean) value);
                    } else if (type.equals(Constant.FieldType.DATE)) {
                        if (value instanceof String) {
                            Date date = EasyUtil.dateFormat.parser((String) value);
                            if (date != null) {
                                field.set(object, date);
                            }
                        }
                    } else if (type.contains(Constant.FieldType.MAP)) {
                        EasyLog.e(TAG + "parseJsonObject Constant.FieldType.MAP 不支持Map类型");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {
                        EasyLog.e(TAG + "parseJsonObject Constant.FieldType.ARRAY 不支持数组类型");
                    } else if (type.contains(Constant.FieldType.LIST)) {
                        if (value instanceof JSONArray) {
                            String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                            Class clazz2 = Class.forName(clazzName);
                            List<Object> objList = parseJsonArray(clazz2, (JSONArray) value);
                            field.set(object, objList);
                        } else {
                            EasyLog.e(TAG + "parseJsonObject Constant.FieldType.LIST error");
                        }
                    } else if (type.contains(Constant.FieldType.OBJECT)) {
                        if (objClass != null) {
                            Object obj = null;
                            if (value instanceof JSONObject) {
                                obj = parseJsonObject(objClass, null, (JSONObject) value);
                            } else if (value instanceof JSONArray) {
                                obj = parseJsonArray(objClass, (JSONArray) value);
                            } else {
                                EasyLog.d(TAG + "parseJsonObject Constant.FieldType.OBJECT error 1");
                            }
                            field.set(object, obj);
                        } else {
                            EasyLog.w(TAG + "parseJsonObject Constant.FieldType.OBJECT error 2");
                        }
                    } else if (type.contains(Constant.FieldType.CLASS)) {
                        if (value instanceof JSONObject) {
                            String clazzName = type.replace("class ", "").trim();
                            Class clazz2 = Class.forName(clazzName);
                            Object obj = parseJsonObject(clazz2, null, (JSONObject) value);
                            field.set(object, obj);
                        } else {
                            EasyLog.e(TAG + "parseJsonObject Constant.FieldType.CLASS error");
                        }
                    } else {
                        EasyLog.e(TAG + "parseJsonObject 不支持该类型：" + type);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "parseJsonObject JSONException：type=" + type + " param=" + param);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "parseJsonObject IllegalAccessException：type=" + type + " param=" + param);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "parseJsonObject ClassNotFoundException：type=" + type + " param=" + param);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "parseJsonObject NumberFormatException：type=" + type + " param=" + param);
            }
        }

        return object;
    }

    /**
     * 解析jsonArray
     *
     * @param clazz
     * @param jsonArray
     * @return
     */
    private static List<Object> parseJsonArray(Class clazz, JSONArray jsonArray) {
        List<Object> list = new ArrayList<>();
        if (jsonArray == null) {
            EasyLog.e(TAG + "parseJsonArray clazz or jsonArray is null");
            return list;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            Object obj = null;
            Object jsonObj = null;
            try {
                jsonObj = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLog.e(TAG + "parseJsonArray JSONException jsonObj");
                continue;
            }
            if (jsonObj instanceof JSONObject) {
                obj = parseJsonObject(clazz, null, (JSONObject) jsonObj);
            } else {
                EasyLog.e(TAG + "parseJsonArray jsonArray.get(i) is not JSONObject");
            }
            if (obj != null)
                list.add(obj);
        }
        return list;
    }

}
