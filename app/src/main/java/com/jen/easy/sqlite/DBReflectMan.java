package com.jen.easy.sqlite;

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
    public static final String TYPE0 = "class java.lang.String";
    public static final String TYPE1 = "class java.lang.Integer";
    public static final String TYPE2 = "class java.lang.Boolean";
    public static final String TYPE3 = "class java.util.Date";

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
     * @return Map<String, List<String>>: (column, primaryKey)
     */
    protected static Map<String, Object> getColumnNames(Class clazz) {
        Map<String, Object> objectMap = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, Integer> column = new HashMap<>();

        Field[] fs = clazz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Annotation[] anns = fs[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyColumn) {
                    EasyColumn easyField = (EasyColumn) anns[j];
                    String coulumnName = easyField.columnName();
                    if (coulumnName == null) {
                        continue;
                    }
                    boolean isPrimary = easyField.primaryKey();
                    int keytype = easyField.columnType();

                    if (isPrimary)
                        primaryKey.add(coulumnName);
                    column.put(coulumnName, keytype);
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
        Map<String, Integer> column = new HashMap<>();
        Map<String, Field> fieldName = new HashMap<>();

        Field[] fs = clazz.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Annotation[] anns = fs[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyColumn) {
                    EasyColumn easyField = (EasyColumn) anns[j];
                    String coulumnName = easyField.columnName();
                    if (coulumnName == null) {
                        continue;
                    }
                    boolean isPrimary = easyField.primaryKey();
                    int oulumnType = easyField.columnType();

                    if (isPrimary)
                        primaryKey.add(coulumnName);
                    column.put(coulumnName, oulumnType);
                    fieldName.put(coulumnName, fs[i]);
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
