package com.jen.easy.http;

import com.jen.easy.EasyResponse;
import com.jen.easy.EasyResponseType;
import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.invalid.EasyInvalidType;
import com.jen.easy.invalid.Invalid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 網絡
 * Created by Jen on 2017/7/19.
 */

class HttpReflectResponseManager {

    /**
     * 返回实体
     */
    static class ResponseObject {
        final Map<String, List<Field>> body = new HashMap<>();
        final Map<String, Field> heads = new HashMap<>();
    }

    /**
     * 解析返回实体类参数
     *
     * @param clazz HttpResponse
     * @return ResponseObject
     */
    static ResponseObject getResponseHeadAndBody(Class clazz) {
        ResponseObject responseObject = new ResponseObject();
        parseResponse(clazz, responseObject.body, responseObject.heads);
        return responseObject;
    }

    /**
     * 获取返回参数
     *
     * @param clazz       类
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    private static void parseResponse(Class clazz, Map<String, List<Field>> param_field, Map<String, Field> head_field) {
        /*if (clazz == null) {
            Throw.exception(ExceptionType.NullPointerException, "parseResponse clazz 空指针异常");
            return;
        }*/

        Class myClass = clazz;
        String clazzName = myClass.getName();
        String respName = HttpHeadResponse.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(respName) && !clazzName.equals(objName)) {
            boolean isInvalid = Invalid.isEasyInvalid(myClass, EasyInvalidType.Response);
            if (!isInvalid) {
                parseResponseEntity(myClass, param_field, head_field);
            }
            myClass = myClass.getSuperclass();
            clazzName = myClass.getName();
        }
    }

    /**
     * 获取单个返回参数
     *
     * @param clazz       类
     * @param param_field 名称_变量
     * @param head_field  头名称_变量
     */
    private static void parseResponseEntity(Class clazz, Map<String, List<Field>> param_field, Map<String, Field> head_field) {
        Field[] fieldsSuper = clazz.getDeclaredFields();
        for (Field field : fieldsSuper) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalidType.Response);
            if (isInvalid) {
                continue;
            }
            boolean isAnnotation = field.isAnnotationPresent(EasyResponse.class);
            String paramName = "";
            EasyResponseType paramType = EasyResponseType.Param;
            if (isAnnotation) {
                EasyResponse param = field.getAnnotation(EasyResponse.class);
                paramType = param.type();
                paramName = param.value().trim();
            }
            if (paramName.length() == 0) {
                paramName = field.getName();
            }
            if (FieldType.isOtherField(paramName)) {
                continue;
            }
            Class fieldClass = field.getType();
            switch (paramType) {
                case Param: {
                    List<Field> fields;
                    boolean haveFields = false;
                    /*
                     * 多个字段相同key,多对象接收 重要标记
                     */
                    if (param_field.containsKey(paramName)) {
                        fields = param_field.get(paramName);
                        haveFields = true;
                    } else {
                        fields = new ArrayList<>();
                    }
                    fields.add(field);
                    if (!haveFields) {
                        param_field.put(paramName, fields);
                    }
                    break;
                }
                case Head: {
                    if (!FieldType.isString(fieldClass)) {
                        HttpLog.exception(ExceptionType.ClassCastException, "请求头返回变量必须为String类型:" + paramName);
                        continue;
                    }
                    head_field.put(paramName, field);
                    break;
                }
            }
        }
    }
}
