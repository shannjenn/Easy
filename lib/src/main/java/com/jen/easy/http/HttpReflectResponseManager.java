package com.jen.easy.http;

import com.jen.easy.EasyResponse;
import com.jen.easy.constant.FieldType;
import com.jen.easy.http.response.EasyHttpResponse;
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
     * 解析返回实体类参数
     *
     * @param clazz 实体类
     * @return Map >> key:String, value:List<Field>
     */
    static Map<String, List<Field>> parseBodyResponse(Class clazz) {
        Map<String, List<Field>> body = new HashMap<>();
        Class myClass = clazz;
        String clazzName = myClass.getName();
        String respName = EasyHttpResponse.class.getName();
        String objName = Object.class.getName();
        while (!clazzName.equals(respName) && !clazzName.equals(objName)) {
            boolean isInvalid = Invalid.isEasyInvalid(myClass, EasyInvalidType.Response);
            if (!isInvalid) {
                parseBodyField(myClass, body);
            }
            myClass = myClass.getSuperclass();
            clazzName = myClass.getName();
        }
        return body;
    }

    /**
     * 获取单个返回实体
     *
     * @param clazz 类
     * @param body  名称_变量
     */
    private static void parseBodyField(Class clazz, Map<String, List<Field>> body) {
        Field[] fieldsSuper = clazz.getDeclaredFields();
        for (Field field : fieldsSuper) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalidType.Response);
            if (isInvalid) {
                continue;
            }
            boolean isAnnotation = field.isAnnotationPresent(EasyResponse.class);
            String paramName = "";
//            EasyResponseType paramType = EasyResponseType.Param;
            if (isAnnotation) {
                EasyResponse param = field.getAnnotation(EasyResponse.class);
//                paramType = param.type();
                paramName = param.value().trim();
            }
            if (paramName.length() == 0) {
                paramName = field.getName();
            }
            if (FieldType.isOtherField(paramName)) {
                continue;
            }
            List<Field> fields;
            boolean haveFields = false;
            /*
             * 多个字段相同key,多对象接收 重要标记
             */
            if (body.containsKey(paramName)) {
                fields = body.get(paramName);
                haveFields = true;
            } else {
                fields = new ArrayList<>();
            }
            fields.add(field);
            if (!haveFields) {
                body.put(paramName, fields);
            }
        }
    }

}
