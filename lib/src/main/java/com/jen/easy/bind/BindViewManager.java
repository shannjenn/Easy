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
            BindLog.exception(ExceptionType.NullPointerException, "参数不能为空");
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
                BindLog.exception(ExceptionType.IllegalAccessException, activity.getClass().toString());
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
                            BindLog.exception(ExceptionType.IllegalAccessException, activity.getClass().toString());
                        } catch (InvocationTargetException e) {
                            BindLog.exception(ExceptionType.InvocationTargetException, "InvocationTargetException " + activity.getClass().toString());
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
        if (obj == null || parent == null) {
            BindLog.exception(ExceptionType.NullPointerException, "参数不能为空");
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
                BindLog.exception(ExceptionType.IllegalAccessException, obj.getClass().toString());
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
                            BindLog.exception(ExceptionType.IllegalAccessException, obj.getClass().toString());
                        } catch (InvocationTargetException e) {
                            BindLog.exception(ExceptionType.InvocationTargetException, "InvocationTargetException " + obj.getClass().toString());
                        }
                    }
                });
            }
        }
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
