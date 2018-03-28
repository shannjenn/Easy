package com.jen.easy.bind;

import com.jen.easy.Easy;
import com.jen.easy.constant.TAG;
import com.jen.easy.log.EasyLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
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
            EasyLog.w(TAG.EasyBind, "getFields clazz is null");
            return objectMap;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(Easy.BIND.ID.class);
            if (!isAnno)
                continue;
            Easy.BIND.ID easyID = field.getAnnotation(Easy.BIND.ID.class);
            int id = easyID.value();
            if (id == -1)
                continue;
            String type = field.getGenericType().toString();

            id_type.put(id, type);
            id_field.put(id, field);
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
            EasyLog.w(TAG.EasyBind, "getMethods clazz is null");
            return method_ids;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean isAnno = method.isAnnotationPresent(Easy.BIND.Method.class);
            if (!isAnno)
                continue;
            Easy.BIND.Method easyID = method.getAnnotation(Easy.BIND.Method.class);
            int[] ids = easyID.value();
            method_ids.put(method, ids);
        }
        return method_ids;
    }
}
