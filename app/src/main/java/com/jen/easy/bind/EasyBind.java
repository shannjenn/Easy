package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Jen on 2017/7/26.
 */

public class EasyBind {
    private static WeakHashMap<String, Activity> mapAct = new WeakHashMap<>();


    /**
     * 绑定
     */
    public static void bind(final Activity activity) {
        if (activity == null) {
            BindLog.e("activity is null");
            return;
        }
        String name = activity.getClass().getName();
        if (!mapAct.containsKey(name)) {
            mapAct.put(name, activity);
        }

        Map<String, Object> objectMap = BindReflectMan.getFields(activity.getClass());
        if (objectMap.size() > 0) {
            Map<Integer, String> id_type = (Map<Integer, String>) objectMap.get(BindReflectMan.ID_TYPE);
            Map<Integer, Field> id_field = (Map<Integer, Field>) objectMap.get(BindReflectMan.ID_FIELD);
            for (int id : id_type.keySet()) {
                View view = activity.findViewById(id);
                Field fild = id_field.get(id);
                fild.setAccessible(true);
                try {
                    fild.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    BindLog.e("fild set value error");
                }
            }
        }

        Map<Method, int[]> method_ids = BindReflectMan.getMethods(activity.getClass());
        for (final Method method : method_ids.keySet()) {
            int[] ids = method_ids.get(method);
            final View view = activity.findViewById(ids[0]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    method.setAccessible(true);
                    try {
                        method.invoke(activity, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 注入
     */
    public static void inject(final Object obj, final View parent) {
        if (obj == null || parent == null) {
            BindLog.e("obj or view is null");
            return;
        }

        Map<String, Object> objectMap = BindReflectMan.getFields(obj.getClass());
        if (objectMap.size() > 0) {
            Map<Integer, String> id_type = (Map<Integer, String>) objectMap.get(BindReflectMan.ID_TYPE);
            Map<Integer, Field> id_field = (Map<Integer, Field>) objectMap.get(BindReflectMan.ID_FIELD);
            for (int id : id_type.keySet()) {
                View view = parent.findViewById(id);
                Field fild = id_field.get(id);
                fild.setAccessible(true);
                try {
                    fild.set(obj, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    BindLog.e("fild set value error");
                }
            }
        }

        Map<Method, int[]> method_ids = BindReflectMan.getMethods(obj.getClass());
        for (final Method method : method_ids.keySet()) {
            int[] ids = method_ids.get(method);
            final View view = parent.findViewById(ids[0]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    method.setAccessible(true);
                    try {
                        method.invoke(obj, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 解除绑定
     *
     * @param activity
     */
    public static void unbind(Activity activity) {
        if (activity == null) {
            BindLog.e("activity is null");
            return;
        }
        String name = activity.getClass().getName();
        mapAct.remove(name);
    }

}
