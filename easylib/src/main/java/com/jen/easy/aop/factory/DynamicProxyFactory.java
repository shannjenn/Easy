package com.jen.easy.aop.factory;

import com.jen.easy.aop.DynamicProxyManager;

import java.lang.reflect.InvocationHandler;

/**
 * 动态代理
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */
public abstract class DynamicProxyFactory implements InvocationHandler {
    public static DynamicProxyFactory getProx() {
        return new DynamicProxyManager();
    }

    /**
     * 绑定对象
     *
     * @param target
     * @return
     */
    public abstract Object bind(Object target);

    /**
     * 切入前执行
     *
     * @param beforeClzz
     * @param beforeParams
     * @return
     */
    public abstract DynamicProxyFactory setBeforeMethod(Class<?> beforeClzz, Object[] beforeParams);

    /**
     * 切入后执行
     *
     * @param afterClzz
     * @param afterParams
     * @return
     */
    public abstract DynamicProxyFactory setAfterMethod(Class<?> afterClzz, Object[] afterParams);
}
