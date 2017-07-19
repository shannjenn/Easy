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

class DBMan {
    /**
     * 获取表名
     *
     * @param classObj
     * @return
     */
    protected static String getTableName(Class<?> classObj) {
        String tbName = null;
        Annotation[] anns = classObj.getDeclaredAnnotations();
        for (int i = 0; i < anns.length; i++) {
            if (anns[i] instanceof EasyTable) {
                EasyTable easyTable = (EasyTable) anns[i];
                tbName = easyTable.tableName();
            }
        }
        return tbName;
    }

    /**
     * 获取字段名
     *
     * @param classObj
     * @return Map<String, List<String>>: (field, primaryKey)
     */
    protected static Map<String, Object> getFieldNames(Class<?> classObj) {
        Map<String, Object> fields = new HashMap<>();
        List<String> primaryKey = new ArrayList<>();
        Map<String, Integer> column = new HashMap<>();

        Field[] fs = classObj.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Annotation[] anns = fs[i].getDeclaredAnnotations();
            for (int j = 0; j < anns.length; j++) {
                if (anns[j] instanceof EasyColumn) {
                    EasyColumn easyField = (EasyColumn) anns[j];
                    String keyName = easyField.columnName();
                    if (keyName == null) {
                        continue;
                    }
                    boolean isPrimary = easyField.primaryKey();
                    int keytype = easyField.columnType();

                    if (isPrimary)
                        primaryKey.add(keyName);
                    column.put(keyName, keytype);
                    break;
                }
            }
        }
        fields.put("primaryKey", primaryKey);
        fields.put("column", column);
        return fields;
    }
}
