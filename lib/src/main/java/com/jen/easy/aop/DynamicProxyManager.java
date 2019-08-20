package com.jen.easy.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */
abstract class DynamicProxyManager implements InvocationHandler {
    private Object target;

    private Object beforeClass;
    private Object afterClass;
    private Method beforeMethod;
    private Method afterMethod;
    private Object[] beforeParams;
    private Object[] afterParams;

    protected Object bind(Object target) {
        if (target == null) {
            AopLog.e("参数不能为空");
            return null;
        }
        this.target = target;
        try {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        } catch (IllegalArgumentException e) {
            AopLog.e("切入对象为空");
        }
        return null;
    }

    protected void setBeforeMethod(Class<?> beforeClzz, Object... beforeParams) {
        if (beforeClzz == null) {
            AopLog.e("Class参数不能为空");
            return;
        }
        try {
            beforeMethod = AOPReflectManager.getBefore(beforeClzz);
            this.beforeClass = beforeClzz.newInstance();
            this.beforeParams = beforeParams;
            for (Object beforeParam : beforeParams) {
                if (beforeParam == null) {
                    this.beforeClass = null;
                    break;
                }
            }
        } catch (InstantiationException e) {
            AopLog.e("setBeforeMethod方法错");
        } catch (IllegalAccessException e) {
            AopLog.e("setBeforeMethod方法错");
        }
    }

    protected void setAfterMethod(Class<?> afterClzz, Object... afterParams) {
        if (afterClzz == null) {
            AopLog.e("Class参数不能为空");
            return;
        }
        try {
            afterMethod = AOPReflectManager.getAfter(afterClzz);
            this.afterClass = afterClzz.newInstance();
            this.afterParams = afterParams;
            for (Object afterParam : afterParams) {
                if (afterParam == null) {
                    this.afterClass = null;
                    break;
                }
            }
        } catch (InstantiationException e) {
            AopLog.e("setAfterMethod方法错");
        } catch (IllegalAccessException e) {
            AopLog.e("setAfterMethod方法错");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try {
            if (beforeClass != null) {
                if (beforeParams != null) {
                    switch (beforeParams.length) {
                        case 1:
                            beforeMethod.invoke(beforeClass, beforeParams[0]);
                            break;
                        case 2:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1]);
                            break;
                        case 3:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1], beforeParams[2]);
                            break;
                        case 4:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3]);
                            break;
                        case 5:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4]);
                            break;
                        case 6:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4], beforeParams[5]);
                            break;
                        case 7:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4], beforeParams[5],
                                    beforeParams[6]);
                            break;
                        case 8:
                            beforeMethod.invoke(beforeClass, beforeParams[0], beforeParams[1], beforeParams[2], beforeParams[3], beforeParams[4], beforeParams[5],
                                    beforeParams[6], beforeParams[7]);
                            break;
                        default:
                            AopLog.e("invoke 方法参数超过8个");
                            break;
                    }
                } else {
                    beforeMethod.invoke(beforeClass);
                }
            }
            result = method.invoke(target, args);
            if (afterClass != null) {
                if (afterParams != null) {
                    switch (afterParams.length) {
                        case 1:
                            afterMethod.invoke(afterClass, afterParams[0]);
                            break;
                        case 2:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1]);
                            break;
                        case 3:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1], afterParams[2]);
                            break;
                        case 4:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1], afterParams[2], afterParams[3]);
                            break;
                        case 5:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4]);
                            break;
                        case 6:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4], afterParams[5]);
                            break;
                        case 7:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4], afterParams[5], afterParams[6]);
                            break;
                        case 8:
                            afterMethod.invoke(afterClass, afterParams[0], afterParams[1], afterParams[2], afterParams[3], afterParams[4], afterParams[5], afterParams[6],
                                    afterParams[7]);
                            break;
                        default:
                            AopLog.e("invoke 方法参数超过8个");
                            break;
                    }
                } else {
                    afterMethod.invoke(afterClass);
                }
            }
        } catch (IllegalAccessException e) {
            AopLog.e("invoke 方法错误");
        } catch (IllegalArgumentException e) {
            AopLog.e("invoke 方法错误");
        } catch (InvocationTargetException e) {
            AopLog.e("InvocationTargetException invoke 方法错误");
        }
        return result;
    }
}
