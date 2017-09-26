package com.jen.easyui.listview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Item布局注释
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemSource {

    boolean isViewType() default false;

    int text() default -1;

    int image() default -1;

    int onClick() default -1;

    int onLongClick() default -1;

}
