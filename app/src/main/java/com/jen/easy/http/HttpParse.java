package com.jen.easy.http;

import com.jen.easy.log.Logcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jen on 2017/7/24.
 */

public class HttpParse {

    /**
     * json解析
     *
     * @param clazz
     * @param obj
     * @return
     */
    public static Object parseJson(Class clazz, Object obj) {
        if (obj == null) {
            Logcat.e("obj is null");
        }
        Object o = null;
        try {
            if (obj instanceof String) {
                JSONObject object = new JSONObject((String) obj);
                parseJson(clazz, object);
            } else if (obj instanceof JSONObject) {
                o = HttpJsonReflectMan.parseJsonObject(clazz, (JSONObject) obj);
            } else if (obj instanceof JSONArray) {
                o = HttpJsonReflectMan.parseJsonArray(clazz, (JSONArray) obj);
            } else {
                Logcat.e("obj is not JSONObject or JSONArray");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }

}
