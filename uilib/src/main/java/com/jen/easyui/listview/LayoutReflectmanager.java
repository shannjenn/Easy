package com.jen.easyui.listview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.jen.easyui.EasyUILog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/25.
 */

public class LayoutReflectmanager {
    static final String TEXT = "text";
    static final String IMAGE = "image";
    static final String ONCLICK = "onClick";
    static final String ONLONGCLICK = "onLongClick";

    /**
     * 布局
     *
     * @param obj
     * @return
     */
    public static Map bindItemLayout(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Map<Integer, String> txtIds = new HashMap<>();
        Map<Integer, Object> imgIds = new HashMap<>();
        List<Integer> onClickIds = new ArrayList<>();
        List<Integer> onLongClickIds = new ArrayList<>();
        map.put(TEXT, txtIds);
        map.put(IMAGE, imgIds);
        map.put(ONCLICK, onClickIds);
        map.put(ONLONGCLICK, onLongClickIds);

        if (obj == null || obj instanceof Class) {
            EasyUILog.e("clazz is null");
            return map;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(ItemLayout.class);
            if (!isAnno)
                continue;
            try {
                ItemLayout itemLayout = fields[i].getAnnotation(ItemLayout.class);
                Object value = fields[i].get(obj);

                int txtId = itemLayout.text();
                if (txtId != -1) {
                    if (value instanceof Character || value instanceof Integer || value instanceof Short
                            || value instanceof Long || value instanceof Float || value instanceof Double ||
                            value instanceof String) {
                        txtIds.put(txtId, txtId + "");
                    }
                }

                int imgId = itemLayout.image();
                if (imgId != -1) {
                    if (value instanceof Integer || value instanceof Drawable || value instanceof Bitmap
                            || value instanceof Uri) {
                        imgIds.put(imgId, imgId);
                    }
                }

                int onClickId = itemLayout.onClick();
                if (onClickId != -1) {
                    onClickIds.add(onClickId);
                }

                int onLongClickId = itemLayout.onLongClick();
                if (onLongClickId != -1) {
                    onLongClickIds.add(onLongClickId);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    static int getViewType(Object object) {
        if (object == null || object instanceof Class) {
            EasyUILog.e("object is null");
            return -1;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(ItemLayout.class);
            if (!isAnno)
                continue;
            ItemLayout itemLayout = fields[i].getAnnotation(ItemLayout.class);
            boolean isViewType = itemLayout.isViewType();
            if (isViewType) {
                try {
                    Object value = fields[i].get(object);
                    if (value instanceof Integer) {
                        return (int) value;
                    } else {
                        EasyUILog.e(ItemLayout.class.getSimpleName() + "必须为int类型");
                        return (int) -1;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return (int) -1;
                }
            }
        }
        return -1;
    }


}
