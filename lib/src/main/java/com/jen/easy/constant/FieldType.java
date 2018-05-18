package com.jen.easy.constant;

import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2018/2/22:20:37
 * 说明：
 */

public final class FieldType {

    public static boolean isChar(String type) {
        return char.class.toString().equals(type);
    }

    public static boolean isString(String type) {
        return String.class.toString().equals(type);
    }

    public static boolean isByte(String type) {
        return Byte.class.toString().equals(type) || byte.class.toString().equals(type);
    }

    public static boolean isShort(String type) {
        return Short.class.toString().equals(type) || short.class.toString().equals(type);
    }

    public static boolean isInt(String type) {
        return Integer.class.toString().equals(type) || int.class.toString().equals(type);
    }

    public static boolean isFloat(String type) {
        return Float.class.toString().equals(type) || float.class.toString().equals(type);
    }

    public static boolean isDouble(String type) {
        return Double.class.toString().equals(type) || double.class.toString().equals(type);
    }

    public static boolean isLong(String type) {
        return Long.class.toString().equals(type) || long.class.toString().equals(type);
    }

    public static boolean isBoolean(String type) {
        return Boolean.class.toString().equals(type) || boolean.class.toString().equals(type);
    }

    public static boolean isList(String type) {
        return type.contains(List.class.toString().replace("interface ", ""));
    }

    public static boolean isMap(String type) {
        return type.contains(Map.class.toString().replace("interface ", ""));
    }

    public static boolean isObject(String type) {
        return Object.class.toString().equals(type);
    }

    public static boolean isClass(String type) {//做最后判断
        return type.contains("class ");
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
     * @param fieldType 对象变量类型
     * @return 表中字段类型
     */
    public static String getDBColumnType(String fieldType) {
        String type;
        if (isChar(fieldType) || isString(fieldType)) {
            type = "TEXT";

        } else if (isByte(fieldType) || isShort(fieldType) || isInt(fieldType) || isLong(fieldType) || isBoolean(fieldType)) {
            type = "INTEGER";

        } else if (isFloat(fieldType) || isDouble(fieldType)) {
            type = "REAL";

        } else {
            type = null;

                /*case DATE:
                    type = "TEXT";
                    break;*/
        }
        return type;
    }
}
