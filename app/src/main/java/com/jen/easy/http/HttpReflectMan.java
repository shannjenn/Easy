package com.jen.easy.http;

import com.jen.easy.http.imp.EasyHttpModelName;
import com.jen.easy.http.imp.EasyHttpParamName;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 表操控
 * Created by Jen on 2017/7/19.
 */

public class HttpReflectMan {


    /**
     * 解析接口返回json格式值
     *
     * @param clazz
     * @param json
     * @return
     */
    public static Object parseHttpResult(Class clazz, String json) {
        String modelName = getModelName(clazz);
        if(modelName == null){

        }
        Map<String, String> param_type = getColumnNames(clazz);
        try {
            JSONObject object = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
    static Map<String, String> getColumnNames(Class clazz) {
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
}
