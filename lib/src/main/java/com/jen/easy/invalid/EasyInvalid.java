package com.jen.easy.invalid;

import android.support.annotation.IntDef;

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

    @IntDef({Type.Request, Type.Response, Type.Column})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
        int Request = 0;//请求参数失效
        int Response = 1;//返回参数失效
        int Column = 2;//数据库列失效
    }

    /**
     * 参数名称
     */
    @Type
    int[] value() default {Type.Request, Type.Response, Type.Column};
}
