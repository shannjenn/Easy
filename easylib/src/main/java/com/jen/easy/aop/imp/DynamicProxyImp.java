package com.jen.easy.aop.imp;

/**
 * 动态代理
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */
public interface DynamicProxyImp {

    /**
     * 绑定对象
     *
     * @param target
     * @return
     */
    public Object bind(Object target);

    /**
     * 切入前执行
     *
     * @param beforeClzz
     * @param beforeParams
     * @return
     */
    public DynamicProxyImp setBeforeMethod(Class<?> beforeClzz, Object[] beforeParams);

    /**
     * 切入后执行
     *
     * @param afterClzz
     * @param afterParams
     * @return
     */
    public DynamicProxyImp setAfterMethod(Class<?> afterClzz, Object[] afterParams);
}
