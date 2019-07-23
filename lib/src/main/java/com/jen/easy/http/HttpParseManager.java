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

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Json数据解析
 */
class HttpParseManager {
    /*错误提示*/
    private List<String> mErrors = new ArrayList<>();

    /**
     * json解析
     *
     * @param tClass   类
     * @return 值
     */
    <T> T newResponseInstance(Class<T> tClass) {
        T response;
        try {
            response = tClass.newInstance();
        } catch (InstantiationException e) {
            HttpLog.w("newResponseInstance InstantiationException 创建对象出错：" + tClass.getName());
            return null;
        } catch (IllegalAccessException e) {
            HttpLog.w("newResponseInstance IllegalAccessException 创建对象出错" + tClass.getName());
            return null;
        }
        return response;
    }

    /**
     * json解析
     *
     * @param tClass 类
     * @param obj    数据
     * @return 值
     */
    <T> T parseResponseBody(Class<T> tClass, String obj) {
        HttpLog.d("解析：" + tClass.getName() + "----开始");
        long startTime = System.currentTimeMillis();
        T response;
        JSONObject object;
        try {
            object = new JSONObject(obj);
        } catch (JSONException e) {
            showErrorLog("JSONException 解析错误，不属于JSONObject数据");
            return null;
        }
        response = parseJSONObject(tClass, object);
        mErrors.clear();
        double timeSec = (System.currentTimeMillis() - startTime) / 1000d;
        if (response == null) {
            HttpLog.w("解析：" + tClass.getName() + "----失败 解析耗时:" + timeSec + "秒");
        } else {
            HttpLog.d("解析：" + tClass.getName() + "----成功 解析耗时:" + timeSec + "秒");
        }
        return response;
    }

