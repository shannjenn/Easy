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
     * 字段名称
     */
    String columnName();

    /**
     * 数据类型：
     * ColumnType.TYPE0TEXT文本类型，
     * ColumnType.TYPE1无符号整型，
     * ColumnType.TYPE2浮点类型，
     * ColumnType.TYPE3任何类型,
     * ColumnType.TYPE4空值类型
     */
    int columnType() default 0;

    /**
     * 是否为主键
     */
    boolean primaryKey() default false;
}
