package com.jen.easy.bind.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jen on 2017/7/26.
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyBindID {

    /**
     * 绑定ID值
     */
    int ID();
}
