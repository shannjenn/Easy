package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
import com.jen.easy.log.EasyLog;

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
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
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
                e.printStackTrace();
                EasyLog.w(TAG.EasyBind, "mBindView  field set value error " + activity.getClass());
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
                            e.printStackTrace();
                            EasyLog.e(TAG.EasyBind, "mBindView  IllegalAccessException " + activity.getClass());
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            EasyLog.e(TAG.EasyBind, "mBindView  InvocationTargetException " + activity.getClass());
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
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
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
                e.printStackTrace();
                EasyLog.w(TAG.EasyBind, "inject field set value error " + obj.getClass());
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
                            e.printStackTrace();
                            EasyLog.e(TAG.EasyBind, "inject  IllegalAccessException " + obj.getClass());
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            EasyLog.e(TAG.EasyBind, "inject  InvocationTargetException " + obj.getClass());
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
            Throw.exception(ExceptionType.NullPointerException, "参数不能为空");
            return;
        }
        String name = activity.getClass().getName();
        mapAct.remove(name);
    }

}
