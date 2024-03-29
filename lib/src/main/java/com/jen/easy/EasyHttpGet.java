package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：网络请求Get
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyHttpGet {
    /**
     * 请求地址前序
     */
    String UrlBase() default "";
    /**
     * 请求地址后序
     */
    String UrlAppend() default "";

    /**
     * 返回实体类
     */
    Class Response() default Object.class;
}
