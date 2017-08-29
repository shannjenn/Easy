package com.jen.easyui.listview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jen on 2017/8/24.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemLayout {

    boolean isViewType() default false;

    int text();

    int image() default -1;

    int onClick() default -1;

    int onLongClick() default -1;

}
