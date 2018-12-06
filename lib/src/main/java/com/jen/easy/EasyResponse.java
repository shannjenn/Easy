package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：网络返回参数
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyResponse {
    /**
     * 参数名称
     */
    String value() default "";

    /**
     * 请求参数类型
     */
    EasyResponseType type() default EasyResponseType.Param;
}
