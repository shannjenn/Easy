package com.jen.easy.constant;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2018/2/22:20:37
 * 说明：
 */

public final class FieldType {

    public static boolean isChar(Class clazz) {
        return Character.class.equals(clazz) || char.class.equals(clazz);
    }

    public static boolean isString(Class clazz) {
        return String.class.equals(clazz);
    }

    public static boolean isByte(Class clazz) {
        return Byte.class.equals(clazz) || byte.class.equals(clazz);
    }

    public static boolean isShort(Class clazz) {
        return Short.class.equals(clazz) || short.class.equals(clazz);
    }

    public static boolean isInt(Class clazz) {
        return Integer.class.equals(clazz) || int.class.equals(clazz);
    }

    public static boolean isFloat(Class clazz) {
        return Float.class.equals(clazz) || float.class.equals(clazz);
    }

    public static boolean isDouble(Class clazz) {
        return Double.class.equals(clazz) || double.class.equals(clazz);
    }

    public static boolean isLong(Class clazz) {
        return Long.class.equals(clazz) || long.class.equals(clazz);
    }

    public static boolean isBoolean(Class clazz) {
        return Boolean.class.equals(clazz) || boolean.class.equals(clazz);
    }

    public static boolean isList(Class clazz) {
        return List.class.equals(clazz) || ArrayList.class.equals(clazz);
    }

    public static boolean isList(Field field) {
        return isList(field.getGenericType());
    }

    public static boolean isList(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getRawType().equals(List.class) ||
                    parameterizedType.getRawType().equals(ArrayList.class);
        } else {
            return false;
        }
    }

    public static boolean isMap(Class clazz) {
        return Map.class.equals(clazz) || HashMap.class.equals(clazz);
    }

    public static boolean isObject(Class clazz) {
        return Object.class.equals(clazz);
    }

    public static boolean isEntityClass(Class clazz) {//做最后判断
        return clazz.getClassLoader() != null && clazz.getClassLoader().getParent() != null;
    }

    /**
     * 1.$change 是Android Studio2.0的.Instant Run 的问题.
     * 2.serialVersionUID 序列号排除
     *
     * @param fieldName 变量名
     * @return 是否排除
     */
    public static boolean isOtherField(String fieldName) {
        switch (fieldName) {
            case "$change"://Android Studio2.0的.Instant Run 的问题.
            case "serialVersionUID"://序列号
                return true;
            default:
                return false;
        }
    }


    /**
     * 获取表中字段类型
     *
     * @param clazz 类
     * @return 表中字段类型
     */
    public static String getDBColumnType(Class clazz) {
        String type;
        if (isChar(clazz) || isString(clazz)) {
            type = "TEXT";

        } else if (isByte(clazz) || isShort(clazz) || isInt(clazz) || isLong(clazz) || isBoolean(clazz)) {
            type = "INTEGER";

        } else if (isFloat(clazz) || isDouble(clazz)) {
            type = "REAL";

        } else {
            type = null;
        }
        return type;
    }
}
