package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.EasyMouse;
import com.jen.easy.EasyUtil;
import com.jen.easy.constant.FieldType;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

class HttpJsonReflectManager {
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
            EasyLog.e("clazz or jsonObject is null");
            return null;
        }
        String modelName = getModelName(clazz);
        if (TextUtils.isEmpty(modelName)) {
            EasyLog.e("modelName is null");
            return null;
        }
        Map<String, Object> objectMap = getFields(clazz);
        if (objectMap.size() == 0) {
            EasyLog.e("objectMap size is empty");
            return null;
        }
        Map<String, String> param_type = (Map<String, String>) objectMap.get(PARAM_TYPE);
        Map<String, Field> param_field = (Map<String, Field>) objectMap.get(PARAM_FIELD);
        if (param_type.size() == 0 || param_field.size() == 0) {
            EasyLog.e("param_type size is empty");
            return null;
        }

        Object object = null;
        try {
            if (jsonObject.has(modelName)) {
                Object obj = jsonObject.get(modelName);
                if (obj instanceof JSONObject) {
                    object = parseJsonObject(clazz, (JSONObject) obj);
                } else if (obj instanceof JSONArray) {
                    object = parseJsonArray(clazz, (JSONArray) obj);
                }
                return obj;
            }
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
                        Date date = EasyUtil.DATAFORMAT.parser(value);
                        field.set(object, date);
                    } else if (type.contains(FieldType.LIST)) {
                        String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                        Class clazz2 = Class.forName(clazzName);
                        JSONArray array = jsonObject.getJSONArray(param);
                        List<Object> objList = parseJsonArray(clazz2, array);
                        field.set(object, objList);
                    } else if (type.contains("class ")) {
                        String clazzName = type.replace("class ", "").trim();
                        Class clazz2 = Class.forName(clazzName);
                        JSONObject jsonObj = jsonObject.getJSONObject(param);
                        Object obj = parseJsonObject(clazz2, jsonObj);
                        field.set(object, obj);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLog.e("JSONException error");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
            EasyLog.e("clazz or jsonArray is null");
            return list;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            Object obj = null;
            try {
                Object jsonObj = jsonArray.get(i);
                if (jsonObj instanceof JSONObject) {
                    obj = parseJsonObject(clazz, (JSONObject) jsonObj);
                } else {
                    EasyLog.e("jsonArray.get(i) is not JSONObject");
                }
            } catch (JSONException e) {
                EasyLog.e("parseJsonArray is error");
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
    private static String getModelName(Class clazz) {
        if (clazz == null) {
            EasyLog.e("clazz is not null");
            return null;
        }
        String modelName = null;
        boolean isAnno = clazz.isAnnotationPresent(EasyMouse.HTTP.Model.class);
        if (!isAnno) {
            EasyLog.e("clazz is not AnnotationPresent");
            return null;
        }
        EasyMouse.HTTP.Model model = (EasyMouse.HTTP.Model) clazz.getAnnotation(EasyMouse.HTTP.Model.class);
        modelName = model.modelName();
        return modelName;
    }

    /**
     * 获取字段和属性
     *
     * @param clazz
     * @return Map<String, List<String>>
     */
    private static Map<String, Object> getFields(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, String> param_type = new HashMap<>();
        Map<String, Field> param_field = new HashMap<>();
        objectMap.put(PARAM_TYPE, param_type);
        objectMap.put(PARAM_FIELD, param_field);
        if (clazz == null) {
            EasyLog.e("clazz is not null");
            return null;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyMouse.HTTP.Param.class);
            if (!isAnno)
                continue;
            EasyMouse.HTTP.Param param = fields[i].getAnnotation(EasyMouse.HTTP.Param.class);
            String paramName = param.paramName();
            String type = fields[i].getGenericType().toString();
            param_type.put(paramName, type);
            param_field.put(paramName, fields[i]);
        }
        return objectMap;
    }
}
