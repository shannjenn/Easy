package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Json数据解析
 */
class HttpParseManager {

    /*解析的class*/
    private Class mTopClass;
    private Map<String, List<String>> mHeadMap;
    //错误提示
    private List<String> mErrors = new ArrayList<>();

    /**
     * json解析
     *
     * @param tClass 类
     * @param obj    数据
     * @return 值
     */
    <T> T parseJson(Class<T> tClass, String obj, Map<String, List<String>> headMap) {
        EasyLog.d(TAG.EasyHttp, "解析：" + tClass.getName() + "----开始");
        mTopClass = tClass;
        this.mHeadMap = headMap;
        T t;
        JSONObject object = null;
        try {
            object = new JSONObject(obj);
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyHttp, "parseJson方法解析错误JSONException");
        }
        t = parseJsonObject(tClass, object);
        mErrors.clear();
        EasyLog.d(TAG.EasyHttp, "解析：" + tClass.getName() + "----完成");
        if (t != null && t instanceof HttpHeadResponse) {
            ((HttpHeadResponse) t).setHeads(headMap);
        }
        return t;
    }

    /**
     * 解析接口返回json格式值
     *
     * @param tClass     类
     * @param jsonObject 数据
     * @return 值
     */
    private <T> T parseJsonObject(Class<T> tClass, JSONObject jsonObject) {
        if (tClass == null || jsonObject == null) {
            EasyLog.w(TAG.EasyHttp, "该Class为空或者JSONObject为空");
            return null;
        }
        T tObj;
        try {
            tObj = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyHttp, "InstantiationException 创建对象出错");
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyHttp, "IllegalAccessException 创建对象出错");
            return null;
        }

        Map<String, String> param_type = new HashMap<>();
        Map<String, Field> param_field = new HashMap<>();
        Map<String, Field> head_field = new HashMap<>();
        HttpReflectManager.getResponseParams(tObj.getClass(), param_type, param_field, head_field);

        Set<String> heads = head_field.keySet();
        for (String param : heads) {//设置head值
            if (!mHeadMap.containsKey(param)) {
                continue;
            }
            Field field = head_field.get(param);
            field.setAccessible(true);

            StringBuilder buffer = new StringBuilder("");
            List<String> values = mHeadMap.get(param);
            for (int i = 0; i < values.size(); i++) {
                if (i == 0) {
                    buffer.append(values.get(i));
                } else {
                    buffer.append("&");
                    buffer.append(values.get(i));
                }
            }
            try {
                field.set(tObj, buffer.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                showWarn("IllegalAccessException：header 参数：" + param);
            }
        }

        if (param_type.size() == 0) {
            EasyLog.w(TAG.EasyHttp, "网络请求返回参数请用@Easy.mHttp.ResponseParam备注正确");
            return null;
        }
        Set<String> sets = param_type.keySet();
        for (String param : sets) {
            String type = null;
            try {
                if (jsonObject.has(param)) {
                    Object value = jsonObject.get(param);
                    if (value == null || value.toString().trim().length() == 0
                            || (!(value instanceof String) && value.toString().equals("null"))) {
                        continue;
                    }
                    Field field = param_field.get(param);
                    field.setAccessible(true);
                    type = param_type.get(param);

                    if (FieldType.isString(type)) {
                        if (value instanceof String) {
                            field.set(tObj, value);
                        } else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
                            field.set(tObj, value + "");
                        } else {
                            showWarn("param=" + param + "不是String、Integer、Long、Float、Double类型，不可以转换为String类型");
                        }
                    } else if (FieldType.isInt(type)) {
                        if (value instanceof Long) {
//                            value = ((Long) value).intValue();
                            showWarn("param=" + param + "不是Integer类型，是Long类型");
                        } else if (!(value instanceof Integer)) {
                            showWarn("param=" + param + "不是Integer类型");
                        } else {
                            field.setInt(tObj, (Integer) value);
                        }
                    } else if (FieldType.isLong(type)) {
                        if (value instanceof Integer) {
//                            value = ((Integer) value).longValue();
                            showWarn("param=" + param + "不是Long类型，是Integer类型");
                        } else if (!(value instanceof Long)) {
                            showWarn("param=" + param + "不是Long类型");
                        } else {
                            field.setLong(tObj, (Long) value);
                        }
                    } else if (FieldType.isFloat(type)) {
                        if (value instanceof Double) {
//                            value = ((Double) value).floatValue();
                            showWarn("param=" + param + "不是Float类型，是Double类型");
                        } else if (!(value instanceof Float)) {
                            showWarn("param=" + param + "不是Float类型");
                        } else {
                            field.setFloat(tObj, (Float) value);
                        }
                    } else if (FieldType.isDouble(type)) {
                        if (value instanceof Float) {
//                            value = ((Float) value).doubleValue();
                            showWarn("param=" + param + "不是Double类型，是Float类型");
                        } else if (!(value instanceof Double)) {
                            showWarn("param=" + param + "不是Double类型");
                        } else {
                            field.setDouble(tObj, (Double) value);
                        }
                    } else if (FieldType.isBoolean(type)) {
                        if (!(value instanceof Boolean)) {
                            showWarn("参数：" + param + "不是Boolean类型");
                        } else {
                            field.setBoolean(tObj, (Boolean) value);
                        }
                    } else if (FieldType.isObject(type)) {//解析Object通用类型
                        field.set(tObj, value);
                    } else if (FieldType.isList(type)) {//解析list
                        if (value instanceof JSONArray) {
                            String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                            Class clazz2 = Class.forName(clazzName);
                            if (clazz2 == mTopClass) {
                                showWarn("不能解析与类相同的变量避免死循环,参数：" + param + " 类型：" + type);
                            } else {
                                List objList = parseJsonArray(clazz2, (JSONArray) value);
                                field.set(tObj, objList);
                            }
                        } else {
                            showWarn("List类型错误 :" + type);
                        }
                    } else if (FieldType.isClass(type)) {//解析指定class
                        if (field.getType() == mTopClass) {
                            showWarn("不能解析与类相同的变量避免死循环,param=" + param + " 类型：" + type);
                        } else if (value instanceof JSONObject) {
                            Object obj = parseJsonObject(field.getType(), (JSONObject) value);
                            field.set(tObj, obj);
                        } else {
                            showWarn("Class类型错误 :" + type);
                        }
                    } else {
                        showWarn("不支持该类型：" + type);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showWarn("JSONException param=" + param);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                showWarn("IllegalAccessException：类型：" + type + " 参数：" + param);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showWarn("ClassNotFoundException：类型：" + type + " 参数：" + param);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                showWarn("NumberFormatException：类型：" + type + " 参数：" + param);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                showWarn("class类型错误 parseJsonObject IllegalArgumentException：类型：" + type + " 参数：" + param);
            } catch (ClassCastException e) {
                e.printStackTrace();
                showWarn("ClassCastException：类型：" + type + " 参数：" + param);
            }
        }
        return tObj;
    }

    /**
     * 解析jsonArray
     *
     * @param TClass    类
     * @param jsonArray JSONArray
     * @return 值
     */
    private <T> List<T> parseJsonArray(Class<T> TClass, JSONArray jsonArray) {
        List<T> list = new ArrayList<>();
        if (jsonArray == null) {
            EasyLog.w(TAG.EasyHttp, "parseJsonArray JSONArray数据为空");
            return list;
        }
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            Object jsonObj;
            try {
                jsonObj = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLog.w(TAG.EasyHttp, "parseJsonArray JSONException");
                continue;
            }
            if (jsonObj instanceof JSONObject) {
                T tObj = parseJsonObject(TClass, (JSONObject) jsonObj);
                if (tObj != null)
                    list.add(tObj);
            } else {

                EasyLog.w(TAG.EasyHttp, "parseJsonArray 该数据不属于JSONObject类型");
            }
        }
        return list;
    }

    /**
     * 警告Log
     *
     * @param error 错误信息
     */
    private void showWarn(String error) {
        if (!mErrors.contains(error)) {
            EasyLog.w(TAG.EasyHttp, error);
            mErrors.add(error);
        }
    }

}
