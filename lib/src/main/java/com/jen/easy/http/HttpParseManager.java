package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;
import com.jen.easy.log.EasyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Json数据解析
 */
class HttpParseManager {
    /*返回头部信息*/
    private Map<String, List<String>> mHeadMap;
    /*错误提示*/
    private List<String> mErrors = new ArrayList<>();

    /**
     * json解析
     *
     * @param tClass 类
     * @param obj    数据
     * @return 值
     */
    <T> T parseResponseFromJSONString(Class<T> tClass, String obj, Map<String, List<String>> headMap) {
        EasyLog.d(TAG.EasyHttp, "解析：" + tClass.getName() + "----开始");
        this.mHeadMap = headMap;
        T t;
        JSONObject object;
        try {
            object = new JSONObject(obj);
        } catch (JSONException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyHttp, "parseJson方法解析错误JSONException");
            return null;
        }
        t = parseResponseFromJSONObject(tClass, object);
        if (t instanceof HttpHeadResponse) {
            ((HttpHeadResponse) t).setHeads(headMap);
        }
        mErrors.clear();
        EasyLog.d(TAG.EasyHttp, "解析：" + tClass.getName() + "----完成");
        return t;
    }

    /**
     * 解析接口返回json格式值
     *
     * @param tClass     类
     * @param jsonObject 数据
     * @return 值
     */
    private <T> T parseResponseFromJSONObject(Class<T> tClass, JSONObject jsonObject) {
        /*if (tClass == null || jsonObject == null) {
            throwError(ExceptionType.NullPointerException, "参数不能为空");
            return null;
        }*/
        T tObj;
        try {
            tObj = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyHttp, "InstantiationException 创建对象出错：" + tClass.getName());
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyHttp, "IllegalAccessException 创建对象出错" + tClass.getName());
            return null;
        }

        HttpReflectManager.ResponseObject responseObject = HttpReflectManager.getResponseHeadAndBody(tObj.getClass());
        Map<String, Field> body = responseObject.body;
        Map<String, Field> heads = responseObject.heads;

        Set<String> headKeys = heads.keySet();
        for (String headKey : headKeys) {//设置head值
            if (!mHeadMap.containsKey(headKey)) {
                continue;
            }
            Field field = heads.get(headKey);
            field.setAccessible(true);

            StringBuilder buffer = new StringBuilder();
            List<String> values = mHeadMap.get(headKey);
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
                showWarn("IllegalAccessException：header 参数：" + headKey);
            }
        }

        if (body.size() == 0) {
            return null;
        }
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String param = keys.next();
            if (!body.containsKey(param)) {
                continue;
            }
            Field field = body.get(param);
            field.setAccessible(true);
            Class fieldClass = field.getType();

            try {
                Object object = jsonObject.get(param);
                if (object == null || object.toString().trim().length() == 0
                        || (!(object instanceof String) && object.toString().equals("null"))) {
                    continue;
                }
                if (FieldType.isString(fieldClass)) {
                    String res = parseString(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isInt(fieldClass)) {
                    int res = parseInt(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isLong(fieldClass)) {
                    long res = parseLong(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isFloat(fieldClass)) {
                    float res = parseFloat(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isDouble(fieldClass)) {
                    double res = parseDouble(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isShort(fieldClass)) {
                    short res = parseShort(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isBoolean(fieldClass)) {
                    boolean res = parseBoolean(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isChar(fieldClass)) {
                    char res = parseCharacter(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isByte(fieldClass)) {
                    byte res = parseByte(object, param);
                    field.set(tObj, res);
                } else if (FieldType.isObject(fieldClass)) {//解析Object通用类型
                    field.set(tObj, object);
                } else if (FieldType.isList(fieldClass)) {//解析list
                    if (object instanceof JSONArray) {
                        String type = field.getGenericType().toString();
                        String clazzName = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
                        Class clazz2 = Class.forName(clazzName);
                        List objList = parseResponseFromJSONArray(clazz2, (JSONArray) object);
                        field.set(tObj, objList);
                    } else {
                        throwError(ExceptionType.ClassCastException, "数据类型错误" + fieldClass + ",该数据为List类型");
                    }
                } else if (FieldType.isClass(fieldClass)) {//解析指定class
                    if (object instanceof JSONObject) {
                        Object obj = parseResponseFromJSONObject(field.getType(), (JSONObject) object);
                        field.set(tObj, obj);
                    } else {
                        throwError(ExceptionType.ClassCastException, "数据类型错误" + fieldClass + ",该数据为Class类型");
                    }
                } else {
                    throwError(ExceptionType.RuntimeException, "不支持该数据类型解析" + fieldClass);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showWarn("JSONException：类型：" + fieldClass + " 参数：" + param);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                showWarn("IllegalAccessException：类型：" + fieldClass + " 参数：" + param);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                showWarn("ClassNotFoundException：类型：" + fieldClass + " 参数：" + param);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                showWarn("IllegalArgumentException：类型：" + fieldClass + " 参数：" + param);
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
    private <T> List<T> parseResponseFromJSONArray(Class<T> TClass, JSONArray jsonArray) {
        List<T> list = new ArrayList<>();
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            Object jsonObj;
            try {
                jsonObj = jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
                EasyLog.w(TAG.EasyHttp, "parseResponseFromJSONArray JSONException");
                continue;
            }
            if (jsonObj instanceof JSONObject) {
                T tObj = parseResponseFromJSONObject(TClass, (JSONObject) jsonObj);
                if (tObj != null)
                    list.add(tObj);
            } else {
                throwError(ExceptionType.ClassCastException, "parseResponseFromJSONArray 该数据不属于JSONObject类型");
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
            throwError(ExceptionType.ClassCastException, "param=" + param + "该数据类型值不支持转换为 String 类型");
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
                throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Integer 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.intValue();
                showWarn("param=" + param + "Float 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.intValue();
                showWarn("param=" + param + "Long 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.intValue();
                showWarn("param=" + param + "Double 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.intValue();
            } else if (obj instanceof Character) {
                res = Integer.valueOf(obj.toString());
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.intValue();
            } else {
                throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Integer 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
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
            throwError(ExceptionType.ClassCastException, "param=" + param + "该 String 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Integer) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Integer 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Float) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Float 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Long) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Long 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Double) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Double 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Short) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Short 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Character) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Character 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Byte) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Byte 值不支持转换为 Boolean 类型");
        } else {
            throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Boolean 类型");
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
                Integer value = (Integer) obj;
                res = value.floatValue();
                showWarn("param=" + param + "Integer 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Float 类型");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.floatValue();
                showWarn("param=" + param + "Long 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.floatValue();
                showWarn("param=" + param + "Double 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.floatValue();
            } else if (obj instanceof Character) {
                res = Float.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.floatValue();
            } else {
                throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Float 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
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
                Integer value = (Integer) obj;
                res = value.longValue();
            } else if (obj instanceof Boolean) {
                throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Long 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.longValue();
                showWarn("param=" + param + "Float 转换为 Long 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.longValue();
                showWarn("param=" + param + "Double 转换为 Long 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.longValue();
            } else if (obj instanceof Character) {
                res = Long.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.longValue();
            } else {
                throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Long 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
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
                Integer value = (Integer) obj;
                res = value.doubleValue();
                showWarn("param=" + param + "Integer 转换为 Double 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Double 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.doubleValue();
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.doubleValue();
                showWarn("param=" + param + "Long 转换为 Double 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.doubleValue();
            } else if (obj instanceof Character) {
                res = Double.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.doubleValue();
            } else {
                throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Double 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
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
                Integer value = (Integer) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Integer 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Short 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Float 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Long 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Double 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Character) {
                res = Short.valueOf(String.valueOf(obj));
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.shortValue();
            } else {
                throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Short 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
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
            throwError(ExceptionType.ClassCastException, "param=" + param + "String 值不支持转换为 Character 类型");
        } else if (obj instanceof Integer) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Integer 值不支持转换为 Character 类型");
        } else if (obj instanceof Boolean) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Character 类型");
        } else if (obj instanceof Float) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Float 值不支持转换为 Character 类型");
        } else if (obj instanceof Long) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Long 值不支持转换为 Character 类型");
        } else if (obj instanceof Double) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Double 值不支持转换为 Character 类型");
        } else if (obj instanceof Short) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Short 值不支持转换为 Character 类型");
        } else if (obj instanceof Byte) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Byte 值不支持转换为 Character 类型");
        } else {
            throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Character 类型");
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
            throwError(ExceptionType.ClassCastException, "param=" + param + "String 值不支持转换为 Byte 类型");
        } else if (obj instanceof Integer) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Integer 值不支持转换为 Byte 类型");
        } else if (obj instanceof Boolean) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Boolean 值不支持转换为 Byte 类型");
        } else if (obj instanceof Float) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Float 值不支持转换为 Byte 类型");
        } else if (obj instanceof Long) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Long 值不支持转换为 Byte 类型");
        } else if (obj instanceof Double) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Double 值不支持转换为 Byte 类型");
        } else if (obj instanceof Short) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Short 值不支持转换为 Byte 类型");
        } else if (obj instanceof Character) {
            throwError(ExceptionType.ClassCastException, "param=" + param + "Character 值不支持转换为 Byte 类型");
        } else {
            throwError(ExceptionType.ClassCastException, "param=" + param + "该类型值不支持转换为 Byte 类型");
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

    /**
     * 抛出异常
     *
     * @param exception 异常类型
     * @param error     错误信息
     */
    private void throwError(@ExceptionType int exception, String error) {
        if (!mErrors.contains(error)) {
            Throw.exception(exception, error);
            mErrors.add(error);
        }
    }

}
