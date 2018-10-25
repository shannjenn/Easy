package com.jen.easy.aop;

import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
import com.jen.easy.log.EasyLog;

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

    private Object beforeClzz;
    private Object afterClzz;
    private Method beforeMethod;
    private Method afterMethod;
    private Object[] beforeParams;
    private Object[] afterParams;

    protected Object bind(Object target) {
        if (target == null) {
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
            return null;
        }
        this.target = target;
        try {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "mBindView 切入对象为空");
        }
        return null;
    }

    protected void setBeforeMethod(Class<?> beforeClzz, Object... beforeParams) {
        if (beforeClzz == null) {
            Throw.exception(ExceptionType.NullPointerException, "Class参数不能为空");
            return;
        }
        try {
            beforeMethod = AOPReflectManager.getBefore(beforeClzz);
            this.beforeClzz = beforeClzz.newInstance();
            this.beforeParams = beforeParams;
            for (Object beforeParam : beforeParams) {
                if (beforeParam == null) {
                    this.beforeClzz = null;
                    break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "mBindView InstantiationException");
        } catch (IllegalAccessException e) {
            EasyLog.w(TAG.EasyAOP, "mBindView IllegalAccessException");
            e.printStackTrace();
        }
    }

    protected void setAfterMethod(Class<?> afterClzz, Object... afterParams) {
        if (afterClzz == null) {
            Throw.exception(ExceptionType.NullPointerException, "Class参数不能为空");
            return;
        }
        try {
            afterMethod = AOPReflectManager.getAfter(afterClzz);
            this.afterClzz = afterClzz.newInstance();
            this.afterParams = afterParams;
            for (Object afterParam : afterParams) {
                if (afterParam == null) {
                    this.afterClzz = null;
                    break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "DynamicProxyManager InstantiationException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "DynamicProxyManager IllegalAccessException");

        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try {
            if (beforeClzz != null) {
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
                        default:
                            EasyLog.w(TAG.EasyAOP, "invoke 方法参数超过8个");
                            break;
                    }
                } else {
                    beforeMethod.invoke(beforeClzz);
                }
            }
            result = method.invoke(target, args);
            if (afterClzz != null) {
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
                        default:
                            EasyLog.w(TAG.EasyAOP, "invoke 参数超过8个");
                            break;
                    }
                } else {
                    afterMethod.invoke(afterClzz);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "invoke IllegalAccessException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "invoke IllegalArgumentException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyAOP, "invoke InvocationTargetException");
        }
        return result;
    }
}
