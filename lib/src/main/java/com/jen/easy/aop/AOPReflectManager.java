package com.jen.easy.aop;

import com.jen.easy.EasyAopAfter;
import com.jen.easy.EasyAopBefore;
import com.jen.easy.exception.AopLog;
import com.jen.easy.exception.ExceptionType;

import java.lang.reflect.Method;

/**
 * 表操控
 * Created by Jen on 2017/7/19.
 */

class AOPReflectManager {
    /**
     * 获取方法
     *
     * @param clazz 类
     * @return 方法
     */
    static Method getBefore(Class clazz) {
        if (clazz == null) {
            AopLog.exception(ExceptionType.NullPointerException, "参数不能为空");
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean isAnno = method.isAnnotationPresent(EasyAopBefore.class);
            if (!isAnno)
                continue;
            return method;
        }
        return null;
    }

    /**
     * 获取方法
     *
     * @param clazz 类
     * @return 方法
     */
    static Method getAfter(Class clazz) {
        if (clazz == null) {
            AopLog.exception(ExceptionType.NullPointerException, "参数不能为空");
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean isAnno = method.isAnnotationPresent(EasyAopAfter.class);
            if (!isAnno)
                continue;
            return method;
        }
        return null;
    }
}