    /**
     * 解析实体
     *
     * @param tClass     类
     * @param jsonObject 数据
     * @return 值
     */
    private <T> T parseJSONObject(Class<T> tClass, JSONObject jsonObject) {
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

        Map<String, List<Field>> bodyFieldMap = HttpReflectResponseManager.parseBodyResponse(tObj.getClass());
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String param = keys.next();
            if (!bodyFieldMap.containsKey(param)) {
                continue;
            }
            List<Field> fields = bodyFieldMap.get(param);
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
                    Object value = parseField(param, object, fieldClass, type);//解析返回数据实体
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
     * 解析返回数据变量
     *
     * @param object     数据
     * @param fieldClass 变量
     * @param type       可以为null，为空时不做List解析
     * @return 返回值
     */
    private Object parseField(String param, Object object, Class fieldClass, Type type) {
//        Class fieldClass = field.getType();
        if (FieldType.isString(fieldClass)) {
            return parseString(param, object);
        } else if (FieldType.isInt(fieldClass)) {
            return parseInt(param, object);
        } else if (FieldType.isLong(fieldClass)) {
            return parseLong(param, object);
        } else if (FieldType.isFloat(fieldClass)) {
            return parseFloat(param, object);
        } else if (FieldType.isDouble(fieldClass)) {
            return parseDouble(param, object);
        } else if (FieldType.isShort(fieldClass)) {
            return parseShort(param, object);
        } else if (FieldType.isBoolean(fieldClass)) {
            return parseBoolean(param, object);
        } else if (FieldType.isChar(fieldClass)) {
            return parseCharacter(param, object);
        } else if (FieldType.isByte(fieldClass)) {
            return parseByte(param, object);
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
                        Object value = parseField(clazz.getName(), jsonObj, clazz, null);
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
                    showErrorLog(String.format("ClassNotFoundException：JSONArray数据解析错误，集合：%s 集合对象：%s", type.toString(), type1.toString()));
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
                    Object value = parseField(clazz.getName(), jsonObj, clazz, null);
                    if (value != null) {
                        list.add(value);
                    } else {
                        throwException(ExceptionType.ClassCastException, "JSONArray数据解析错误6：" + type.toString());
                    }
                } catch (ClassNotFoundException e) {
                    showErrorLog(String.format("ClassNotFoundException：JSONArray数据解析错误，集合：%s 集合对象：%s", type.toString(), type1.toString()));
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
    private String parseString(String param, Object obj) {
        final String logType = "String";
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
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Int类型
     *
     * @param obj 值
     * @return 返回
     */
    private int parseInt(String param, Object obj) {
        final String logType = "Integer";
        int res = 0;
        try {
            if (obj instanceof Integer) {
                res = (int) obj;
            } else if (obj instanceof String) {
                res = Integer.valueOf((String) obj);
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, getParseErrorEnable(param, obj, logType));
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.intValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.intValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.intValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.intValue();
            } else if (obj instanceof Character) {
                res = Integer.valueOf(obj.toString());
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.intValue();
            } else {
                throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
            }
        } catch (NumberFormatException e) {
            throwException(ExceptionType.NumberFormatException, getParseErrorThrow(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Boolean类型
     *
     * @param obj 值
     * @return 返回
     */
    private boolean parseBoolean(String param, Object obj) {
        final String logType = "Boolean";
        boolean res = false;
        if (obj instanceof Boolean) {
            res = (boolean) obj;
        } else if (obj instanceof String) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Integer) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Float) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Long) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Double) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Short) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Character) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Byte) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Float类型
     *
     * @param obj 值
     * @return 返回
     */
    private float parseFloat(String param, Object obj) {
        final String logType = "Float";
        float res = 0.0f;
        try {
            if (obj instanceof Float) {
                res = (float) obj;
            } else if (obj instanceof String) {
                res = Float.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.floatValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, getParseErrorEnable(param, obj, logType));
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.floatValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.floatValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.floatValue();
            } else if (obj instanceof Character) {
                res = Float.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.floatValue();
            } else {
                throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
            }
        } catch (NumberFormatException e) {
            throwException(ExceptionType.NumberFormatException, getParseErrorThrow(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Long类型
     *
     * @param obj 值
     * @return 返回
     */
    private long parseLong(String param, Object obj) {
        final String logType = "Long";
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
                throwException(ExceptionType.ClassCastException, getParseErrorEnable(param, obj, logType));
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.longValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.longValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.longValue();
            } else if (obj instanceof Character) {
                res = Long.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.longValue();
            } else {
                throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
            }
        } catch (NumberFormatException e) {
            throwException(ExceptionType.NumberFormatException, getParseErrorThrow(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Double类型
     *
     * @param obj 值
     * @return 返回
     */
    private double parseDouble(String param, Object obj) {
        final String logType = "Double";
        double res = 0;
        try {
            if (obj instanceof Double) {
                res = (double) obj;
            } else if (obj instanceof String) {
                res = Double.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.doubleValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, getParseErrorEnable(param, obj, logType));
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.doubleValue();
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.doubleValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.doubleValue();
            } else if (obj instanceof Character) {
                res = Double.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.doubleValue();
            } else {
                throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
            }
        } catch (NumberFormatException e) {
            throwException(ExceptionType.NumberFormatException, getParseErrorThrow(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Short类型
     *
     * @param obj 值
     * @return 返回
     */
    private short parseShort(String param, Object obj) {
        final String logType = "Short";
        short res = 0;
        try {
            if (obj instanceof Short) {
                res = (short) obj;
            } else if (obj instanceof String) {
                res = Short.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.shortValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Boolean) {
                throwException(ExceptionType.ClassCastException, getParseErrorEnable(param, obj, logType));
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.shortValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.shortValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.shortValue();
                showWarnLog(getParseErrorLoss(param, obj, logType));
            } else if (obj instanceof Character) {
                res = Short.valueOf(String.valueOf(obj));
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.shortValue();
            } else {
                throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
            }
        } catch (NumberFormatException e) {
            throwException(ExceptionType.NumberFormatException, getParseErrorThrow(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Character类型
     *
     * @param obj 值
     * @return 返回
     */
    private char parseCharacter(String param, Object obj) {
        final String logType = "Character";
        char res = 0;
        if (obj instanceof Character) {
            res = (char) obj;
        } else if (obj instanceof String) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Integer) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Boolean) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Float) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Long) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Double) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Short) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Byte) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        }
        return res;
    }

    /**
     * 转Byte类型
     *
     * @param obj 值
     * @return 返回
     */
    private byte parseByte(String param, Object obj) {
        final String logType = "Byte";
        byte res = 0;
        if (obj instanceof Byte) {
            res = (byte) obj;
        } else if (obj instanceof String) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Integer) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Boolean) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Float) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Long) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Double) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Short) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else if (obj instanceof Character) {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        } else {
            throwException(ExceptionType.ClassCastException, getParseErrorUnInvalid(param, obj, logType));
        }
        return res;
    }

    private String getParseErrorLoss(String param, Object obj, String type) {
        return "参数:" + param + " Json值:" + obj + " 转换为" + type + " 类型可能会丢失精度";
    }

    private String getParseErrorEnable(String param, Object obj, String type) {
        return "参数:" + param + " Json值:" + obj + " 无法转换成" + type + "类型";
    }

    private String getParseErrorUnInvalid(String param, Object obj, String type) {
        return "参数:" + param + " Json值:" + obj + " 不支持" + type + "类型转换";
    }

    private String getParseErrorThrow(String param, Object obj, String type) {
        return "参数:" + param + " Json值:" + obj + "转换成" + type + "类型错误";
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
     * 警告Log
     *
     * @param error 错误信息
     */
    private void showWarnLog(String error) {
        if (!mErrors.contains(error)) {
            HttpLog.w(error);
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
