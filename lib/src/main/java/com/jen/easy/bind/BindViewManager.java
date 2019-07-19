package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import com.jen.easy.exception.BindLog;
import com.jen.easy.exception.ExceptionType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

abstract class BindViewManager {
    private WeakHashMap<String, Activity> mapAct = new WeakHashMap<>();

    /**
     * 绑定
     */
    protected void bind(final Activity activity) {
        if (activity == null) {
            BindLog.exception(ExceptionType.NullPointerException, "BindView bind 出现空指针：activity不能为空");
            return;
        }
        String name = activity.getClass().getName();
        if (!mapAct.containsKey(name)) {
            mapAct.put(name, activity);
        }
        BindReflectManager.FieldInfo fieldInfo = BindReflectManager.getFields(activity.getClass());
        for (int i = 0; i < fieldInfo.ids.size(); i++) {
            View view = activity.findViewById(fieldInfo.ids.get(i));
            Field field = fieldInfo.fields.get(i);
            field.setAccessible(true);
            try {
                field.set(activity, view);
            } catch (IllegalAccessException e) {
                throwErrorField(activity.getClass(), field, e);
            }
        }

        Map<Method, int[]> method_ids = BindReflectManager.getMethods(activity.getClass());
        Set<Method> methodSet = method_ids.keySet();
        for (final Method method : methodSet) {
            int[] ids = method_ids.get(method);
            for (int id : ids) {
                final View view = activity.findViewById(id);
                if (view == null)
                    continue;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);
                        try {
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            throwErrorMethod(activity.getClass(), method, e);
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            throwErrorMethod(activity.getClass(), method, e);
                        }
                    }
                });
            }
        }
    }

    /**
     * 注入
     */
    protected void inject(final Object obj, final View parent) {
        if (obj == null) {
            BindLog.exception(ExceptionType.NullPointerException, "BindView inject 出现空指针：类 不能为空");
            return;
        } else if (parent == null) {
            BindLog.exception(ExceptionType.NullPointerException, "BindView inject 出现空指针：View 不能为空");
            return;
        }
        BindReflectManager.FieldInfo fieldInfo = BindReflectManager.getFields(obj.getClass());
        for (int i = 0; i < fieldInfo.ids.size(); i++) {
            View view = parent.findViewById(fieldInfo.ids.get(i));
            Field field = fieldInfo.fields.get(i);
            field.setAccessible(true);
            try {
                field.set(obj, view);
            } catch (IllegalAccessException e) {
                throwErrorField(obj.getClass(), field, e);
            }
        }

        Map<Method, int[]> method_ids = BindReflectManager.getMethods(obj.getClass());
        Set<Method> methodSet = method_ids.keySet();
        for (final Method method : methodSet) {
            int[] ids = method_ids.get(method);
            for (int id : ids) {
                final View view = parent.findViewById(id);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);
                        try {
                            method.invoke(obj, view);
                        } catch (IllegalAccessException e) {
                            throwErrorMethod(obj.getClass(), method, e);
                        } catch (InvocationTargetException e) {
                            throwErrorMethod(obj.getClass(), method, e);
                        }
                    }
                });
            }
        }
    }

    private void throwErrorField(Class cls, Field field, Exception e) {
        BindLog.e("类名: " + cls.toString() + " 变量名: " + field.getName() + " 出现错误(没有找到该ID)：\n");
        e.printStackTrace();
    }

    private void throwErrorMethod(Class cls, Method method, Exception e) {
        BindLog.e("类名: " + cls.toString() + " 方法名: " + method.getName() + "出现错误：\n");
        e.printStackTrace();
    }

    /**
     * 解除绑定
     *
     * @param activity .
     */
    protected void unbind(Activity activity) {
        if (activity == null) {
            return;
        }
        String name = activity.getClass().getName();
        mapAct.remove(name);
    }
}
