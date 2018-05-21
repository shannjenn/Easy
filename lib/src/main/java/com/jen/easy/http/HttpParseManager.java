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
//    private Class mTopClass;
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
//        mTopClass = tClass;
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
                        String res = parseString(value, param);
                        field.set(tObj, res);
                    } else if (FieldType.isInt(type)) {
                        int res = parseInt(value, param);
                        field.setInt(tObj, res);
                    } else if (FieldType.isLong(type)) {
                        long res = parseLong(value, param);
                        field.setLong(tObj, res);
                    } else if (FieldType.isFloat(type)) {
                        float res = parseFloat(value, param);
                        field.setFloat(tObj, res);
                    } else if (FieldType.isDouble(type)) {
                        double res = parseDouble(value, param);
                        field.setDouble(tObj, res);
                    } else if (FieldType.isShort(type)) {
                        short res = parseShort(value, param);
                        field.setShort(tObj, res);
                    } else if (FieldType.isBoolean(type)) {
                        boolean res = parseBoolean(value, param);
                        field.setBoolean(tObj, res);
                    } else if (FieldType.isChar(type)) {
                        char res = parseCharacter(value, param);
                        field.setChar(tObj, res);
                    } else if (FieldType.isByte(type)) {
                        byte res = parseByte(value, param);
                        field.setByte(tObj, res);
                    } else if (FieldType.isObject(type)) {//解析Object通用类型
                        field.set(tObj, value);
                    } else if (FieldType.isList(type)) {//解析list
                        if (value instanceof JSONArray) {
                            String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                            Class clazz2 = Class.forName(clazzName);
                            /*if (clazz2 == mTopClass) {
                                showWarn("不能解析与类相同的变量避免死循环,参数：" + param + " 类型：" + type);
                            } else {*/
                            List objList = parseJsonArray(clazz2, (JSONArray) value);
                            field.set(tObj, objList);
//                            }
                        } else {
                            showWarn("List类型错误 :" + type);
                        }
                    } else if (FieldType.isClass(type)) {//解析指定class
                        /*if (field.getType() == mTopClass) {
                            showWarn("不能解析与类相同的变量避免死循环,param=" + param + " 类型：" + type);
                        } else */
                        if (value instanceof JSONObject) {
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
     * 转String类型
     *
     * @param obj 值
     * @return 返回
     */
    private String parseString(Object obj, String param) {
        String res = null;
        if (obj instanceof String) {
            res = (String) obj;
        } else if (obj instanceof Integer) {
            res = String.valueOf(obj);
        } else if (obj instanceof Boolean) {
            res = String.valueOf(obj);
        } else if (obj instanceof Float) {
            res = String.valueOf(obj);
        } else if (obj instanceof Long) {
            res = String.valueOf(obj);
        } else if (obj instanceof Double) {
            res = String.valueOf(obj);
        } else if (obj instanceof Short) {
            res = String.valueOf(obj);
        } else if (obj instanceof Character) {
            res = String.valueOf(obj);
        } else if (obj instanceof Byte) {
            res = String.valueOf(obj);
        } else {
            showWarn("param=" + param + "该值不支持转换为 String 类型");
        }
        return res;
    }

    /**
     * 转Int类型
     *
     * @param obj 值
     * @return 返回
     */
    private int parseInt(Object obj, String param) {
        int res = 0;
        try {
            if (obj instanceof Integer) {
                res = (int) obj;
            } else if (obj instanceof String) {
                res = Integer.valueOf((String) obj);
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Integer 类型");
            } else if (obj instanceof Float) {
                res = ((Float) obj).intValue();
            } else if (obj instanceof Long) {
                res = ((Long) obj).intValue();
            } else if (obj instanceof Double) {
                res = ((Double) obj).intValue();
            } else if (obj instanceof Short) {
                res = ((Short) obj).intValue();
            } else if (obj instanceof Character) {
                res = Integer.valueOf(obj.toString());
            } else if (obj instanceof Byte) {
                res = ((Byte) obj).intValue();
            } else {
                showWarn("param=" + param + "该值不支持转换为 Integer 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转Boolean类型
     *
     * @param obj 值
     * @return 返回
     */
    private boolean parseBoolean(Object obj, String param) {
        boolean res = false;
        if (obj instanceof Boolean) {
            res = (boolean) obj;
        } else if (obj instanceof String) {
            if ("true".equals(obj)) {
                res = true;
            } else if ("false".equals(obj)) {
                res = false;
            } else {
                showWarn("param=" + param + "该 String 值不支持转换为 Boolean 类型");
            }
        } else if (obj instanceof Integer) {
            showWarn("param=" + param + "Integer 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Float) {
            showWarn("param=" + param + "Float 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Long) {
            showWarn("param=" + param + "Long 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Double) {
            showWarn("param=" + param + "Double 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Short) {
            showWarn("param=" + param + "Short 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Character) {
            showWarn("param=" + param + "Character 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Byte) {
            showWarn("param=" + param + "Byte 值不支持转换为 Boolean 类型");
        } else {
            showWarn("param=" + param + "该值不支持转换为 Boolean 类型");
        }
        return res;
    }

    /**
     * 转Float类型
     *
     * @param obj 值
     * @return 返回
     */
    private float parseFloat(Object obj, String param) {
        float res = 0.0f;
        try {
            if (obj instanceof Float) {
                res = (float) obj;
            } else if (obj instanceof String) {
                res = Float.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                res = (float) obj;
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Float 类型");
            } else if (obj instanceof Long) {
                res = (float) obj;
            } else if (obj instanceof Double) {
                res = (float) obj;
            } else if (obj instanceof Short) {
                res = (float) obj;
            } else if (obj instanceof Character) {
                res = Float.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                res = (float) obj;
            } else {
                showWarn("param=" + param + "该值不支持转换为 Float 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转Long类型
     *
     * @param obj 值
     * @return 返回
     */
    private long parseLong(Object obj, String param) {
        long res = 0;
        try {
            if (obj instanceof Long) {
                res = (long) obj;
            } else if (obj instanceof String) {
                res = Long.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                res = (long) obj;
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Long 类型");
            } else if (obj instanceof Float) {
                res = (long) obj;
            } else if (obj instanceof Double) {
                res = (long) obj;
            } else if (obj instanceof Short) {
                res = (long) obj;
            } else if (obj instanceof Character) {
                res = Long.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                res = (long) obj;
            } else {
                showWarn("param=" + param + "该值不支持转换为 Long 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转Double类型
     *
     * @param obj 值
     * @return 返回
     */
    private double parseDouble(Object obj, String param) {
        double res = 0;
        try {
            if (obj instanceof Double) {
                res = (double) obj;
            } else if (obj instanceof String) {
                res = Double.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                res = (double) obj;
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Double 类型");
            } else if (obj instanceof Float) {
                res = (double) obj;
            } else if (obj instanceof Long) {
                res = (double) obj;
            } else if (obj instanceof Short) {
                res = (double) obj;
            } else if (obj instanceof Character) {
                res = Double.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                res = (double) obj;
            } else {
                showWarn("param=" + param + "该值不支持转换为 Double 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转Short类型
     *
     * @param obj 值
     * @return 返回
     */
    private short parseShort(Object obj, String param) {
        short res = 0;
        try {
            if (obj instanceof Short) {
                res = (short) obj;
            } else if (obj instanceof String) {
                res = Short.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                res = (short) obj;
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Short 类型");
            } else if (obj instanceof Float) {
                res = (short) obj;
            } else if (obj instanceof Long) {
                res = (short) obj;
            } else if (obj instanceof Double) {
                res = (short) obj;
            } else if (obj instanceof Character) {
                res = Short.valueOf(String.valueOf(obj));
            } else if (obj instanceof Byte) {
                res = (short) obj;
            } else {
                showWarn("param=" + param + "该值不支持转换为 Short 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转Character类型
     *
     * @param obj 值
     * @return 返回
     */
    private char parseCharacter(Object obj, String param) {
        char res = 0;
        if (obj instanceof Character) {
            res = (char) obj;
        } else if (obj instanceof String) {
            if (((String) obj).length() == 1) {
                res = ((String) obj).charAt(0);
            } else {
                showWarn("param=" + param + "String 值不支持转换为 Character 类型");
            }
        } else if (obj instanceof Integer) {
            showWarn("param=" + param + "Integer 值不支持转换为 Character 类型");
        } else if (obj instanceof Boolean) {
            showWarn("param=" + param + "Boolean 值不支持转换为 Character 类型");
        } else if (obj instanceof Float) {
            showWarn("param=" + param + "Float 值不支持转换为 Character 类型");
        } else if (obj instanceof Long) {
            showWarn("param=" + param + "Long 值不支持转换为 Character 类型");
        } else if (obj instanceof Double) {
            showWarn("param=" + param + "Double 值不支持转换为 Character 类型");
        } else if (obj instanceof Short) {
            showWarn("param=" + param + "Short 值不支持转换为 Character 类型");
        } else if (obj instanceof Byte) {
            res = (char) obj;
        } else {
            showWarn("param=" + param + "该值不支持转换为 Character 类型");
        }
        return res;
    }

    /**
     * 转Byte类型
     *
     * @param obj 值
     * @return 返回
     */
    private byte parseByte(Object obj, String param) {
        byte res = 0;
        if (obj instanceof Byte) {
            res = (byte) obj;
        } else if (obj instanceof String) {
            showWarn("param=" + param + "String 值不支持转换为 Byte 类型");
        } else if (obj instanceof Integer) {
            showWarn("param=" + param + "Integer 值不支持转换为 Byte 类型");
        } else if (obj instanceof Boolean) {
            showWarn("param=" + param + "Boolean 值不支持转换为 Byte 类型");
        } else if (obj instanceof Float) {
            showWarn("param=" + param + "Float 值不支持转换为 Byte 类型");
        } else if (obj instanceof Long) {
            showWarn("param=" + param + "Long 值不支持转换为 Byte 类型");
        } else if (obj instanceof Double) {
            showWarn("param=" + param + "Double 值不支持转换为 Byte 类型");
        } else if (obj instanceof Short) {
            showWarn("param=" + param + "Short 值不支持转换为 Byte 类型");
        } else if (obj instanceof Character) {
            showWarn("param=" + param + "Character 值不支持转换为 Byte 类型");
        } else {
            showWarn("param=" + param + "该值不支持转换为 Byte 类型");
        }
        return res;
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
