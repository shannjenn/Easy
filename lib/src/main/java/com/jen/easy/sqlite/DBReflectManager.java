package com.jen.easy.sqlite;

import com.jen.easy.EasyColumn;
import com.jen.easy.EasyTable;
import com.jen.easy.constant.FieldType;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.SQLLog;
import com.jen.easy.invalid.EasyInvalidType;
import com.jen.easy.invalid.Invalid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表操控
 * Created by Jen on 2017/7/19.
 */

class DBReflectManager {

    /**
     * 列明信息
     */
    static class ColumnInfo {
        List<String> primaryKeys;//主键
        List<String> columns;//列名
        List<Field> fields;//变量

        ColumnInfo() {
            primaryKeys = new ArrayList<>();
            columns = new ArrayList<>();
            fields = new ArrayList<>();
        }
    }

    /**
     * 获取表名
     *
     * @param clazz 类
     * @return 返回表名
     */
    static String getTableName(Class clazz) {
        /*if (clazz == null) {
            Throw.exception(ExceptionType.NullPointerException, "传入的Class为空" );
            return null;
        }*/
        boolean isAnnotation = clazz.isAnnotationPresent(EasyTable.class);
        if (!isAnnotation) {
            SQLLog.exception(ExceptionType.RuntimeException, clazz.getName() + "未增加表注释");
            return null;
        }
        EasyTable table = (EasyTable) clazz.getAnnotation(EasyTable.class);
        return table.value();
    }

    /**
     * 获取字字段(包含主键)
     *
     * @param clazz 类
     */
    static ColumnInfo getColumnInfo(Class clazz) {
        ColumnInfo columnInfo = new ColumnInfo();


        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalidType.Column);
            if (isInvalid)
                continue;
            String columnName = "";
            boolean isPrimary = false;

            boolean isAnnotation = field.isAnnotationPresent(EasyColumn.class);
            if (isAnnotation) {
                EasyColumn columnClass = field.getAnnotation(EasyColumn.class);
                columnName = columnClass.value().trim();
                isPrimary = columnClass.primaryKey();
            }

            if (columnName.length() == 0) {
                columnName = field.getName();
            }
            if (FieldType.isOtherField(columnName)) {
                continue;
            }
            if (isPrimary)
                columnInfo.primaryKeys.add(columnName);
            columnInfo.columns.add(columnName);
            columnInfo.fields.add(field);
        }
        return columnInfo;
    }

    /**
     * 获取主键集合
     *
     * @param clazz 类
     * @return 主键集合
     */
    static List<String> getPrimaryKeys(Class clazz) {
        List<String> primaryKeys = new ArrayList<>();
        /*if (clazz == null) {
            EasyLog.w(TAG.EasySQL, "传入的Class为空");
            return primaryKeys;
        }*/

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalidType.Column);
            if (isInvalid)
                continue;
            boolean isAnnotation = field.isAnnotationPresent(EasyColumn.class);
            if (!isAnnotation)
                continue;
            EasyColumn columnClass = field.getAnnotation(EasyColumn.class);
            boolean isPrimary = columnClass.primaryKey();
            if (!isPrimary)
                continue;
            String name = columnClass.value().trim();
            if (name.length() == 0)
                name = field.getName();
            primaryKeys.add(name);
        }
        return primaryKeys;
    }

    /**
     * 获取主键_值 集合
     *
     * @param obj Object
     * @return 主键信息
     */
    static Map<String, String> getPrimaryKeysValues(Object obj) {
        Map<String, String> primaryKeys_values = new HashMap<>();
        /*if (obj == null || obj instanceof Class) {
            EasyLog.w(TAG.EasySQL, "getPrimaryKeyValue clazz is not null");
            return primaryKeys_values;
        }*/
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isInvalid = Invalid.isEasyInvalid(field, EasyInvalidType.Column);
            if (isInvalid)
                continue;
            boolean isAnnotation = field.isAnnotationPresent(EasyColumn.class);
            if (!isAnnotation)
                continue;
            EasyColumn columnClass = field.getAnnotation(EasyColumn.class);
            boolean isPrimary = columnClass.primaryKey();
            if (!isPrimary)
                continue;
            String name = columnClass.value().trim();
            if (name.length() == 0)
                name = field.getName();
            field.setAccessible(true);
            try {
                String value = field.get(obj) + "";
                primaryKeys_values.put(name, value);
            } catch (IllegalAccessException e) {
                SQLLog.exception(ExceptionType.IllegalAccessException, "获取主键失败");
            }
        }
        return primaryKeys_values;
    }
}
