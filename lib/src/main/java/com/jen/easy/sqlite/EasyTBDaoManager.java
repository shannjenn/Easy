package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.constant.FieldType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

abstract class EasyTBDaoManager {
    /*错误提示*/
    private List<String> mErrors = new ArrayList<>();
    private EasyDatabase database;

    EasyTBDaoManager(Context context) {
        database = new EasyDatabase(context);
    }


    /**
     * @param clazz 要查找的对象
     * @param id    ID
     * @return 对象
     */
    protected <T> T searchById(Class<T> clazz, String id) {
        if (clazz == null || id == null) {
            showErrorLog("参数不能为空");
            return null;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            showErrorLog("表名不能为空，请注释 class=" + clazz.getName());
            return null;
        }
        DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(clazz);
        if (columnInfo.primaryKeys.size() == 0) {
            showErrorLog("主键不能为空，请注释 class=" + clazz.getName());
            return null;
        }

        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            db = database.getReadableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException class=" + clazz.getName());
            return null;
        }
        try {
            String selection = columnInfo.primaryKeys.get(0) + "=?";
            String[] selectionArgs = {id};
            cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return valuation(clazz, columnInfo, cursor);
        } catch (SQLiteException e) {
            throwException(e, "SQLiteException class=" + clazz.getName());
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return null;
    }

    /**
     * 按条件查询
     * <p>
     *
     * @param clazz         要查找的对象(not null)
     * @param selection     查询条件 如："? = " + id + " AND name=?"
     * @param selectionArgs 条件参数 如：new String[]{"id", name}
     * @param orderBy       排序 如：如：id DESC
     * @param page          页数
     * @param pageNo        大于0分页,小于等于0不分页
     * @return 对象列表集合
     */
    protected <T> List<T> searchByWhere(Class<T> clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo) {
        List<T> objs = new ArrayList<>();
        if (clazz == null) {
            showErrorLog("class参数不能为空");
            return objs;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            showErrorLog("表名不能为空，请注释 class=" + clazz.getName());
            return objs;
        }
        DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(clazz);

        if (columnInfo.columns.size() == 0)
            return objs;
        String limit = null;
        if (pageNo > 0) {
            limit = page * pageNo + "," + pageNo;
        }

        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            db = database.getReadableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException class=" + clazz.getName());
            return objs;
        }
        try {
            cursor = db.query(tableName, null, selection, selectionArgs, null, null, orderBy, limit);
            if (cursor == null || cursor.getCount() == 0) {
                return objs;
            }
            cursor.moveToFirst();
            do {
                T obj = valuation(clazz, columnInfo, cursor);
                objs.add(obj);
            } while (cursor.moveToNext());
        } catch (SQLiteException e) {
            throwException(e, "SQLiteException class=" + clazz.getName());
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return objs;
    }

    /**
     * 按条件查询
     * <p>
     *
     * @param clazz         要查找的对象(not null)
     * @param selection     查询条件
     * @param selectionArgs 条件参数
     * @param orderBy       排序
     * @return 对象列表集合
     */
    protected <T> List<T> searchByWhere(Class<T> clazz, String selection, String[] selectionArgs, String orderBy) {
        return searchByWhere(clazz, selection, selectionArgs, orderBy, 0, 0);
    }

    /**
     * 查询所有
     * <p>
     *
     * @param clazz 对象
     * @return 对象列表集合
     */
    protected <T> List<T> searchAll(Class<T> clazz) {
        return searchByWhere(clazz, null, null, null, 0, 0);
    }

    /**
     * 查询所有
     * <p>
     *
     * @param clazz   对象
     * @param orderBy 排序 如：date DESC
     * @return 对象列表集合
     */
    protected <T> List<T> searchAll(Class<T> clazz, String orderBy) {
        return searchByWhere(clazz, null, null, orderBy, 0, 0);
    }


