package com.jen.easy.http.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网络请求参数名
 * Created by Jen on 2017/7/21.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyHttpParamName {

    /**
     * 参数名称
     *
     * @return
     */
    String paramName();
}
