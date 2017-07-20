package com.jen.easy.sqlite;

/**
 * Created by Administrator on 2017/7/19.
 */

public class ColumnType {
    /**
     * TEXT文本类型
     */
    public static final int TYPE0 = 0;
    /**
     * INTEEGER无符号整型
     */
    public static final int TYPE1 = 1;
    /**
     * REAL浮点类型
     */
    public static final int TYPE2 = 2;
    /**
     * BLOB任何类型
     */
    public static final int TYPE3 = 3;
    /**
     * NULL空值类型
     */
    public static final int TYPE4 = 4;


    /**
     * 获取字段类型
     *
     * @param fieldType
     * @return
     */
    public static String getFieldType(int fieldType) {
        String type = "TEXT";
        switch (fieldType) {
            case TYPE0:
                type = "TEXT";
                break;
            case TYPE1:
                type = "INTEEGER";
                break;
            case TYPE2:
                type = "REAL";
                break;
            case TYPE3:
                type = "NULL";
                break;
            case TYPE4:
                type = "NULL";
                break;
        }
        return type;
    }
}
