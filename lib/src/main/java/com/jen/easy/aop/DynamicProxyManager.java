package com.jen.easy.aop;

import com.jen.easy.log.EasyLibLog;

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
    private final String TAG = DynamicProxyManager.class.getSimpleName() + " : ";
    private Object target;

    private Object beforeClzz;
    private Object afterClzz;
    private Method beforeMethod;
    private Method afterMethod;
    private Object[] beforeParams;
    private Object[] afterParams;

    protected Object bind(Object target) {
        if (target == null || target instanceof Class) {
            EasyLibLog.e(TAG + "BIND 绑定对象为空");
            return null;
        }
        this.target = target;
        try {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "BIND 切入对象为空");
        }
        return null;
    }

    protected void setBeforeMethod(Class<?> beforeClzz, Object... beforeParams) {
        if (beforeClzz == null) {
            EasyLibLog.e(TAG + "BIND 切入对象为空");
            return;
        }
        try {
            beforeMethod = AOPReflectManager.getBefore(beforeClzz);
            this.beforeClzz = beforeClzz.newInstance();
            this.beforeParams = beforeParams;
            for (int i = 0; i < beforeParams.length; i++) {
                if (beforeParams[i] == null) {
                    this.beforeClzz = null;
                    break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "BIND InstantiationException");
        } catch (IllegalAccessException e) {
            EasyLibLog.e(TAG + "BIND IllegalAccessException");
            e.printStackTrace();
        }
    }

    protected void setAfterMethod(Class<?> afterClzz, Object... afterParams) {
        if (afterClzz == null) {
            EasyLibLog.e(TAG + "切入对象为空");
            return;
        }
        try {
            afterMethod = AOPReflectManager.getAfter(afterClzz);
            this.afterClzz = afterClzz.newInstance();
            this.afterParams = afterParams;
            for (int i = 0; i < afterParams.length; i++) {
                if (afterParams[i] == null) {
                    this.afterClzz = null;
                    break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "DynamicProxyManager InstantiationException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "DynamicProxyManager IllegalAccessException");

        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
                            EasyLibLog.e(TAG + "invoke 方法参数超过8个");
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
                            EasyLibLog.e(TAG + "invoke 参数超过8个");
                            break;
                    }
                } else {
                    afterMethod.invoke(afterClzz);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "invoke IllegalAccessException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "invoke IllegalArgumentException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "invoke InvocationTargetException");
        }
        return result;
    }
}
