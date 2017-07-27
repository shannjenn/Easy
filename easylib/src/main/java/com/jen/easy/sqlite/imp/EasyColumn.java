package com.jen.easy.sqlite.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jen on 2017/7/18.
 * primaryKey：是否为主键
 * columnName：字段名称
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyColumn {
    /**
     * 字段名称
     */
    String columnName();

    /**
     * 是否为主键
     */
    boolean primaryKey() default false;
}
