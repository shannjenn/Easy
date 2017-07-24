package com.jen.easy.constant;

/**
 * Created by Jen on 2017/7/20.
 */

public class FieldType {
    public static final String CHAR = "char";
    public static final String STRING = "class java.lang.String";

    public static final String BYTE = "class java.lang.Byte";
    public static final String SHORT = "class java.lang.Short";
    public static final String INTEGER = "int";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String LONG = "class java.lang.Long";

    public static final String BOOLEAN = "boolean";

    public static final String DATE = "class java.util.Date";


    /**
     * 获取表中字段类型
     *
     * @param fieldType 对象变量类型
     * @return表中字段类型
     */
    public static String getDBCoumnType(String fieldType) {
        String type = "TEXT";
        if (fieldType.equals(CHAR) || fieldType.equals(STRING)) {
            type = "TEXT";
        } else if (fieldType.equals(BYTE) || fieldType.equals(SHORT)
                || fieldType.equals(INTEGER) || fieldType.equals(LONG)) {
            type = "INTEEGER";
        } else if (fieldType.equals(FLOAT) || fieldType.equals(DOUBLE)) {
            type = "REAL";
        } else if (fieldType.equals(BOOLEAN)) {
            type = "INTEEGER";
        } else if (fieldType.equals(DATE)) {
            type = "TEXT";
        }
        return type;
    }
}
