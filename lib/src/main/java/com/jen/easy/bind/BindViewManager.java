package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Jen on 2017/7/26.
 */

abstract class BindViewManager {
    private final String TAG = "BindViewManager : ";
    private WeakHashMap<String, Activity> mapAct = new WeakHashMap<>();

    /**
     * 绑定
     */
    protected void bind(final Activity activity) {
        if (activity == null) {
            EasyLibLog.e(TAG + "mBindView activity is null");
            return;
        }
        String name = activity.getClass().getName();
        if (!mapAct.containsKey(name)) {
            mapAct.put(name, activity);
        }

        Map<String, Object> objectMap = BindReflectManager.getFields(activity.getClass());
        Map<Integer, String> id_type = (Map<Integer, String>) objectMap.get(BindReflectManager.ID_TYPE);
        Map<Integer, Field> id_field = (Map<Integer, Field>) objectMap.get(BindReflectManager.ID_FIELD);
        for (int id : id_type.keySet()) {
            View view = activity.findViewById(id);
            Field fild = id_field.get(id);
            fild.setAccessible(true);
            try {
                fild.set(activity, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "mBindView  fild set value error");
            }
        }

        Map<Method, int[]> method_ids = BindReflectManager.getMethods(activity.getClass());
        for (final Method method : method_ids.keySet()) {
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
                            EasyLibLog.e(TAG + "mBindView  IllegalAccessException");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            EasyLibLog.e(TAG + "mBindView  InvocationTargetException");
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
            EasyLibLog.e(TAG + "inject obj or view is null");
            return;
        }

        Map<String, Object> objectMap = BindReflectManager.getFields(obj.getClass());
        Map<Integer, String> id_type = (Map<Integer, String>) objectMap.get(BindReflectManager.ID_TYPE);
        Map<Integer, Field> id_field = (Map<Integer, Field>) objectMap.get(BindReflectManager.ID_FIELD);
        for (int id : id_type.keySet()) {
            View view = parent.findViewById(id);
            Field fild = id_field.get(id);
            fild.setAccessible(true);
            try {
                fild.set(obj, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "inject fild set value error");
            }
        }

        Map<Method, int[]> method_ids = BindReflectManager.getMethods(obj.getClass());
        for (final Method method : method_ids.keySet()) {
            int[] ids = method_ids.get(method);
            for (int i = 0; i < ids.length; i++) {
                final View view = parent.findViewById(ids[i]);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);
                        try {
                            method.invoke(obj, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            EasyLibLog.e(TAG + "inject  IllegalAccessException");
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            EasyLibLog.e(TAG + "inject  InvocationTargetException");
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
            EasyLibLog.e(TAG + "activity is null");
            return;
        }
        String name = activity.getClass().getName();
        mapAct.remove(name);
    }

}
