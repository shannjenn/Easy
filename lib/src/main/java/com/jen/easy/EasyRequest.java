package com.jen.easy;

import android.support.annotation.IntDef;

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

    @IntDef({Type.Param, Type.Head, Type.Url})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
        /*（请求/返回）参数*/
        int Param = 0;
        /*（请求/返回）头部*/
        int Head = 1;
        /*请求地址拼接*/
        int Url = 2;
    }

    /**
     * 参数名称
     */
    String value() default "";

    /**
     * 请求参数类型
     */
    @EasyRequest.Type
    int type() default Type.Param;
}
