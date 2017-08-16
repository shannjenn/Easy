package com.jen.easy.aop;

import com.jen.easy.EasyMouse;
import com.jen.easy.log.Logcat;

import java.lang.reflect.Method;

/**
 * 表操控
 * Created by Jen on 2017/7/19.
 */

class AOPReflectManager {

    /**
     * 获取方法
     *
     * @param clazz
     * @return
     */
    static Method getBefore(Class clazz) {
        if (clazz == null) {
            Logcat.e("clazz is null");
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            boolean isAnno = methods[i].isAnnotationPresent(EasyMouse.AOP.before.class);
            if (!isAnno)
                continue;
            return methods[i];
        }
        return null;
    }

    /**
     * 获取方法
     *
     * @param clazz
     * @return
     */
    static Method getAfter(Class clazz) {
        if (clazz == null) {
            Logcat.e("clazz is null");
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            boolean isAnno = methods[i].isAnnotationPresent(EasyMouse.AOP.after.class);
            if (!isAnno)
                continue;
            return methods[i];
        }
        return null;
    }
}
