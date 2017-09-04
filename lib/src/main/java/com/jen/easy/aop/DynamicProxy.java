package com.jen.easy.aop;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：切入编程
 */
public class DynamicProxy extends DynamicProxyManager {

    /**
     * 绑定对象
     *
     * @param target
     * @return
     */
    @Override
    public Object bind(Object target) {
        return super.bind(target);
    }

    /**
     * 切入前执行
     *
     * @param beforeClzz
     * @param beforeParams
     * @return
     */
    @Override
    public void setBeforeMethod(Class<?> beforeClzz, Object... beforeParams) {
        super.setBeforeMethod(beforeClzz, beforeParams);
    }

    /**
     * 切入后执行
     *
     * @param afterClzz
     * @param afterParams
     * @return
     */
    @Override
    public void setAfterMethod(Class<?> afterClzz, Object... afterParams) {
        super.setAfterMethod(afterClzz, afterParams);
    }

}
