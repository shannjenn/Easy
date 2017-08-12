package com.jen.easy.bind;

import com.jen.easy.EasyA;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jen on 2017/7/19.
 */

abstract class BindReflectManager {
    /**
     * 全部列明和属性类型
     */
    static final String ID_TYPE = "id_type";
    /**
     * 全部列明和属性名称
     */
    static final String ID_FIELD = "id_field";

    /**
     * 获取字段
     *
     * @param clazz
     * @return
     */
    static Map<String, Object> getFields(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        Map<Integer, String> id_type = new HashMap<>();
        Map<Integer, Field> id_field = new HashMap<>();
        objectMap.put(ID_TYPE, id_type);
        objectMap.put(ID_FIELD, id_field);
        if (clazz == null) {
            BindLog.e("clazz is null");
            return objectMap;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyA.BIND.ID.class);
            if (!isAnno)
                continue;
            EasyA.BIND.ID easyID = fields[i].getAnnotation(EasyA.BIND.ID.class);
            int coulumnName = easyID.ID();
            String type = fields[i].getGenericType().toString();

            id_type.put(coulumnName, type);
            id_field.put(coulumnName, fields[i]);
        }
        return objectMap;
    }

    /**
     * 获取方法
     *
     * @param clazz
     * @return
     */
    static Map<Method, int[]> getMethods(Class clazz) {
        Map<Method, int[]> method_ids = new HashMap<>();
        if (clazz == null) {
            BindLog.e("clazz is null");
            return method_ids;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            boolean isAnno = methods[i].isAnnotationPresent(EasyA.BIND.Method.class);
            if (!isAnno)
                continue;
            EasyA.BIND.Method easyID = methods[i].getAnnotation(EasyA.BIND.Method.class);
            int[] ids = easyID.onClick();
            method_ids.put(methods[i], ids);
        }
        return method_ids;
    }
}
