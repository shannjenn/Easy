package com.jen.easy.aop;

import com.jen.easy.aop.imp.DynamicProxyImp;
import com.jen.easy.log.Logcat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */
public class DynamicProxyManager implements InvocationHandler, DynamicProxyImp {
    private Object target;

    private Class<?> beforeClzz;
    private Class<?> afterClzz;
    private Method beforeMethod;
    private Method afterMethod;
    private Object[] beforeParams;
    private Object[] afterParams;

    @Override
    public Object bind(Object target) {
        if (target == null) {
            Logcat.e("绑定对象为空----");
            return null;
        }
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public DynamicProxyManager setBeforeMethod(Class<?> beforeClzz, Object[] beforeParams) {
        if (beforeClzz == null) {
            Logcat.e("切入对象为空----");
            return this;
        }
        beforeMethod = AOPReflectManager.getMethods(beforeClzz);
        this.beforeClzz = beforeClzz;
        this.beforeParams = beforeParams;
        return this;
    }

    @Override
    public DynamicProxyManager setAfterMethod(Class<?> afterClzz, Object[] afterParams) {
        if (afterClzz == null) {
            Logcat.e("切入对象为空----");
            return this;
        }
        afterMethod = AOPReflectManager.getMethods(afterClzz);
        this.afterClzz = afterClzz;
        this.afterParams = afterParams;
        return this;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (beforeParams != null) {
            switch (beforeParams.length) {
                case 1:
                    beforeMethod.invoke(beforeClzz, beforeParams[0]);
                    break;
                case 2:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1]);
                    break;
                case 3:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1], beforeParams[2]);
                    break;
                case 4:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3]);
                    break;
                case 5:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4]);
                    break;
                case 6:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4], beforeParams[5]);
                    break;
                case 7:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4], beforeParams[5],
                            beforeParams[6]);
                    break;
                case 8:
                    beforeMethod.invoke(beforeClzz, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4], beforeParams[5],
                            beforeParams[6], beforeParams[7]);
                    break;
            }
        } else {
            beforeMethod.invoke(afterClzz);
        }
        result = method.invoke(target, args);
        if (afterParams != null) {
            switch (afterParams.length) {
                case 1:
                    afterMethod.invoke(afterClzz, afterParams[0]);
                    break;
                case 2:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1]);
                    break;
                case 3:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1], afterParams[2]);
                    break;
                case 4:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1], afterParams[2], afterParams[3]);
                    break;
                case 5:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4]);
                    break;
                case 6:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4], afterParams[5]);
                    break;
                case 7:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4], afterParams[5], afterParams[6]);
                    break;
                case 8:
                    afterMethod.invoke(afterClzz, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4], afterParams[5], afterParams[6],
                            afterParams[7]);
                    break;
            }
        } else {
            afterMethod.invoke(afterClzz);
        }
        return result;
    }
}
