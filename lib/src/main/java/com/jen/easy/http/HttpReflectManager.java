package com.jen.easy.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.log.EasyLog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectManager {

    /**
     * 获取对象名参数
     *
     * @param obj
     * @return
     */
    static Map<String, String> getParams(Object obj) {
        Map<String, String> params = new HashMap<>();
        if (obj == null || obj instanceof Class) {
            EasyLog.e("getParams obj is null");
            return params;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyMouse.HTTP.Param.class);
            if (!isAnno)
                continue;
            EasyMouse.HTTP.Param param = fields[i].getAnnotation(EasyMouse.HTTP.Param.class);
            String paramName = param.value().trim();
            if (paramName.length() == 0) {
                continue;
            }
            try {
                String value = fields[i].get(obj) + "";
                params.put(paramName, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}
