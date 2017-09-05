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
     * @param target 对象
     * @return 类
     */
    @Override
    public Object bind(Object target) {
        return super.bind(target);
    }

    /**
     * 切入前执行
     *
     * @param beforeClzz   切入前类
     * @param beforeParams 切入参数
     */
    @Override
    public void setBeforeMethod(Class<?> beforeClzz, Object... beforeParams) {
        super.setBeforeMethod(beforeClzz, beforeParams);
    }

    /**
     * 切入后执行
     *
     * @param afterClzz   切入后类
     * @param afterParams 切入参数
     */
    @Override
    public void setAfterMethod(Class<?> afterClzz, Object... afterParams) {
        super.setAfterMethod(afterClzz, afterParams);
    }

}
