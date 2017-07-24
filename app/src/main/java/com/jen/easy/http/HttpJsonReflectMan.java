package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.http.imp.EasyHttpModelName;
import com.jen.easy.http.imp.EasyHttpParamName;
import com.jen.easy.util.DataFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpJsonReflectMan {
    static String PARAM_TYPE = "param_type";
    static String PARAM_FIELD = "param_field";

    /**
     * 解析接口返回json格式值
     *
     * @param clazz
     * @param jsonObject
     * @return
     */
    static Object parseJsonObject(Class clazz, JSONObject jsonObject) {
        if (clazz == null || jsonObject == null) {
            HttpLog.e("clazz or jsonObject is null");
            return null;
        }
        Map<String, Object> objectMap = getFields(clazz);
        if (objectMap.size() == 0) {
            HttpLog.e("objectMap size is empty");
            return null;
        }
        Map<String, String> param_type = (Map<String, String>) objectMap.get(PARAM_TYPE);
        Map<String, Field> param_field = (Map<String, Field>) objectMap.get(PARAM_FIELD);
        if (param_type.size() == 0 || param_field.size() == 0) {
            HttpLog.e("param_type size is empty");
            return null;
        }

        Object object = null;
        try {
            object = clazz.newInstance();
            for (String param : param_type.keySet()) {
                Field field = param_field.get(param);
                field.setAccessible(true);

                if (jsonObject.has(param)) {
                    String type = param_type.get(param);

                    if (type.equals(FieldType.CHAR)) {
                        String value = jsonObject.getString(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.STRING)) {
                        String value = jsonObject.getString(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.BYTE)) {
                        String value = jsonObject.getString(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.SHORT)) {
                        int value = jsonObject.getInt(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.INTEGER)) {
                        int value = jsonObject.getInt(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.FLOAT)) {
                        double value = jsonObject.getDouble(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.DOUBLE)) {
                        double value = jsonObject.getDouble(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.LONG)) {
                        long value = jsonObject.getLong(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.BOOLEAN)) {
                        boolean value = jsonObject.getBoolean(param);
                        field.set(object, value);
                    } else if (type.equals(FieldType.DATE)) {
                        String value = jsonObject.getString(param);
                        Date date = DataFormat.parser(value);
                        field.set(object, date);
                    } /*else if(){
                        Class<?> clazz2 = Class.forName(clazzName);
                    }*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            HttpLog.e("JSONException error");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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
    static List<Object> parseJsonArray(Class clazz, JSONArray jsonArray) {
        List<Object> list = new ArrayList<>();
        if (jsonArray == null) {
            HttpLog.e("clazz or jsonArray is null");
            return list;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            Object obj = null;
            try {
                Object jsonObj = jsonArray.get(i);
                if (jsonObj instanceof JSONObject) {
                    obj = parseJsonObject(clazz, (JSONObject) jsonObj);
                } else {
                    HttpLog.e("jsonArray.get(i) is not JSONObject");
                }
            } catch (JSONException e) {
                HttpLog.e("parseJsonArray is error");
                e.printStackTrace();
            }
            list.add(obj);
        }
        return list;
    }

    /**
     * 获取对象名
     *
     * @param clazz
     * @return
     */
    static String getModelName(Class clazz) {
        String modelName = null;
        Annotation[] anns = clazz.getDeclaredAnnotations();
        for (int i = 0; i < anns.length; i++) {
            if (anns[i] instanceof EasyHttpModelName) {
                EasyHttpModelName easyTable = (EasyHttpModelName) anns[i];
                modelName = easyTable.modelName();
                break;
            }
        }
        return modelName;
    }

    /**
     * 获取属性
     *
     * @param clazz
     * @return Map<String, List<String>>
     */
    static Map<String, String> getParamNames(Class clazz) {
        Map<String, String> param_type = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] anns = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyHttpParamName) {
                    EasyHttpParamName easyField = (EasyHttpParamName) anns[j];
                    String coulumnName = easyField.paramName();
                    if (coulumnName == null) {
                        continue;
                    }
                    String type = fields[i].getGenericType().toString();
                    param_type.put(coulumnName, type);
                    break;
                }
            }
        }
        return param_type;
    }

    /**
     * 获取字段和属性
     *
     * @param clazz
     * @return Map<String, List<String>>
     */
    static Map<String, Object> getFields(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, String> param_type = new HashMap<>();
        Map<String, Field> param_field = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] anns = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyHttpParamName) {
                    EasyHttpParamName easyField = (EasyHttpParamName) anns[j];
                    String coulumnName = easyField.paramName();
                    if (coulumnName == null) {
                        continue;
                    }
                    String type = fields[i].getGenericType().toString();

                    param_type.put(coulumnName, type);
                    param_field.put(coulumnName, fields[i]);
                    break;
                }
            }
        }
        objectMap.put(PARAM_TYPE, param_type);
        objectMap.put(PARAM_FIELD, param_field);
        return objectMap;
    }
}
