package com.jen.easy.bind;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easy.exception.BindLog;
import com.jen.easy.exception.ExceptionType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */
abstract class BindReflectManager {

    static class FieldInfo {
        List<Integer> ids;
        //        List<String> types;
        List<Field> fields;

        FieldInfo() {
            ids = new ArrayList<>();
//            types = new ArrayList<>();
            fields = new ArrayList<>();
        }
    }

    /**
     * 获取字段
     *
     * @param clazz 类
     * @return 值
     */
    static FieldInfo getFields(Class clazz) {
        FieldInfo fieldInfo = new FieldInfo();
        if (clazz == null) {
            BindLog.exception(ExceptionType.NullPointerException,"参数不能为空");
            return fieldInfo;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnnotation = field.isAnnotationPresent(EasyBindId.class);
            if (!isAnnotation)
                continue;
            EasyBindId easyID = field.getAnnotation(EasyBindId.class);
            int id = easyID.value();
            if (id == -1)
                continue;
//            String type = field.getGenericType().toString();
            fieldInfo.ids.add(id);
//            fieldInfo.types.add(type);
            fieldInfo.fields.add(field);
        }
        return fieldInfo;
    }

    /**
     * 获取方法
     *
     * @param clazz .
     * @return .
     */
    static Map<Method, int[]> getMethods(Class clazz) {
        Map<Method, int[]> method_ids = new HashMap<>();
        if (clazz == null) {
            BindLog.exception(ExceptionType.NullPointerException, "参数不能为空");
            return method_ids;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean isAnnotation = method.isAnnotationPresent(EasyBindClick.class);
            if (!isAnnotation)
                continue;
            EasyBindClick easyID = method.getAnnotation(EasyBindClick.class);
            int[] ids = easyID.value();
            method_ids.put(method, ids);
        }
        return method_ids;
    }
}
