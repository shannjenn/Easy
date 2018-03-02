package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import com.jen.easy.constant.TAG;
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
            EasyLog.w(TAG.EasyBind, "mBindView activity is null");
            return;
        }
        String name = activity.getClass().getName();
        if (!mapAct.containsKey(name)) {
            mapAct.put(name, activity);
        }

        Map<String, Object> objectMap = BindReflectManager.getFields(activity.getClass());
        Map<Integer, String> id_type = (Map<Integer, String>) objectMap.get(BindReflectManager.ID_TYPE);
        Map<Integer, Field> id_field = (Map<Integer, Field>) objectMap.get(BindReflectManager.ID_FIELD);
        Set<Integer> sets = id_type.keySet();
        for (int id : sets) {
            View view = activity.findViewById(id);
            Field fild = id_field.get(id);
            fild.setAccessible(true);
            try {
                fild.set(activity, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLog.w(TAG.EasyBind, "mBindView  fild set value error");
            }
        }

        Map<Method, int[]> method_ids = BindReflectManager.getMethods(activity.getClass());
        Set<Method> methodSet = method_ids.keySet();
        for (final Method method : methodSet) {
            int[] ids = method_ids.get(method);
            for (int i = 0; i < ids.length; i++) {
                final View view = activity.findViewById(ids[i]);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);
                        try {
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            EasyLog.w(TAG.EasyBind, "mBindView  IllegalAccessException");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            EasyLog.w(TAG.EasyBind, "mBindView  InvocationTargetException");
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
            EasyLog.w(TAG.EasyBind, "inject obj or view is null");
            return;
        }

        Map<String, Object> objectMap = BindReflectManager.getFields(obj.getClass());
        Map<Integer, String> id_type = (Map<Integer, String>) objectMap.get(BindReflectManager.ID_TYPE);
        Map<Integer, Field> id_field = (Map<Integer, Field>) objectMap.get(BindReflectManager.ID_FIELD);
        Set<Integer> sets = id_type.keySet();
        for (int id : sets) {
            View view = parent.findViewById(id);
            Field fild = id_field.get(id);
            fild.setAccessible(true);
            try {
                fild.set(obj, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLog.w(TAG.EasyBind, "inject fild set value error");
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
                            EasyLog.w(TAG.EasyBind, "inject  IllegalAccessException");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            EasyLog.w(TAG.EasyBind, "inject  InvocationTargetException");
                        }
                    }
                });
            }
        }
    }

    /**
     * 解除绑定
     *
     * @param activity
     */
    protected void unbind(Activity activity) {
        if (activity == null) {
            EasyLog.w(TAG.EasyBind, "activity is null");
            return;
        }
        String name = activity.getClass().getName();
        mapAct.remove(name);
    }

}
