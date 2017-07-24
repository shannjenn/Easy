package com.jen.easy.http;

import android.text.TextUtils;

import com.jen.easy.log.Logcat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/24.
 */

public class HttpJsonParse {

    public static Object parseJson(Class clazz, Object obj) {
        if (obj == null) {
            Logcat.e("obj is null");
        }
        try {
            if (obj instanceof String) {
                JSONObject object = new JSONObject((String) obj);
                parseJson(clazz, object);
            } else if (obj instanceof JSONObject) {
                HttpJsonReflectMan.parseJsonObject(clazz, (JSONObject) obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
