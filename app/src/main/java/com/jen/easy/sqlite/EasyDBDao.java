package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.constant.FieldType;
import com.jen.easy.util.DataFormat;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

public class EasyDBDao {


    /**
     * @param clazz 要查找的对象
     * @param id
     * @return
     */
    public static Object searchById(Class clazz, String id) {
        if (clazz == null || id == null) {
            DBLog.w("clazz is null or id is null");
            return null;
        }
        String tableName = DBReflectMan.getTableName(clazz);
        if (tableName == null) {
            DBLog.w("tableName is null");
            return null;
        }
        Map<String, Object> objectMap = DBReflectMan.getFields(clazz);
        List<String> primaryKey = (List<String>) objectMap.get("primaryKey");
//        Map<String, Integer> column = (Map<String, Integer>) objectMap.get("column");
        Map<String, Field> fieldName = (Map<String, Field>) objectMap.get("fieldName");

        if (primaryKey.size() == 0) {
            DBLog.w("primaryKey is null");
            return null;
        }
        Cursor cursor = null;
        try {
            SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
            String selection = primaryKey.get(0) + "=?";
            String[] selectionArgs = {id};
            cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            Object obj = valuation(clazz, fieldName, cursor);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return null;
    }

    /**
     * 插入数据
     *
     * @param obj
     */
    public static void insert(Object obj) {
        if (obj == null) {
            DBLog.w("obj is null");
            return;
        }
        String tableName = DBReflectMan.getTableName(obj.getClass());
        ContentValues values = cntentValues(obj);
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        db.insert(tableName, null, values);
    }

    /**
     * 设值
     *
     * @param obj
     * @return
     */
    private static ContentValues cntentValues(Object obj) {
        Map<String, Object> objectMap = DBReflectMan.getFields(obj.getClass());
//        List<String> primaryKey = (List<String>) objectMap.get("primaryKey");
//        Map<String, Integer> column = (Map<String, Integer>) objectMap.get("column");
        Map<String, Field> fieldName = (Map<String, Field>) objectMap.get("fieldName");

        ContentValues values = new ContentValues();
        try {
            for (String column : fieldName.keySet()) {
                Field field = fieldName.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();
                if (type.equals(FieldType.STRING)) {
                    String value = (String) field.get(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.INTEGER)) {
                    int value = field.getInt(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.BOOLEAN)) {
                    boolean value = field.getBoolean(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.DATE)) {
                    Date value = (Date) field.get(obj);
                    values.put(column, value == null ? null : DataFormat.format(value));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 赋值
     *
     * @param clazz
     * @param fieldName
     * @param cursor
     * @return
     */
    private static Object valuation(Class clazz, Map<String, Field> fieldName, Cursor cursor) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            for (String column : fieldName.keySet()) {
                Field field = fieldName.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();
                if (type.equals(FieldType.STRING)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.INTEGER)) {
                    int value = cursor.getInt(cursor.getColumnIndex(column));
                    field.setInt(obj, value);
                } else if (type.equals(FieldType.BOOLEAN)) {
                    int value = cursor.getInt(cursor.getColumnIndex(column));
                    field.setBoolean(obj, value != 0);
                } else if (type.equals(FieldType.DATE)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    Date date = DataFormat.parser(value);
                    field.set(obj, date);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
