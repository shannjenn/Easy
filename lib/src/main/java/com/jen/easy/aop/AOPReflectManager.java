package com.jen.easy.aop;

import com.jen.easy.Easy;
import com.jen.easy.constant.TAG;
import com.jen.easy.log.EasyLog;

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
            EasyLog.w(TAG.EasyAOP, "clazz is null");
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean isAnno = method.isAnnotationPresent(Easy.AOP.before.class);
            if (!isAnno)
                continue;
            return method;
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
            EasyLog.w(TAG.EasyAOP, "clazz is null");
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean isAnno = method.isAnnotationPresent(Easy.AOP.after.class);
            if (!isAnno)
                continue;
            return method;
        }
        return null;
    }
}
