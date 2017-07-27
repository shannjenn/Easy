package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.constant.FieldType;
import com.jen.easy.util.DataFormat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jen.easy.sqlite.DBReflectMan.COLUMN_FIELD;

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
        List<String> primaryKey = (List<String>) objectMap.get(DBReflectMan.PRIMARY_KEY);
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(DBReflectMan.COLUMN_FIELD);

        if (primaryKey.size() == 0) {
            DBLog.w("primaryKey is null");
            return null;
        }
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        String selection = primaryKey.get(0) + "=?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        Object obj = valuation(clazz, column_field, cursor);
        cursor.close();
        return obj;
    }

    /**
     * 按条件查询
     *
     * @param clazz         (not null)
     * @param selection     查询条件(not null)
     * @param selectionArgs 条件参数(not null)
     * @param orderBy       排序
     * @param page          页数
     * @param pageNo        大于0分页,小于等于0不分页
     * @return
     */
    public static List<Object> searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo) {
        List<Object> objs = new ArrayList<>();
        if (clazz == null || selection == null || selectionArgs == null || selectionArgs.length == 0) {
            DBLog.w("clazz is null or id is null");
            return objs;
        }
        String tableName = DBReflectMan.getTableName(clazz);
        if (tableName == null) {
            DBLog.w("tableName is null");
            return objs;
        }
        Map<String, Object> objectMap = DBReflectMan.getFields(clazz);
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(DBReflectMan.COLUMN_FIELD);
        if (column_field.size() == 0)
            return objs;

        String limit = null;
        if (pageNo > 0) {
            limit = page * pageNo + "," + pageNo;
        }
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, orderBy, limit);
        if (cursor == null || cursor.getCount() == 0) {
            return objs;
        }
        cursor.moveToFirst();
        do {
            Object obj = valuation(clazz, column_field, cursor);
            objs.add(obj);
        } while (cursor.moveToNext());
        cursor.close();
        return objs;
    }

    /**
     * 按条件查询
     *
     * @param clazz
     * @param selection     查询条件
     * @param selectionArgs 条件参数
     * @param orderBy       排序（null不进行排序）
     * @return
     */
    public static List<Object> searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy) {
        return searchByWhere(clazz, selection, selectionArgs, orderBy, 0, 0);
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
     * 插入或者更新数据
     *
     * @param obj
     */
    public static void replace(Object obj) {
        if (obj == null) {
            DBLog.w("obj is null");
            return;
        }
        String tableName = DBReflectMan.getTableName(obj.getClass());
        ContentValues values = cntentValues(obj);
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        db.replace(tableName, null, values);
    }

    /**
     * 删除
     *
     * @param clazz
     * @param id
     */
    public static void delete(Class clazz, String id) {
        if (clazz == null) {
            DBLog.w("obj is null");
            return;
        }
        String tableName = DBReflectMan.getTableName(clazz);
        if (tableName == null) {
            DBLog.w("tableName is null");
            return;
        }
        Map<String, Object> objectMap = DBReflectMan.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectMan.PRIMARY_KEY);
        if (primarys.size() == 0) {
            DBLog.w("primary is null");
            return;
        }
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        db.delete(tableName, primarys.get(0) + "=?", new String[]{id});
    }

    public static void delete(Class clazz, String selection, String[] selectionArgs) {
        if (clazz == null || selection == null || selectionArgs == null || selectionArgs.length == 0) {
            DBLog.w("obj or selection or selectionArgs is error");
            return;
        }
        String tableName = DBReflectMan.getTableName(clazz);
        if (tableName == null) {
            DBLog.w("tableName is null");
            return;
        }
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        db.delete(tableName, selection, selectionArgs);
    }

    /**
     * 自定义操作
     *
     * @param sql
     */
    public static void execSQL(String sql) {
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 自定义操作
     *
     * @param sql
     * @param bindArgs 参数的值
     */
    public static void execSQL(String sql, String[] bindArgs) {
        SQLiteDatabase db = DBHelper.getInstance().getReadDatabse();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    /**
     * 数据库存值
     *
     * @param obj
     * @return
     */
    private static ContentValues cntentValues(Object obj) {
        Map<String, Object> objectMap = DBReflectMan.getFields(obj.getClass());
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(COLUMN_FIELD);

        ContentValues values = new ContentValues();
        try {
            for (String column : column_field.keySet()) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();
                if (type.equals(FieldType.CHAR)) {
                    char value = (char) field.getChar(obj);
                    values.put(column, value + "");
                } else if (type.equals(FieldType.STRING)) {
                    String value = (String) field.get(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.BYTE)) {
                    byte value = field.getByte(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.SHORT)) {
                    short value = field.getShort(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.INTEGER)) {
                    int value = field.getInt(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.FLOAT)) {
                    float value = field.getFloat(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.DOUBLE)) {
                    double value = field.getDouble(obj);
                    values.put(column, value);
                } else if (type.equals(FieldType.LONG)) {
                    long value = field.getLong(obj);
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
     * cursor赋值到对象
     *
     * @param clazz
     * @param column_field
     * @param cursor
     * @return
     */
    private static Object valuation(Class clazz, Map<String, Field> column_field, Cursor cursor) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            for (String column : column_field.keySet()) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();

                if (type.equals(FieldType.CHAR)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.STRING)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.BYTE)) {
                    int value = cursor.getInt(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.SHORT)) {
                    short value = cursor.getShort(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.INTEGER)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.FLOAT)) {
                    float value = cursor.getFloat(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.DOUBLE)) {
                    double value = cursor.getDouble(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.LONG)) {
                    long value = cursor.getLong(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(FieldType.BOOLEAN)) {
                    boolean value = cursor.getInt(cursor.getColumnIndex(column)) > 0;
                    field.set(obj, value);
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
