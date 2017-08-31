package com.jen.easy.parse;

import android.text.TextUtils;

import com.jen.easy.parse.imp.HttpParseImp;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.jen.easy.parse.HttpJsonReflectManager.getJsonObjectName;

/**
 * Created by Jen on 2017/7/24.
 */

public class HttpParseManager implements HttpParseImp {

    /**
     * json解析
     *
     * @param clazz
     * @param obj
     * @return
     */
    @Override
    public Object parseJson(Class clazz, Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.e("obj is null");
        }
        String modelName = getJsonObjectName(clazz);
        if (TextUtils.isEmpty(modelName)) {
            EasyLog.e("请检查是否已经增加注释：EasyMouse.Http.Model、EasyMouse.Http.Param");
            return null;
        }
        Object o = null;
        try {
            if (obj instanceof String) {
                JSONObject object = new JSONObject((String) obj);
                parseJson(clazz, object);
            } else if (obj instanceof JSONObject) {
                o = HttpJsonReflectManager.parseJsonObject(clazz, (JSONObject) obj);
            } else if (obj instanceof JSONArray) {
                o = HttpJsonReflectManager.parseJsonArray(clazz, (JSONArray) obj);
            } else {
                EasyLog.e("obj is not JSONObject or JSONArray");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }

}
