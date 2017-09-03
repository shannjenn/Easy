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
        try {
            if (obj instanceof String) {
                JSONObject object = new JSONObject((String) obj);
                result = parseJson(clazz, objClass, object);
            } else if (obj instanceof JSONObject) {
                EasyLog.d(TAG + "parseJson JSONObject");
                result = parseJsonObject(clazz, objClass, (JSONObject) obj);
            } else if (obj instanceof JSONArray) {
                EasyLog.d(TAG + "parseJson parseJsonArray");
                result = parseJsonArray(clazz, (JSONArray) obj);
            } else {
                EasyLog.e(TAG + "parseJson obj is not JSONObject or JSONArray");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJson JSONException");
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
//        EasyLog.d(TAG + "clazz=" + clazz + " objClass=" + objClass + " jsonObject=" + jsonObject);

        Object object = null;
        try {
            object = clazz.newInstance();
            for (String param : param_type.keySet()) {
                Field field = param_field.get(param);
                field.setAccessible(true);
                if (jsonObject.has(param)) {
                    String type = param_type.get(param);
                    if (type.equals(Constant.FieldType.CHAR)) {
                        String value = jsonObject.getString(param);
                        if (value.length() > 0) {
                            field.setChar(object, value.charAt(0));
                        }
                    } else if (type.equals(Constant.FieldType.STRING)) {
                        String value = jsonObject.getString(param);
                        field.set(object, value);
                    } else if (type.equals(Constant.FieldType.BYTE)) {
                        byte[] value = jsonObject.getString(param).getBytes();
                        if (value.length > 0) {
                            field.setByte(object, value[0]);
                        }
                    } else if (type.equals(Constant.FieldType.SHORT)) {
                        int value = jsonObject.getInt(param);
                        field.setShort(object, (short) value);
                    } else if (type.equals(Constant.FieldType.INTEGER)) {
                        int value = jsonObject.getInt(param);
                        field.setInt(object, value);
                    } else if (type.equals(Constant.FieldType.FLOAT)) {
                        double value = jsonObject.getDouble(param);
                        field.setFloat(object, (float) value);
                    } else if (type.equals(Constant.FieldType.DOUBLE)) {
                        double value = jsonObject.getDouble(param);
                        field.setDouble(object, value);
                    } else if (type.equals(Constant.FieldType.LONG)) {
                        long value = jsonObject.getLong(param);
                        field.setLong(object, value);
                    } else if (type.equals(Constant.FieldType.BOOLEAN)) {
                        boolean value = jsonObject.getBoolean(param);
                        field.setBoolean(object, value);
                    } else if (type.equals(Constant.FieldType.DATE)) {
                        String value = jsonObject.getString(param);
                        Date date = EasyUtil.DateFormat.parser(value);
                        field.set(object, date);
                    } else if (type.contains(Constant.FieldType.MAP)) {
                        EasyLog.e(TAG + "parseJsonObject Constant.FieldType.MAP 不支持Map类型");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {
                        EasyLog.e(TAG + "parseJsonObject Constant.FieldType.ARRAY 不支持数组类型");
                    } else if (type.contains(Constant.FieldType.LIST)) {
                        Object value = jsonObject.get(param);
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
                            Object value = jsonObject.get(param);
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
                            EasyLog.d(TAG + "parseJsonObject Constant.FieldType.OBJECT error 2");
                        }
                    } else if (type.contains(Constant.FieldType.CLASS)) {
                        Object value = jsonObject.get(param);
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJsonObject JSONException error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJsonObject ClassNotFoundException error");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJsonObject IllegalAccessException error");
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "parseJsonObject InstantiationException error");
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
    private static List<Object> parseJsonArray(Class clazz, JSONArray jsonArray) throws JSONException {
        List<Object> list = new ArrayList<>();
        if (jsonArray == null) {
            EasyLog.e(TAG + "parseJsonArray clazz or jsonArray is null");
            return list;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            Object obj = null;
            Object jsonObj = jsonArray.get(i);
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
