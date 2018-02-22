package com.jen.easy.constant;

/**
 * 作者：ShannJenn
 * 时间：2018/2/22:20:37
 * 说明：
 */

public final class FieldType {
    public static final String CHAR = "char";
    public static final String STRING = "class java.lang.String";

    public static final String BYTE = "class java.lang.Byte";
    public static final String SHORT = "class java.lang.Short";
    public static final String INTEGER = "int";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String LONG = "class java.lang.Long";
    public static final String BOOLEAN = "boolean";

//        public static final String DATE = "class java.util.Date";

    public static final String OBJECT = "class java.lang.Object";
    public static final String LIST = "java.util.List";
    public static final String MAP = "java.util.Map";
    public static final String ARRAY = "class [L";//数组
    public static final String CLASS = "class ";//做最后判断

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
        switch (fieldType) {
            case CHAR:
            case STRING:
                type = "TEXT";
                break;
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
                type = "INTEGER";
                break;
            case FLOAT:
            case DOUBLE:
                type = "REAL";
                break;
            case BOOLEAN:
                type = "INTEGER";
                break;
            default:
                type = null;
                break;
                /*case DATE:
                    type = "TEXT";
                    break;*/
        }
        return type;
    }
}
