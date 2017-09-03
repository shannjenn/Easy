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

    /**
     * json解析
     *
     * @param clazz
     * @param obj
     * @return
     */
    static Object parseJson(Class clazz, Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.e("obj is null");
        }
        Object result = null;
        try {
            if (obj instanceof String) {
                JSONObject object = new JSONObject((String) obj);
                parseJson(clazz, object);
            } else if (obj instanceof JSONObject) {
                result = parseJsonObject(clazz, (JSONObject) obj);
            } else if (obj instanceof JSONArray) {
                result = parseJsonArray(clazz, (JSONArray) obj);
            } else {
                EasyLog.e("obj is not JSONObject or JSONArray");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
    private static Object parseJsonObject(Class clazz, JSONObject jsonObject) {
        Map<String, Object> objectMap = HttpReflectManager.getResponseParams(clazz);
        Map<String, String> param_type = (Map<String, String>) objectMap.get(PARAM_TYPE);
        Map<String, Field> param_field = (Map<String, Field>) objectMap.get(PARAM_FIELD);
        if (param_type.size() == 0) {
            EasyLog.e("网络请求返回参数请用@EasyMouse.HTTP.ResponseParam备注正确");
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

                    if (Constant.FieldType.isBaseicType(type)) {
                        Object value = jsonObject.get(param);
                        field.set(object, value);
                    } else if (type.equals(Constant.FieldType.DATE)) {
                        String value = jsonObject.getString(param);
                        Date date = EasyUtil.DATA.parser(value);
                        field.set(object, date);
                    } else if (type.contains(Constant.FieldType.LIST)) {
                        String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                        Class clazz2 = Class.forName(clazzName);
                        JSONArray array = jsonObject.getJSONArray(param);
                        List<Object> objList = parseJsonArray(clazz2, array);
                        field.set(object, objList);
                    } else if (type.contains(Constant.FieldType.MAP)) {
                        EasyLog.e("Constant.FieldType.MAP 不支持Map类型");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {
                        EasyLog.e("Constant.FieldType.ARRAY 不支持数组类型");
                    } else if (type.contains(Constant.FieldType.CLASS)) {
                        String clazzName = type.replace("class ", "").trim();
                        Class clazz2 = Class.forName(clazzName);
                        JSONObject jsonObj = jsonObject.getJSONObject(param);
                        Object obj = parseJsonObject(clazz2, jsonObj);
                        field.set(object, obj);
                    } else {
                        EasyLog.e("不支持该类型：" + type);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLog.e("JSONException error");
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.e("InstantiationException error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            EasyLog.e("ClassNotFoundException error");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.e("IllegalAccessException error");
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
            if (obj != null)
                list.add(obj);
        }
        return list;
    }

}
