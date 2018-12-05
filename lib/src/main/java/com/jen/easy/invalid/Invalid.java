package com.jen.easy.invalid;

import java.lang.reflect.Field;

/**
 * 作者：ShannJenn
 * 时间：2018/10/27.
 * 说明： 判断是否失效
 */
public class Invalid {
    /**
     * @param clazz class
     * @param type  类型
     * @return boolean
     */
    public static boolean isEasyInvalid(Class clazz, EasyInvalid.Type type) {
        boolean isInvalid = false;
        boolean isEasyInvalid = clazz.isAnnotationPresent(EasyInvalid.class);
        if (isEasyInvalid) {
            EasyInvalid param = (EasyInvalid) clazz.getAnnotation(EasyInvalid.class);
            EasyInvalid.Type[] types = param.value();
            for (EasyInvalid.Type invalidType : types) {
                if (type == invalidType) {
                    isInvalid = true;
                    break;
                }
            }
        }
        return isInvalid;
    }

    /**
     * @param field field
     * @param type  类型
     * @return boolean
     */
    public static boolean isEasyInvalid(Field field, EasyInvalid.Type type) {
        boolean isInvalid = false;
        boolean isEasyInvalid = field.isAnnotationPresent(EasyInvalid.class);
        if (isEasyInvalid) {
            EasyInvalid param = field.getAnnotation(EasyInvalid.class);
            EasyInvalid.Type[] types = param.value();
            for (EasyInvalid.Type invalidType : types) {
                if (type == invalidType) {
                    isInvalid = true;
                    break;
                }
            }
        }
        return isInvalid;
    }
}
