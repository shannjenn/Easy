package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：网络请求参数
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyRequest {
    enum Type {
        /*（请求/返回）参数*/
        Param,
        /*（请求/返回）头部*/
        Head,
        /*请求地址拼接*/
        Url
    }

    /**
     * 参数名称
     */
    String value() default "";

    /**
     * 失效，不做请求参数
     */
    boolean invalid() default false;

    /**
     * 请求参数类型
     */
    Type type() default Type.Param;
}
