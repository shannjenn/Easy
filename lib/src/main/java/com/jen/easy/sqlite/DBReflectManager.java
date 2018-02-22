package com.jen.easy.sqlite;

import com.jen.easy.EasyMouse;
import com.jen.easy.constant.FieldType;
import com.jen.easy.log.EasyLibLog;

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
    private static final String TAG = "DBReflectManager : ";

    /**
     * 获取表名
     *
     * @param clazz 类
     * @return 返回表名
     */
    static String getTableName(Class clazz) {
        if (clazz == null) {
            EasyLibLog.e(TAG + "传入的Class为空");
            return null;
        }
        boolean isAnnotation = clazz.isAnnotationPresent(EasyMouse.DB.Table.class);
        if (!isAnnotation) {
            EasyLibLog.e(TAG + clazz.getName() + "未增加表注释");
            return null;
        }
        EasyMouse.DB.Table table = (EasyMouse.DB.Table) clazz.getAnnotation(EasyMouse.DB.Table.class);
        return table.value();
    }

    /**
     * 获取字字段(包含主键)
     *
     * @param clazz        类
     * @param primaryKeys  主键
     * @param column_type  列名_类型
     * @param column_field 列名_变量
     */
    static void getColumnNames(Class clazz, List<String> primaryKeys, Map<String, String> column_type, Map<String, Field> column_field) {
        if (clazz == null) {
            EasyLibLog.e(TAG + "getColumnNames clazz is not null");
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String columnName = "";
            boolean isPrimary = false;
            String type = field.getGenericType().toString();

            boolean isAnnotation = field.isAnnotationPresent(EasyMouse.DB.Column.class);
            if (isAnnotation) {
                EasyMouse.DB.Column columnClass = field.getAnnotation(EasyMouse.DB.Column.class);
                boolean noColumn = columnClass.noColumn();
                if (noColumn)
                    continue;
                columnName = columnClass.value().trim();
                isPrimary = columnClass.primaryKey();
            }

            if (columnName.length() == 0) {
                columnName = field.getName();
            }
            if (FieldType.isOtherField(columnName)) {
                continue;
            }
            if (isPrimary && primaryKeys != null)
                primaryKeys.add(columnName);
            if (column_type != null)
                column_type.put(columnName, type);
            if (column_field != null)
                column_field.put(columnName, field);
        }
    }

    /**
     * 获取主键集合
     *
     * @param clazz 类
     * @return 主键集合
     */
    static List<String> getPrimaryKeys(Class clazz) {
        List<String> primaryKeys = new ArrayList<>();
        if (clazz == null) {
            EasyLibLog.e(TAG + "传入的Class为空");
            return primaryKeys;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnnotation = field.isAnnotationPresent(EasyMouse.DB.Column.class);
            if (!isAnnotation)
                continue;

            EasyMouse.DB.Column columnClass = field.getAnnotation(EasyMouse.DB.Column.class);
            boolean noColumn = columnClass.noColumn();
            if (noColumn)
                continue;

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
        if (obj == null || obj instanceof Class) {
            EasyLibLog.e(TAG + "getPrimaryKeyValue clazz is not null");
            return primaryKeys_values;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isAnnotation = field.isAnnotationPresent(EasyMouse.DB.Column.class);
            if (!isAnnotation)
                continue;

            EasyMouse.DB.Column columnClass = field.getAnnotation(EasyMouse.DB.Column.class);
            boolean noColumn = columnClass.noColumn();
            if (noColumn)
                continue;

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
                e.printStackTrace();
            }
        }
        return primaryKeys_values;
    }
}
