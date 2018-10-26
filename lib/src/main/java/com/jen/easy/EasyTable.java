package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：数据库
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyTable {
    /**
     * 表名
     */
    String value();
}
