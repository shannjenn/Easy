package com.jen.easy.sqlite;

import com.jen.easy.EasyA;
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
     * 主键名List
     */
    static final String PRIMARY_KEY = "primary_key";
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
            DBLog.e("clazz is not null");
            return null;
        }
        String tbName = null;
        boolean isAnno = clazz.isAnnotationPresent(EasyA.DB.Table.class);
        if (!isAnno) {
            DBLog.e("clazz is not AnnotationPresent");
            return null;
        }
        EasyA.DB.Table table = (EasyA.DB.Table) clazz.getAnnotation(EasyA.DB.Table.class);
        tbName = table.tableName();
        return tbName;
    }

    /**
     * 获取数据列
     *
     * @param clazz
     * @return Map<String, List<String>>
     */
    static Map<String, Object> getColumnNames(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, String> column = new HashMap<>();
        objectMap.put(PRIMARY_KEY, primaryKey);
        objectMap.put(COLUMN_TYPE, column);
        if (clazz == null) {
            DBLog.e("clazz is not null");
            return objectMap;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyA.DB.Column.class);
            if (!isAnno)
                continue;
            EasyA.DB.Column columnClass = fields[i].getAnnotation(EasyA.DB.Column.class);
            String coulumnName = columnClass.columnName();
            boolean isPrimary = columnClass.primaryKey();
            String type = fields[i].getGenericType().toString();

            if (isPrimary)
                primaryKey.add(coulumnName);
            column.put(coulumnName, type);
        }
        return objectMap;
    }

    /**
     * 获取字字段
     *
     * @param clazz
     * @return Map<String, List<String>>
     */
    static Map<String, Object> getFields(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, String> column = new HashMap<>();
        Map<String, Field> fieldName = new HashMap<>();
        objectMap.put(PRIMARY_KEY, primaryKey);
        objectMap.put(COLUMN_TYPE, column);
        objectMap.put(COLUMN_FIELD, fieldName);
        if (clazz == null) {
            DBLog.e("clazz is not null");
            return objectMap;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean isAnno = fields[i].isAnnotationPresent(EasyA.DB.Column.class);
            if (!isAnno)
                continue;
            EasyA.DB.Column columnClass = fields[i].getAnnotation(EasyA.DB.Column.class);
            String coulumnName = columnClass.columnName();
            boolean isPrimary = columnClass.primaryKey();
            String type = fields[i].getGenericType().toString();

            if (isPrimary)
                primaryKey.add(coulumnName);
            column.put(coulumnName, type);
            fieldName.put(coulumnName, fields[i]);
        }
        return objectMap;
    }
}
