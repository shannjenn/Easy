package com.jen.easy.http;

import com.jen.easy.EasyUtil;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jen.easy.http.HttpReflectManager.RSP_FIELD;
import static com.jen.easy.http.HttpReflectManager.RSP_HEAD;
import static com.jen.easy.http.HttpReflectManager.RSP_TYPE;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Json数据解析
 */
class HttpParseManager {
    private final static String TAG = "数据解析 : ";

    /*解析的class*/
    private Class mClass;
    private Map<String, List<String>> mHeadMap;
    //错误提示
    private List<String> mErrors = new ArrayList<>();
    /**
     * 通用数据返回
     * 设置返回Object变量实体：List集合实体、单实体
     * 如：
     * （@EasyMouse.mHttp.ResponseParam("data") 注释返回参数）
     * （@private Object data; 实体变量）
     */
    private Class responseObjectType;//由HttpBaseRequest传递过来

    void setResponseObjectType(Class responseObjectType) {
        this.responseObjectType = responseObjectType;
    }

    /**
     * json解析
     *
     * @param tClass
     * @param obj
     * @return
     */
    <T> T parseJson(Class<T> tClass, String obj, Map<String, List<String>> headMap) {
        EasyLibLog.d(TAG + "解析：" + tClass.getName() + "----开始");
        mClass = tClass;
        this.mHeadMap = headMap;
        T t = null;
        try {
            JSONObject object = new JSONObject(obj);
            t = parseJsonObject(tClass, object);
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "parseJson JSONException error");
        }
        mErrors.clear();
        EasyLibLog.d(TAG + "解析：" + tClass.getName() + "----结束");
        if (t != null && t instanceof HttpHeadResponse) {
            ((HttpHeadResponse) t).setHeads(headMap);
        }
        return t;
    }

    /**
     * 解析接口返回json格式值
     *
     * @param tClass     类
     * @param jsonObject
     * @return
     */
    private <T> T parseJsonObject(Class<T> tClass, JSONObject jsonObject) {
        if (tClass == null || jsonObject == null) {
            EasyLibLog.e(TAG + "tClass == null || jsonObject == null");
            return null;
        }
        T tObj;
        try {
            tObj = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "parseJsonObject InstantiationException 创建对象出错");
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "parseJsonObject IllegalAccessException 创建对象出错");
            return null;
        }

        Map<String, Object> objectMap = HttpReflectManager.getResponseParams(tObj.getClass());
        Map<String, String> param_type = (Map<String, String>) objectMap.get(RSP_TYPE);
        Map<String, Field> param_field = (Map<String, Field>) objectMap.get(RSP_FIELD);
        Map<String, Field> head_field = (Map<String, Field>) objectMap.get(RSP_HEAD);

        Set<String> heads = head_field.keySet();
        for (String param : heads) {//设置head值
            if (!mHeadMap.containsKey(param)) {
                continue;
            }
            Field field = head_field.get(param);
            field.setAccessible(true);

            StringBuffer buffer = new StringBuffer("");
            List<String> values = mHeadMap.get(param);
            int size = values.size();
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    buffer.append(values.get(i));
                } else {
                    buffer.append(";");
                    buffer.append(values.get(i));
                }
            }
            try {
                field.set(tObj, buffer.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                showError("IllegalAccessException：head param=" + param);
            }
        }

        if (param_type.size() == 0) {
            EasyLibLog.e(TAG + "parseJsonObject 网络请求返回参数请用@EasyMouse.mHttp.ResponseParam备注正确");
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

                    if (type.equals(Constant.FieldType.STRING)) {
                        if (value instanceof String) {
                            field.set(tObj, value);
                        } else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
                            field.set(tObj, value + "");
                        } else {
                            showWarn("param=" + param + "不是tring、Integer、Long、Float、Double类型，不可以转换为String类型");
                        }
                    } else if (type.equals(Constant.FieldType.INTEGER)) {
                        if (value instanceof Long) {
//                            value = ((Long) value).intValue();
                            showWarn("param=" + param + " is not Integer,is Long");
                        } else if (!(value instanceof Integer)) {
                            showWarn("param=" + param + " is not Integer");
                        } else {
                            field.setInt(tObj, (Integer) value);
                        }
                    } else if (type.equals(Constant.FieldType.LONG)) {
                        if (value instanceof Integer) {
//                            value = ((Integer) value).longValue();
                            showWarn("param=" + param + " is not Long,is Integer");
                        } else if (!(value instanceof Long)) {
                            showWarn("param=" + param + " is not Long");
                        } else {
                            field.setLong(tObj, (Long) value);
                        }
                    } else if (type.equals(Constant.FieldType.FLOAT)) {
                        if (value instanceof Double) {
//                            value = ((Double) value).floatValue();
                            showWarn("param=" + param + " is not Float,is Double");
                        } else if (!(value instanceof Float)) {
                            showWarn("param=" + param + " is not Float");
                        } else {
                            field.setFloat(tObj, (Float) value);
                        }
                    } else if (type.equals(Constant.FieldType.DOUBLE)) {
                        if (value instanceof Float) {
//                            value = ((Float) value).doubleValue();
                            showWarn("param=" + param + " is not Double,is Float");
                        } else if (!(value instanceof Double)) {
                            showWarn("param=" + param + " is not Double");
                        } else {
                            field.setDouble(tObj, (Double) value);
                        }
                    } else if (type.equals(Constant.FieldType.BOOLEAN)) {
                        if (!(value instanceof Boolean)) {
                            showWarn("param=" + param + " is not Boolean ");
                            continue;
                        }
                        field.setBoolean(tObj, (Boolean) value);
                    } else if (type.equals(Constant.FieldType.DATE)) {
                        if (value instanceof String) {
                            Date date = EasyUtil.mDateFormat.parser((String) value);
                            if (date != null) {
                                field.set(tObj, date);
                            }
                        }
                    } else if (type.contains(Constant.FieldType.MAP)) {//解析Map
                        EasyLibLog.e(TAG + "不支持Map类型");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {//解析数组
                        EasyLibLog.e(TAG + "不支持数组类型");
                    } else if (type.contains(Constant.FieldType.OBJECT)) {//解析Object通用类型
                        if (responseObjectType != null) {
                            if (value instanceof JSONArray) {
                                List objList = parseJsonArray(responseObjectType, (JSONArray) value);
                                field.set(tObj, objList);
                            } else if (value instanceof JSONObject) {
                                Object object = parseJsonObject(responseObjectType, (JSONObject) value);
                                field.set(tObj, object);
                            } else {
                                showError("Object类型错误 :" + type);
                            }
                        } else {
                            showError("Object类型解析：请指定转换的类");
                        }
                    } else if (type.contains(Constant.FieldType.LIST)) {//解析list
                        if (value instanceof JSONArray) {
                            String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                            Class clazz2 = Class.forName(clazzName);
                            if (clazz2 == mClass) {
                                showError("不能解析与类相同的变量,param=" + param + " type=" + clazzName);
                                continue;
                            }
                            List objList = parseJsonArray(clazz2, (JSONArray) value);
                            field.set(tObj, objList);
                        } else {
                            showError("List类型错误 :" + type);
                        }
                    } else if (type.contains(Constant.FieldType.CLASS)) {//解析指定class
                        if (value instanceof JSONObject) {
                            if (field.getType() == mClass) {
                                showError("不能解析与类相同的变量,param=" + param + " type=" + field.getType());
                                continue;
                            }
                            Object obj = parseJsonObject(field.getType(), (JSONObject) value);
                            field.set(tObj, obj);
                        } else {
                            showError("Class类型错误 :" + type);
                        }
                    } else {
                        showError("不支持该类型：" + type);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showError("JSONException：type=" + type + " param=" + param);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                showError("IllegalAccessException：type=" + type + " param=" + param);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showError("ClassNotFoundException：type=" + type + " param=" + param);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                showError("NumberFormatException：type=" + type + " param=" + param);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                showError("class类型错误 parseJsonObject IllegalArgumentException：type=" + type + " param=" + param);
            } catch (ClassCastException e) {
                e.printStackTrace();
                showError("ClassCastException：type=" + type + " param=" + param);
            }
        }
        return tObj;
    }

    /**
     * 解析jsonArray
     *
     * @param TClass
     * @param jsonArray
     * @return
     */
    private <T> List<T> parseJsonArray(Class<T> TClass, JSONArray jsonArray) {
        List<T> list = new ArrayList<>();
        if (jsonArray == null) {
            EasyLibLog.e(TAG + "parseJsonArray clazz or jsonArray is null");
            return list;
        }
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            Object jsonObj;
            try {
                jsonObj = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLibLog.e(TAG + "parseJsonArray JSONException jsonObj");
                continue;
            }
            if (jsonObj instanceof JSONObject) {
                T tObj = parseJsonObject(TClass, (JSONObject) jsonObj);
                if (tObj != null)
                    list.add(tObj);
            } else {
                EasyLibLog.e(TAG + "parseJsonArray jsonArray.get(i) is not JSONObject");
            }
        }
        return list;
    }

    /**
     * 警告Log
     *
     * @param error
     */
    private void showWarn(String error) {
        if (!mErrors.contains(error)) {
            EasyLibLog.w(TAG + error);
            mErrors.add(error);
        }
    }

    /**
     * 错误Log
     *
     * @param error
     */
    private void showError(String error) {
        if (!mErrors.contains(error)) {
            EasyLibLog.e(TAG + error);
            mErrors.add(error);
        }
    }

}
