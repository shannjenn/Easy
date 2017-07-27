package com.jen.easy.http.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网络请求对象名
 * Created by Jen on 2017/7/21.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyHttpModelName {

    /**
     * 对象名称
     *
     * @return
     */
    String modelName();
}
