package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：绑定控件ID
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyViewID {
    /**
     * 绑定ID值
     */
    int value() default -1;
}
