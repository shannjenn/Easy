package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        HttpLog.d("解析：" + tClass.getName() + "----开始");
        this.mHeadMap = headMap;
        T t;
        JSONObject object;
        try {
            object = new JSONObject(obj);
        } catch (JSONException e) {
            showErrorLog("JSONException 解析错误，不属于JSONObject数据");
            return null;
        }
        t = parseJSONObject(tClass, object);
        if (t instanceof HttpHeadResponse) {
            ((HttpHeadResponse) t).setHeads(headMap);
        }
        mErrors.clear();
        HttpLog.d("解析：" + tClass.getName() + "----完成");
        return t;
    }

    /**
     * 解析实体
     *
     * @param tClass     类
     * @param jsonObject 数据
     * @return 值
     */
    private <T> T parseJSONObject(Class<T> tClass, JSONObject jsonObject) {
        /*if (tClass == null || jsonObject == null) {
            throwException(ExceptionType.NullPointerException, "参数不能为空");
            return null;
        }*/
        T tObj;
        try {
            tObj = tClass.newInstance();
        } catch (InstantiationException e) {
            showErrorLog("InstantiationException 创建对象出错：" + tClass.getName());
            return null;
        } catch (IllegalAccessException e) {
            showErrorLog("IllegalAccessException 创建对象出错" + tClass.getName());
            return null;
        }

        HttpReflectResponseManager.ResponseObject responseObject = HttpReflectResponseManager.getResponseHeadAndBody(tObj.getClass());
        Map<String, List<Field>> body = responseObject.body;
        Map<String, Field> heads = responseObject.heads;

        Set<String> headKeys = heads.keySet();
        for (String headKey : headKeys) {//设置head值
            if (!mHeadMap.containsKey(headKey)) {
                continue;
            }
            String value = parseHeader(headKey);//解析头部
            Field field = heads.get(headKey);
            field.setAccessible(true);
            try {
                field.set(tObj, value);
            } catch (IllegalAccessException e) {
                showErrorLog("IllegalAccessException：header 参数：" + headKey);
            }
        }

        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String param = keys.next();
            if (!body.containsKey(param)) {
                continue;
            }
            List<Field> fields = body.get(param);
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                field.setAccessible(true);
                Class fieldClass = field.getType();
                try {
                    Object object = jsonObject.get(param);
                    if (object == null || object.toString().trim().length() == 0
                            || (!(object instanceof String) && object.toString().equals("null"))) {
                        continue;
                    }
                    Type type = field.getGenericType();
                    Object value = parseField(object, fieldClass, type);//解析返回数据实体
                    if (value == null) {
                        continue;
                    }
                    field.set(tObj, value);
                } catch (JSONException e) {
                    showErrorLog("JSONException：类型：" + fieldClass + " 参数：" + param);
                } catch (IllegalAccessException e) {
                    showErrorLog("IllegalAccessException：类型：" + fieldClass + " 参数：" + param);
                } catch (IllegalArgumentException e) {
                    showErrorLog("IllegalArgumentException：类型：" + fieldClass + " 参数：" + param);
                }
            }
        }
        return tObj;
    }

    /**
     * 解析头部
     *
     * @param headKey key
     */
    private String parseHeader(String headKey) {
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
        return buffer.toString();
    }

    /**
     * 解析返回数据变量
     *
     * @param object     数据
     * @param fieldClass 变量
     * @param type       可以为null，为空时不做List解析
     * @return 返回值
     */
    private Object parseField(Object object, Class fieldClass, Type type) {
//        Class fieldClass = field.getType();
        if (FieldType.isString(fieldClass)) {
            return parseString(object);
        } else if (FieldType.isInt(fieldClass)) {
            return parseInt(object);
        } else if (FieldType.isLong(fieldClass)) {
            return parseLong(object);
        } else if (FieldType.isFloat(fieldClass)) {
            return parseFloat(object);
        } else if (FieldType.isDouble(fieldClass)) {
            return parseDouble(object);
        } else if (FieldType.isShort(fieldClass)) {
            return parseShort(object);
        } else if (FieldType.isBoolean(fieldClass)) {
            return parseBoolean(object);
        } else if (FieldType.isChar(fieldClass)) {
            return parseCharacter(object);
        } else if (FieldType.isByte(fieldClass)) {
            return parseByte(object);
        } else if (FieldType.isObject(fieldClass)) {//解析Object通用类型
            return object;
        } else if (FieldType.isJSONObject(fieldClass)) {//解析JsonObject
            if (object instanceof JSONObject) {
                return object;
            } else {
                throwException(ExceptionType.ClassCastException, "数据类型错误" + fieldClass + ",该数据不是JSONObject类型");
            }
        } else if (FieldType.isJSONArray(fieldClass)) {//解析JSONArray
            if (object instanceof JSONArray) {
                return object;
            } else {
                throwException(ExceptionType.ClassCastException, "数据类型错误" + fieldClass + ",该数据不是JSONArray类型");
            }
        } else if (FieldType.isList(fieldClass)) {//解析list
            if (object instanceof JSONArray) {
                return parseJSONArray(type, (JSONArray) object);
            } else {
                throwException(ExceptionType.ClassCastException, "数据类型错误" + fieldClass + ",该数据为List类型");
            }
        } else if (FieldType.isEntityClass(fieldClass)) {//解析指定class
            if (object instanceof JSONObject) {
                return parseJSONObject(fieldClass, (JSONObject) object);
            } else {
                throwException(ExceptionType.ClassCastException, "数据类型错误" + fieldClass + ",该数据为Class类型");
            }
        } else {
            throwException(ExceptionType.RuntimeException, "不支持该数据类型解析" + fieldClass);
        }
        return null;
    }

    /**
     * 解析jsonArray
     *
     * @param jsonArray 值
     * @return 返回
     */
    private List<Object> parseJSONArray(Type type, JSONArray jsonArray) {
        if (type == null) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            Object jsonObj;
            try {
                jsonObj = jsonArray.get(i);
            } catch (JSONException e) {
                showErrorLog("JSONArray数据解析错误 JSONException");
                continue;
            }
            Type type1 = null;
            if (type instanceof ParameterizedType) {
                type1 = ((ParameterizedType) type).getActualTypeArguments()[0];
            }
            if (type1 == null) {
                continue;
            }
            if (jsonObj instanceof JSONObject) {//实体解析
                try {
                    Class clazz = Class.forName(type1.toString().replace("class ", ""));
                    if (FieldType.isObject(clazz) //Object类型
                            || FieldType.isJSONObject(clazz)//JSONObject类型
                            || FieldType.isJSONArray(clazz)) {//JSONArray类型
                        Object value = parseField(jsonObj, clazz, null);
                        if (value != null) {
                            list.add(value);
                        } else {
                            throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误2：" + type.toString());
                        }
                    } else if (FieldType.isEntityClass(clazz)) {
                        Object value = parseJSONObject(clazz, (JSONObject) jsonObj);
                        if (value != null) {
                            list.add(value);
                        } else {
                            throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误1：" + type.toString());
                        }
                    } else {
                        throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误3：" + type.toString());
                    }
                } catch (ClassNotFoundException e) {
                    showErrorLog("ClassNotFoundException：JSONArray数据解析错误，集合：" + type.toString() + " 集合对象：" + type1.toString());
                }
            } else if (jsonObj instanceof JSONArray) {
                if (FieldType.isList(type1)) {
                    List<Object> childList = parseJSONArray(type1, (JSONArray) jsonObj);
                    if (childList != null) {
                        list.add(childList);
                    } else {
                        throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误4：" + type.toString());
                    }
                } else {
                    throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误5：" + type.toString());
                }
            } else {//数据为基本类型/Object
                try {
                    Class clazz = Class.forName(type1.toString().replace("class ", ""));
                    Object value = parseField(jsonObj, clazz, null);
                    if (value != null) {
                        list.add(value);
                    } else {
                        throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误6：" + type.toString());
                    }
                } catch (ClassNotFoundException e) {
                    showErrorLog("ClassNotFoundException：JSONArray数据解析错误，集合：" + type.toString() + " 集合对象：" + type1.toString());
                }
            }
        }
        if (list.size() == 0) {
            list = null;
        }
        return list;
    }

    /**
     * 转String类型
     *
     * @param obj 值
     * @return 返回
     */
    private String parseString(Object obj) {
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
            throwException(ExceptionType.ClassCastException, obj + " 不支持 String 类型转换");
        }
        return res;
    }

    /**
     * 转Int类型
     *
     * @param obj 值
     * @return 返回
     */
    private int parseInt(Object obj) {
        int res = 0;
        try {
            if (obj instanceof Integer) {
                res = (int) obj;
            } else if (obj instanceof String) {
                res = Integer.valueOf((String) obj);
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 int 类型转换");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.intValue();
                showErrorLog("Float 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.intValue();
                showErrorLog("Long 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.intValue();
                showErrorLog("Double 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.intValue();
            } else if (obj instanceof Character) {
                res = Integer.valueOf(obj.toString());
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.intValue();
            } else {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 int 类型转换");
            }
        } catch (NumberFormatException e) {
            showErrorLog("NumberFormatException ：" + obj.getClass().getName());
        }
        return res;
    }

    /**
     * 转Boolean类型
     *
     * @param obj 值
     * @return 返回
     */
    private boolean parseBoolean(Object obj) {
        boolean res = false;
        if (obj instanceof Boolean) {
            res = (boolean) obj;
        } else if (obj instanceof String) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Integer) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Float) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Long) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Double) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Short) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Character) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else if (obj instanceof Byte) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        } else {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 boolean 类型转换");
        }
        return res;
    }

    /**
     * 转Float类型
     *
     * @param obj 值
     * @return 返回
     */
    private float parseFloat(Object obj) {
        float res = 0.0f;
        try {
            if (obj instanceof Float) {
                res = (float) obj;
            } else if (obj instanceof String) {
                res = Float.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.floatValue();
                showErrorLog(obj + " 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 float 类型转换");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.floatValue();
                showErrorLog(obj + " 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.floatValue();
                showErrorLog(obj + " 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.floatValue();
            } else if (obj instanceof Character) {
                res = Float.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.floatValue();
            } else {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 float 类型转换");
            }
        } catch (NumberFormatException e) {
            showErrorLog("NumberFormatException：" + obj.getClass().getName());
        }
        return res;
    }

    /**
     * 转Long类型
     *
     * @param obj 值
     * @return 返回
     */
    private long parseLong(Object obj) {
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
                throwException(ExceptionType.ClassCastException, obj + " 不支持 Long 类型转换");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.longValue();
                showErrorLog(obj + " 转换为 Long 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.longValue();
                showErrorLog(obj + " 转换为 Long 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.longValue();
            } else if (obj instanceof Character) {
                res = Long.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.longValue();
            } else {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 Long 类型转换");
            }
        } catch (NumberFormatException e) {
            showErrorLog("NumberFormatException：" + obj.getClass().getName());
        }
        return res;
    }

    /**
     * 转Double类型
     *
     * @param obj 值
     * @return 返回
     */
    private double parseDouble(Object obj) {
        double res = 0;
        try {
            if (obj instanceof Double) {
                res = (double) obj;
            } else if (obj instanceof String) {
                res = Double.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.doubleValue();
                showErrorLog(obj + "  转换为 double 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 double 类型转换");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.doubleValue();
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.doubleValue();
                showErrorLog(obj + "  转换为 double 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.doubleValue();
            } else if (obj instanceof Character) {
                res = Double.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.doubleValue();
            } else {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 double 类型转换");
            }
        } catch (NumberFormatException e) {
            showErrorLog("NumberFormatException：" + obj.getClass().getName());
        }
        return res;
    }

    /**
     * 转Short类型
     *
     * @param obj 值
     * @return 返回
     */
    private short parseShort(Object obj) {
        short res = 0;
        try {
            if (obj instanceof Short) {
                res = (short) obj;
            } else if (obj instanceof String) {
                res = Short.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.shortValue();
                showErrorLog(obj + "  转换为 Short 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 Short 类型转换");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.shortValue();
                showErrorLog(obj + "  转换为 Short 可能会丢失精度");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.shortValue();
                showErrorLog(obj + "  转换为 Short 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.shortValue();
                showErrorLog(obj + "  转换为 Short 可能会丢失精度");
            } else if (obj instanceof Character) {
                res = Short.valueOf(String.valueOf(obj));
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.shortValue();
            } else {
                throwException(ExceptionType.ClassCastException, obj + " 不支持 Short 类型转换");
            }
        } catch (NumberFormatException e) {
            showErrorLog("NumberFormatException：" + obj.getClass().getName());
        }
        return res;
    }

    /**
     * 转Character类型
     *
     * @param obj 值
     * @return 返回
     */
    private char parseCharacter(Object obj) {
        char res = 0;
        if (obj instanceof Character) {
            res = (char) obj;
        } else if (obj instanceof String) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Integer) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Boolean) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Float) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Long) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Double) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Short) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else if (obj instanceof Byte) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        } else {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 char 类型转换");
        }
        return res;
    }

    /**
     * 转Byte类型
     *
     * @param obj 值
     * @return 返回
     */
    private byte parseByte(Object obj) {
        byte res = 0;
        if (obj instanceof Byte) {
            res = (byte) obj;
        } else if (obj instanceof String) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Integer) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Boolean) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Float) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Long) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Double) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Short) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else if (obj instanceof Character) {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        } else {
            throwException(ExceptionType.ClassCastException, obj + " 不支持 byte 类型转换");
        }
        return res;
    }

    /**
     * 警告Log
     *
     * @param error 错误信息
     */
    private void showErrorLog(String error) {
        if (!mErrors.contains(error)) {
            HttpLog.e(error);
            mErrors.add(error);
        }
    }

    /**
     * 抛出异常
     *
     * @param exception 异常类型
     * @param error     错误信息
     */
    private void throwException(@ExceptionType int exception, String error) {
        if (!mErrors.contains(error)) {
            HttpLog.exception(exception, error);
            mErrors.add(error);
        }
    }

}
