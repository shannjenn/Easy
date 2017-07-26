package com.jen.easy.bind;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 绑定activity的记得要解绑
 * Created by Jen on 2017/7/26.
 */

public class EasyBind {
    private static WeakHashMap<String, Activity> mapAct = new WeakHashMap<>();


    /**
     * 绑定
     */
    public static void bind(final Object obj, final View view) {
        if (obj == null || view == null) {
            BindLog.e("obj or view is null");
            return;
        }
        Map<Method, int[]> method_ids = BindReflectMan.getMethods(obj.getClass());
        for (final Method method : method_ids.keySet()) {
            int[] ids = method_ids.get(method);
            view.findViewById(ids[0]).setOnClickListener(new View.OnClickListener() {
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

        /*String name = activity.getClass().getName();
        if (!mapAct.containsKey(name)) {
            mapAct.put(name, activity);
        }*/
    }

    /**
     * 注入
     */
    public static void inject(final Object obj, final View view) {
        if (obj == null || view == null) {
            BindLog.e("obj or view is null");
            return;
        }
        Map<Method, int[]> method_ids = BindReflectMan.getMethods(obj.getClass());
        for (final Method method : method_ids.keySet()) {
            int[] ids = method_ids.get(method);
            view.findViewById(ids[0]).setOnClickListener(new View.OnClickListener() {
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
    public static void unBind(Activity activity) {
        if (activity == null) {
            BindLog.e("activity is null");
            return;
        }
        String name = activity.getClass().getName();
        mapAct.remove(name);
    }

}
