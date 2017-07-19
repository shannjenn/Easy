package com.jen.easy.sqlite.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jen on 2017/7/18.
 * primaryKey：是否为主键
 * columnName：字段名称
 * columnType：数据类型，不输入默认文本类型
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyColumn {

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
     * 字段名称
     */
    String columnName();

    /**
     * 数据类型：
     * EasyColumn.TYPE0TEXT文本类型，
     * EasyColumn.TYPE1无符号整型，
     * EasyColumn.TYPE2浮点类型，
     * EasyColumn.TYPE3任何类型,
     * EasyColumn.TYPE4空值类型
     */
    int columnType() default 0;

    /**
     * 是否为主键
     */
    boolean primaryKey() default false;
}
