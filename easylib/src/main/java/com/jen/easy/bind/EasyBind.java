package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class EasyBind {

    /**
     * 绑定
     */
    public static void bind(Activity activity) {
        EasyBindMan.bind(activity);
    }

    /**
     * 注入
     */
    static void inject(Object obj, View parent) {
        EasyBindMan.inject(obj, parent);
    }

    /**
     * 解除绑定
     *
     * @param activity
     */
    static void unbind(Activity activity) {
        EasyBindMan.unbind(activity);
    }
}
