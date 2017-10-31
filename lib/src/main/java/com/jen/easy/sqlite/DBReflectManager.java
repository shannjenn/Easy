package com.jen.easy.sqlite;

import com.jen.easy.EasyMouse;
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
    private static final String TAG = DBReflectManager.class.getSimpleName() + " : ";
    /**
     * 主键名
     */
    static final String PRIMARY_KEY = "primary_key";
    /**
     * 外键名
     */
    static final String FOREIGN_KEY = "foreign_Key";
    /**
     * 全部列明和属性类型
     */
    static final String COLUMN_TYPE = "column_type";
    /**
     * 全部列明和属性名称
     */
    static final String COLUMN_FIELD = "column_field";


    /**
     * 获取表名
     *
     * @param clazz
     * @return
     */
    static String getTableName(Class clazz) {
        if (clazz == null) {
            EasyLibLog.e(TAG + "getTableName clazz is not null");
            return null;
        }
        String tbName = null;
        boolean isAnno = clazz.isAnnotationPresent(EasyMouse.DB.Table.class);
        if (!isAnno) {
            EasyLibLog.e(TAG + "getTableName clazz is not AnnotationPresent");
            return null;
        }
        EasyMouse.DB.Table table = (EasyMouse.DB.Table) clazz.getAnnotation(EasyMouse.DB.Table.class);
        tbName = table.value();
        return tbName;
    }

    /**
     * 获取字字段
     *
     * @param clazz
     * @return
     */
    static Map<String, Object> getColumnNames(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, String> column_foreignKey = new HashMap<>();
        Map<String, String> column_type = new HashMap<>();
        Map<String, Field> fieldName = new HashMap<>();
        objectMap.put(PRIMARY_KEY, primaryKey);
        objectMap.put(FOREIGN_KEY, column_foreignKey);
        objectMap.put(COLUMN_TYPE, column_type);
        objectMap.put(COLUMN_FIELD, fieldName);
        if (clazz == null) {
            EasyLibLog.e(TAG + "getColumnNames clazz is not null");
            return objectMap;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.DB.Column.class);
            if (!isAnno)
                continue;
            EasyMouse.DB.Column columnClass = field.getAnnotation(EasyMouse.DB.Column.class);
            String coulumnName = columnClass.columnName();
            boolean isPrimary = columnClass.primaryKey();
            String foreignKey = columnClass.foreignKey().trim();
            String type = field.getGenericType().toString();

            if (coulumnName.trim().length() == 0) {
                continue;
            }
            if (isPrimary)
                primaryKey.add(coulumnName);
            if (foreignKey.length() > 0)
                column_foreignKey.put(coulumnName, foreignKey);
            column_type.put(coulumnName, type);
            fieldName.put(coulumnName, field);
        }
        return objectMap;
    }

    /**
     * 获取字字段(只获取一个key)
     *
     * @param obj
     * @return
     */
    static String getPrimaryKeyValue(Object obj, String primaryKey) {
        if (obj == null || obj instanceof Class || primaryKey == null) {
            EasyLibLog.e(TAG + "getPrimaryKeyValue clazz is not null");
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.DB.Column.class);
            if (!isAnno)
                continue;
            EasyMouse.DB.Column columnClass = field.getAnnotation(EasyMouse.DB.Column.class);
            String columnName = columnClass.columnName().trim();
            if (columnName.equals(primaryKey)) {
                try {
                    field.setAccessible(true);
                    String value = field.get(obj) + "";
                    return value;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取字字段(只获取一个key)
     *
     * @param obj
     * @return
     */
    static Map<String, String> getPrimaryKeysValues(Object obj) {
        Map<String, String> primaryKeys_values = new HashMap<>();
        if (obj == null || obj instanceof Class) {
            EasyLibLog.e(TAG + "getPrimaryKeyValue clazz is not null");
            return primaryKeys_values;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isAnno = field.isAnnotationPresent(EasyMouse.DB.Column.class);
            if (!isAnno)
                continue;
            EasyMouse.DB.Column columnClass = field.getAnnotation(EasyMouse.DB.Column.class);
            if (columnClass.primaryKey()) {
                String columnName = columnClass.columnName().trim();
                field.setAccessible(true);
                try {
                    String value = field.get(obj) + "";
                    primaryKeys_values.put(columnName, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return primaryKeys_values;
    }
}
