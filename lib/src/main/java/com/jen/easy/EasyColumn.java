package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：表名
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyColumn {
    /**
     * 字段名称
     */
    String value() default "";

    /**
     * 是否为主键
     */
    boolean primaryKey() default false;

    /**
     * 主键是否自增
     */
    boolean autoincrement() default false;

    /**
     * 失效，不做字段
     */
    boolean invalid() default false;
}
