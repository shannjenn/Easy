package com.jen.easy.invalid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：禁止类（用于优化）
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyInvalid {
    /**
     * 参数名称
     */
    @InvalidType
    int[] value() default {InvalidType.Request, InvalidType.Response, InvalidType.Column};
}