    /**
     * 插入数据
     * <p>
     *
     * @param t 如：对象List集合、对象数组、对象Map集合value值、单个对象
     * @return 是否成功
     */
    protected <T> boolean insert(T t) {
        if (t == null) {
            showErrorLog("参数不能为空");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException object=" + t.getClass().toString());
            return false;
        }
        try {
            db.beginTransaction();
            if (t instanceof List) {
                List list = (List) t;
                if (list.size() <= 0) {
                    showErrorLog("insert 插入数据为空 object=" + t.getClass().toString());
                    return false;
                }
                String tableName = DBReflectManager.getTableName(list.get(0).getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(list.get(0).getClass());
                removeAutoincrementKeyFromColumn(columnInfo.autoincrement, columnInfo);
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = contentValues(list.get(i), columnInfo);
                    db.insert(tableName, null, values);
                }

            } else if (t instanceof Object[]) {
                Object[] objects = (Object[]) t;
                if (objects.length <= 0) {
                    showErrorLog("insert 数据为空 object=" + t.getClass().toString());
                    return false;
                }
                String tableName = DBReflectManager.getTableName(objects[0].getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(objects[0].getClass());
                removeAutoincrementKeyFromColumn(columnInfo.autoincrement, columnInfo);
                for (Object obj : objects) {
                    ContentValues values = contentValues(obj, columnInfo);
                    db.insert(tableName, null, values);
                }

            } else if (t instanceof Map) {
                Map map = (Map) t;
                if (map.size() <= 0) {
                    showErrorLog("insert 数据为空 object=" + t.getClass().toString());
                    return false;
                }
                Object[] collection = map.values().toArray();
                String tableName = DBReflectManager.getTableName(collection[0].getClass());
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(collection[0].getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                removeAutoincrementKeyFromColumn(columnInfo.autoincrement, columnInfo);
                for (Object value : collection) {
                    ContentValues values = contentValues(value, columnInfo);
                    db.insert(tableName, null, values);
                }
            } else {
                String tableName = DBReflectManager.getTableName(t.getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(t.getClass());
                removeAutoincrementKeyFromColumn(columnInfo.autoincrement, columnInfo);
                ContentValues values = contentValues(t, columnInfo);
                db.insert(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            throwException(e, "insert SQLiteException object=" + t.getClass().toString());
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 插入或者更新数据(替换有值的项，没值的项不做修改)
     * <p>
     *
     * @param t 如：对象List集合、对象数组、对象Map集合value值、单个对象
     * @return 是否成功
     */
    protected <T> boolean replace(T t) {
        if (t == null) {
            showErrorLog("参数不能为空");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException object=" + t.getClass().toString());
            return false;
        }
        try {
            db.beginTransaction();
            if (t instanceof List) {
                List list = (List) t;
                if (list.size() <= 0) {
                    showErrorLog("replace 数据为空 object=" + t.getClass().toString());
                    return false;
                }
                String tableName = DBReflectManager.getTableName(list.get(0).getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(list.get(0).getClass());
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = contentValues(list.get(i), columnInfo);
                    db.replace(tableName, null, values);
                }
            } else if (t instanceof Object[]) {
                Object[] objs = (Object[]) t;
                if (objs.length <= 0) {
                    showErrorLog("replace 数据为空 object=" + t.getClass().toString());
                    return false;
                }
                String tableName = DBReflectManager.getTableName(objs[0].getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(objs[0].getClass());
                for (Object obj : objs) {
                    ContentValues values = contentValues(obj, columnInfo);
                    db.replace(tableName, null, values);
                }
            } else if (t instanceof Map) {
                Map map = (Map) t;
                if (map.size() <= 0) {
                    showErrorLog("replace 数据为空 object=" + t.getClass().toString());
                    return false;
                }
                Object[] collection = map.values().toArray();
                String tableName = DBReflectManager.getTableName(collection[0].getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(collection[0].getClass());
                for (Object value : collection) {
                    ContentValues values = contentValues(value, columnInfo);
                    db.replace(tableName, null, values);
                }
            } else {
                String tableName = DBReflectManager.getTableName(t.getClass());
                if (tableName == null) {
                    showErrorLog("表名不能为空，请注释 object=" + t.getClass().toString());
                    return false;
                }
                DBReflectManager.ColumnInfo columnInfo = DBReflectManager.getColumnInfo(t.getClass());
                ContentValues values = contentValues(t, columnInfo);
                db.replace(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            throwException(e, "replace SQLiteException object=" + t.getClass().toString());
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 删除
     * <p>
     * param clazz
     * param id
     */
    protected boolean delete(Class clazz, String id) {
        if (clazz == null || id == null) {
            showErrorLog("参数不能为空");
            return false;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            showErrorLog("表名不能为空，请注释 clazz=" + clazz.toString());
            return false;
        }
        List<String> primaryKeys = DBReflectManager.getPrimaryKeys(clazz);
        if (primaryKeys.size() == 0) {
            showErrorLog("delete error primary is null clazz=" + clazz.toString());
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException clazz=" + clazz.toString());
            return false;
        }
        try {
            db.beginTransaction();
            db.delete(tableName, primaryKeys.get(0) + "=?", new String[]{id});
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            throwException(e, "delete error SQLiteException clazz=" + clazz.toString());
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 删除
     * <p>
     * param clazz
     * param ids
     */
    protected boolean delete(Class clazz, List<String> ids) {
        if (clazz == null || ids == null || ids.size() == 0) {
            showErrorLog("参数不能为空");
            return false;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            showErrorLog("表名不能为空，请注释 clazz=" + clazz.toString());
            return false;
        }
        List<String> primaryKeys = DBReflectManager.getPrimaryKeys(clazz);
        if (primaryKeys.size() == 0) {
            showErrorLog("主键不能为空，请注释 clazz=" + clazz.toString());
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException clazz=" + clazz.toString());
            return false;
        }
        try {
            db.beginTransaction();
            for (int i = 0; i < ids.size(); i++) {
                db.delete(tableName, primaryKeys.get(0) + "=?", new String[]{ids.get(i)});
            }
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 删除对象
     * <p>
     *
     * @param t 如：对象List集合、单个对象
     * @return 是否删除成功
     */
    protected <T> boolean delete(T t) {
        if (t == null) {
            showErrorLog("参数不能为空，请注释");
            return false;
        }

        Class clazz;
        if (t instanceof List) {
            List list = (List) t;
            if (list.size() > 0) {
                clazz = list.get(0).getClass();
            } else {
                showErrorLog("delete error 集合空数据 object =" + t.getClass().toString());
                return false;
            }
        } else if (t instanceof Object[]) {
            Object[] objects = (Object[]) t;
            if (objects.length > 0) {
                clazz = objects[0].getClass();
            } else {
                showErrorLog("delete error 集合空数据 object =" + t.getClass().toString());
                return false;
            }
        } else if (t instanceof Map) {
            Map map = (Map) t;
            Iterator iterator = map.values().iterator();
            if (iterator.hasNext()) {
                clazz = iterator.next().getClass();
            } else {
                showErrorLog("delete error 集合空数据 object =" + t.getClass().toString());
                return false;
            }
        } else {
            clazz = t.getClass();
        }
        String tableName = DBReflectManager.getTableName(clazz);

        if (tableName == null) {
            showErrorLog("表名不能为空，请注释 object =" + t.getClass().toString());
            return false;
        }

        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException object =" + t.getClass().toString());
            return false;
        }
        try {
            db.beginTransaction();
            if (t instanceof List) {
                int size = ((List) t).size();
                for (int i = 0; i < size; i++) {
                    Map<String, String> primaryKeys_values = DBReflectManager.getPrimaryKeysValues(((List) t).get(i));
                    if (primaryKeys_values.size() == 0)
                        continue;
                    int index = 0;
                    StringBuilder whereCause = new StringBuilder();
                    String[] selectArg = new String[primaryKeys_values.size()];
                    Set<String> sets = primaryKeys_values.keySet();
                    for (String key : sets) {
                        if (index > 0) {
                            whereCause.append(" and ");
                        }
                        whereCause.append(key);
                        whereCause.append("=?");
                        selectArg[index] = primaryKeys_values.get(key);
                        index++;
                    }
                    db.delete(tableName, whereCause.toString(), selectArg);
                }
            } else {
                Map<String, String> primaryKeys_values = DBReflectManager.getPrimaryKeysValues(t);
                if (primaryKeys_values.size() == 0)
                    return false;
                int index = 0;
                StringBuilder whereCause = new StringBuilder();
                String[] selectArg = new String[primaryKeys_values.size()];
                Set<String> sets = primaryKeys_values.keySet();
                for (String key : sets) {
                    if (index > 0) {
                        whereCause.append(" and ");
                    }
                    whereCause.append(key);
                    whereCause.append("=?");
                    selectArg[index] = primaryKeys_values.get(key);
                    index++;
                }
                db.delete(tableName, whereCause.toString(), selectArg);
            }
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    protected boolean delete(Class clazz, String whereCause, String[] selectionArgs) {
        if (clazz == null || whereCause == null || selectionArgs == null || selectionArgs.length == 0) {
            showErrorLog("参数不能为空，请注释");
            return false;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            showErrorLog("表名不能为空，请注释 clazz =" + clazz.toString());
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "SQLiteCantOpenDatabaseException clazz =" + clazz.toString());
            return false;
        }
        try {
            db.beginTransaction();
            db.delete(tableName, whereCause, selectionArgs);
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 自定义操作
     * <p>
     * param sql
     */
    protected boolean execSQL(String sql) {
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            throwException(e, "execSQL SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            throwException(e, "execSQL SQLiteException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 移除自动增长主键
     *
     * @param isAutoincrement .
     * @param columnInfo      .
     */
    private void removeAutoincrementKeyFromColumn(boolean isAutoincrement, DBReflectManager.ColumnInfo columnInfo) {
        if (!isAutoincrement)
            return;
        for (int i = 0; i < columnInfo.primaryKeys.size(); i++) {
            String primaryKey = columnInfo.primaryKeys.get(i);
            int index = columnInfo.columns.indexOf(primaryKey);
            if (index >= 0) {
                columnInfo.columns.remove(index);
                columnInfo.fields.remove(index);
            }
        }
    }

    /**
     * 数据库存值
     * <p>
     * param obj
     * return
     */
    private ContentValues contentValues(Object obj, DBReflectManager.ColumnInfo columnInfo) {
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < columnInfo.columns.size(); i++) {
                String column = columnInfo.columns.get(i);
                Field field = columnInfo.fields.get(i);
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null)
                    continue;
                if (value instanceof Character) {
                    values.put(column, (char) value + "");
                } else if (value instanceof String) {
                    values.put(column, (String) value);
                } else if (value instanceof Byte) {
                    values.put(column, (Byte) value);
                } else if (value instanceof Short) {
                    values.put(column, (Short) value);
                } else if (value instanceof Integer) {
                    values.put(column, (Integer) value);
                } else if (value instanceof Float) {
                    values.put(column, (Float) value);
                } else if (value instanceof Double) {
                    values.put(column, (Double) value);
                } else if (value instanceof Long) {
                    values.put(column, (Long) value);
                } else if (value instanceof Boolean) {
                    values.put(column, (Boolean) value);
                } else {
                    showErrorLog("插入数据库错误提示：不支持该类型：" + field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            throwException(e, "contentValues IllegalAccessException");
        }
        return values;
    }

    /**
     * cursor赋值到对象
     * <p>
     * param clazz
     * param column_field
     * param cursor
     * return
     */
    private <T> T valuation(Class<T> clazz, DBReflectManager.ColumnInfo columnInfo, Cursor cursor) {
        T obj = null;
        Class fieldClass = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            throwException(e, "valuation InstantiationException");
        } catch (IllegalAccessException e) {
            throwException(e, "valuation IllegalAccessException");
        }

        for (int i = 0; i < columnInfo.columns.size(); i++) {
            try {
                String column = columnInfo.columns.get(i);
                Field field = columnInfo.fields.get(i);
                field.setAccessible(true);
                fieldClass = field.getType();
                if (FieldType.isString(fieldClass)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (FieldType.isInt(fieldClass)) {
                    int value = cursor.getInt(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (FieldType.isFloat(fieldClass)) {
                    float value = cursor.getFloat(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (FieldType.isDouble(fieldClass)) {
                    double value = cursor.getDouble(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (FieldType.isLong(fieldClass)) {
                    long value = cursor.getLong(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (FieldType.isDouble(fieldClass)) {
                    boolean value = cursor.getInt(cursor.getColumnIndex(column)) > 0;
                    field.set(obj, value);
                } else {
                    showErrorLog("valuation 不支持该类型：" + fieldClass);
                }
            } catch (IllegalAccessException e) {
                throwException(e, "valuation IllegalAccessException type=" + fieldClass);
            } catch (IllegalArgumentException e) {
                throwException(e, "valuation IllegalArgumentException type=" + fieldClass);
            } catch (IllegalStateException e) {
                throwException(e, "valuation IllegalStateException type=" + fieldClass);
            }
        }
        return obj;
    }

    /**
     * 警告Log
     *
     * @param error 错误信息
     */
    private void showErrorLog(String error) {
        if (!mErrors.contains(error)) {
            SQLLog.e(error);
            mErrors.add(error);
        }
    }

    /**
     * 抛出异常
     *
     * @param e     异常类型
     * @param error 错误信息
     */
    private void throwException(Exception e, String error) {
        if (!mErrors.contains(error)) {
            SQLLog.e(error);
            e.printStackTrace();
            mErrors.add(error);
        }
    }
}
