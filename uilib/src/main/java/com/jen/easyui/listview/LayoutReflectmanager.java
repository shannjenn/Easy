package com.jen.easyui.listview;

import com.jen.easyui.EasyUILog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jen on 2017/8/25.
 */

class LayoutReflectmanager {
    private static final String INTEGER = "int";

    static final String FIELD = "field";
    static final String VIEWTYPES = "viewTypes";
    static final String LAYOUTS = "layouts";

    /**
     * 布局
     *
     * @param obj
     * @return
     */
    static Map<String, Object> getLaout(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null || obj instanceof Class) {
            EasyUILog.e("clazz is null");
            return map;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(Itemlayout.class);
            if (!isAnno)
                continue;
            String type = fields[i].getGenericType().toString();
            if (!type.equals(INTEGER)) {
                EasyUILog.e("viewType必须为int类型");
                return map;
            }
            Itemlayout itemlayout = fields[i].getAnnotation(Itemlayout.class);
            int[] viewTypes = itemlayout.viewType();
            int[] layouts = itemlayout.layout();
            if (viewTypes.length + layouts.length == 0 || viewTypes.length != layouts.length)
                break;
            map.put(FIELD, fields[i]);
            map.put(VIEWTYPES, viewTypes);
            map.put(LAYOUTS, layouts);
            return map;
        }
        return map;
    }


}
