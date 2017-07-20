package com.jen.easy.sqlite;

import com.jen.easy.constant.FieldType;
import com.jen.easy.sqlite.imp.EasyColumn;
import com.jen.easy.sqlite.imp.EasyTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表操控
 * Created by Jen on 2017/7/19.
 */

class DBReflectMan {

    /**
     * 获取表名
     *
     * @param clazz
     * @return
     */
    protected static String getTableName(Class clazz) {
        String tbName = null;
        Annotation[] anns = clazz.getDeclaredAnnotations();
        for (int i = 0; i < anns.length; i++) {
            if (anns[i] instanceof EasyTable) {
                EasyTable easyTable = (EasyTable) anns[i];
                tbName = easyTable.tableName();
            }
        }
        return tbName;
    }

    /**
     * 获取数据列
     *
     * @param clazz
     * @return Map<String, List<String>>: (column（name,type）, primaryKey)
     */
    protected static Map<String, Object> getColumnNames(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, String> column = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] anns = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyColumn) {
                    EasyColumn easyField = (EasyColumn) anns[j];
                    String coulumnName = easyField.columnName();
                    if (coulumnName == null) {
                        continue;
                    }
                    boolean isPrimary = easyField.primaryKey();
                    String type = fields[i].getGenericType().toString();

                    if (isPrimary)
                        primaryKey.add(coulumnName);
                    column.put(coulumnName, type);
                    break;
                }
            }
        }
        objectMap.put("primaryKey", primaryKey);
        objectMap.put("column", column);
        return objectMap;
    }

    /**
     * 获取字字段
     *
     * @param clazz
     * @return Map<String, List<String>>: (column, primaryKey,fieldName)
     */
    public static Map<String, Object> getFields(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, String> column = new HashMap<>();
        Map<String, Field> fieldName = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Annotation[] anns = fields[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyColumn) {
                    EasyColumn easyField = (EasyColumn) anns[j];
                    String coulumnName = easyField.columnName();
                    if (coulumnName == null) {
                        continue;
                    }
                    boolean isPrimary = easyField.primaryKey();
                    String type = fields[i].getGenericType().toString();

                    if (isPrimary)
                        primaryKey.add(coulumnName);
                    column.put(coulumnName, type);
                    fieldName.put(coulumnName, fields[i]);
                    break;
                }
            }
        }
        objectMap.put("primaryKey", primaryKey);
        objectMap.put("column", column);
        objectMap.put("fieldName", fieldName);
        return objectMap;
    }
}
