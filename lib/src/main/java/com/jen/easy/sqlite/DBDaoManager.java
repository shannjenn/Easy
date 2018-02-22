package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.constant.FieldType;
import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

abstract class DBDaoManager {
    private final String TAG = "DBDaoManager : ";
    private Database database;

    DBDaoManager(Context context) {
        database = new Database(context);
    }


    /**
     * @param clazz 要查找的对象
     * @param id    ID
     * @return 对象
     */
    protected <T> T searchById(Class<T> clazz, String id) {
        if (clazz == null || id == null) {
            EasyLibLog.e(TAG + "searchById clazz is null or id is null");
            return null;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "searchById tableName is null");
            return null;
        }
        List<String> primaryKeys = new ArrayList<>();
        Map<String, Field> column_field = new HashMap<>();
        DBReflectManager.getColumnNames(clazz, primaryKeys, null, column_field);

        if (primaryKeys.size() == 0) {
            EasyLibLog.e(TAG + "searchById primaryKey is null");
            return null;
        }

        SQLiteDatabase db;
        try {
            db = database.getReadableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return null;
        }
        try {
            String selection = primaryKeys.get(0) + "=?";
            String[] selectionArgs = {id};
            Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            T obj = valuation(clazz, column_field, cursor);
            cursor.close();
            db.close();
            return obj;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteException");
        } finally {
            db.close();
        }
        return null;
    }

    /**
     * 按条件查询
     * <p>
     *
     * @param clazz         要查找的对象(not null)
     * @param selection     查询条件
     * @param selectionArgs 条件参数
     * @param orderBy       排序
     * @param page          页数
     * @param pageNo        大于0分页,小于等于0不分页
     * @return 对象列表集合
     */
    protected <T> List<T> searchByWhere(Class<T> clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo) {
        List<T> objs = new ArrayList<>();
        if (clazz == null) {
            EasyLibLog.e(TAG + "searchByWhere clazz is null or id is null");
            return objs;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "searchByWhere tableName is null");
            return objs;
        }
        Map<String, Field> column_field = new HashMap<>();
        DBReflectManager.getColumnNames(clazz, null, null, column_field);

        if (column_field.size() == 0)
            return objs;
        String limit = null;
        if (pageNo > 0) {
            limit = page * pageNo + "," + pageNo;
        }

        SQLiteDatabase db;
        try {
            db = database.getReadableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return objs;
        }
        try {
            Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, orderBy, limit);
            if (cursor == null || cursor.getCount() == 0) {
                return objs;
            }
            cursor.moveToFirst();
            do {
                T obj = valuation(clazz, column_field, cursor);
                objs.add(obj);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteException");
        } finally {
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
        if (t == null || t instanceof Class) {
            EasyLibLog.e(TAG + "insert obj is null");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            if (t instanceof List) {
                List list = (List) t;
                if (list.size() <= 0) {
                    EasyLibLog.e(TAG + "insert 插入数据为空");
                    return false;
                }
                String tableName = DBReflectManager.getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表名");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(list.get(0).getClass(), null, null, column_field);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ContentValues values = contentValues(list.get(i), column_field);
                    db.insert(tableName, null, values);
                }

            } else if (t instanceof Object[]) {
                Object[] objects = (Object[]) t;
                if (objects.length <= 0) {
                    EasyLibLog.e(TAG + "insert 数据为空");
                    return false;
                }
                String tableName = DBReflectManager.getTableName(objects[0].getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表名");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(objects[0].getClass(), null, null, column_field);

                for (Object obj : objects) {
                    ContentValues values = contentValues(obj, column_field);
                    db.insert(tableName, null, values);
                }

            } else if (t instanceof Map) {
                Map map = (Map) t;
                if (map.size() <= 0) {
                    EasyLibLog.e(TAG + "insert 数据为空");
                    return false;
                }
                Object[] collection = map.values().toArray();
                String tableName = DBReflectManager.getTableName(collection[0].getClass());
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(collection[0].getClass(), null, null, column_field);
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }

                for (Object value : collection) {
                    ContentValues values = contentValues(value, column_field);
                    db.insert(tableName, null, values);
                }
            } else {
                String tableName = DBReflectManager.getTableName(t.getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(t.getClass(), null, null, column_field);
                ContentValues values = contentValues(t, column_field);
                db.insert(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "insert SQLiteException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 插入或者更新数据
     * <p>
     *
     * @param t 如：对象List集合、对象数组、对象Map集合value值、单个对象
     * @return 是否成功
     */
    protected <T> boolean replace(T t) {
        if (t == null || t instanceof Class) {
            EasyLibLog.e(TAG + "replace obj is null");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            if (t instanceof List) {
                List list = (List) t;
                if (list.size() <= 0) {
                    EasyLibLog.e(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = DBReflectManager.getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(list.get(0).getClass(), null, null, column_field);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ContentValues values = contentValues(list.get(i), column_field);
                    db.replace(tableName, null, values);
                }
            } else if (t instanceof Object[]) {
                Object[] objs = (Object[]) t;
                if (objs.length <= 0) {
                    EasyLibLog.e(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = DBReflectManager.getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(objs[0].getClass(), null, null, column_field);
                for (Object obj : objs) {
                    ContentValues values = contentValues(obj, column_field);
                    db.replace(tableName, null, values);
                }
            } else if (t instanceof Map) {
                Map map = (Map) t;
                if (map.size() <= 0) {
                    EasyLibLog.e(TAG + "replace 数据为空");
                    return false;
                }
                Object[] collection = map.values().toArray();
                String tableName = DBReflectManager.getTableName(collection[0].getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(collection[0].getClass(), null, null, column_field);
                for (Object value : collection) {
                    ContentValues values = contentValues(value, column_field);
                    db.replace(tableName, null, values);
                }
            } else {
                String tableName = DBReflectManager.getTableName(t.getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Field> column_field = new HashMap<>();
                DBReflectManager.getColumnNames(t.getClass(), null, null, column_field);
                ContentValues values = contentValues(t, column_field);
                db.replace(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "replace SQLiteException");
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
            EasyLibLog.e(TAG + "delete error obj is null");
            return false;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "delete error tableName is null");
            return false;
        }
        List<String> primaryKeys = DBReflectManager.getPrimaryKeys(clazz);
        if (primaryKeys.size() == 0) {
            EasyLibLog.e(TAG + "delete error primary is null");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            db.delete(tableName, primaryKeys.get(0) + "=?", new String[]{id});
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "delete error SQLiteException");
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
            EasyLibLog.e(TAG + "delete error obj is null");
            return false;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "delete error tableName is null");
            return false;
        }
        List<String> primaryKeys = DBReflectManager.getPrimaryKeys(clazz);
        if (primaryKeys.size() == 0) {
            EasyLibLog.e(TAG + "delete error primary is null");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            int size = ids.size();
            for (int i = 0; i < size; i++) {
                db.delete(tableName, primaryKeys.get(0) + "=?", new String[]{ids.get(i)});
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "delete error SQLiteException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
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
            EasyLibLog.e(TAG + "delete obj is null");
            return false;
        }

        Class clazz;
        if (t instanceof List) {
            List list = (List) t;
            if (list.size() > 0) {
                clazz = list.get(0).getClass();
            } else {
                EasyLibLog.w(TAG + "delete error 集合空数据");
                return false;
            }
        } else if (t instanceof Object[]) {
            Object[] objects = (Object[]) t;
            if (objects.length > 0) {
                clazz = objects[0].getClass();
            } else {
                EasyLibLog.w(TAG + "delete error 集合空数据");
                return false;
            }
        } else if (t instanceof Map) {
            Map map = (Map) t;
            Iterator iterator = map.values().iterator();
            if (iterator.hasNext()) {
                clazz = iterator.next().getClass().getClass();
            } else {
                EasyLibLog.w(TAG + "delete error 集合空数据");
                return false;
            }
        } else {
            clazz = t.getClass();
        }
        String tableName = DBReflectManager.getTableName(clazz);


        if (tableName == null) {
            EasyLibLog.e(TAG + "delete tableName is null");
            return false;
        }

        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
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
                    StringBuilder whereCause = new StringBuilder("");
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
                StringBuilder whereCause = new StringBuilder("");
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
        } catch (Exception e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "delete SQLiteException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }


    protected boolean delete(Class clazz, String whereCause, String[] selectionArgs) {
        if (clazz == null || whereCause == null || selectionArgs == null || selectionArgs.length == 0) {
            EasyLibLog.e(TAG + "delete obj or selection or selectionArgs is error");
            return false;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "delete tableName is null");
            return false;
        }
        SQLiteDatabase db;
        try {
            db = database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            db.delete(tableName, whereCause, selectionArgs);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "delete SQLiteException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
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
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return false;
        }
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "execSQL SQLiteException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 数据库存值
     * <p>
     * param obj
     * return
     */
    private ContentValues contentValues(Object obj, Map<String, Field> column_field) {
        ContentValues values = new ContentValues();
        try {
            Set<String> sets = column_field.keySet();
            for (String column : sets) {
                Field field = column_field.get(column);
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
                    EasyLibLog.e(TAG + "插入数据库错误提示：不支持该类型：" + field.getName());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "contentValues IllegalAccessException");
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
    private <T> T valuation(Class<T> clazz, Map<String, Field> column_field, Cursor cursor) {
        T obj = null;
        String type = null;
        try {
            obj = clazz.newInstance();
            Set<String> sets = column_field.keySet();
            for (String column : sets) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                type = field.getGenericType().toString();

                switch (type) {
                    case FieldType.STRING: {
                        String value = cursor.getString(cursor.getColumnIndex(column));
                        field.set(obj, value);
                        break;
                    }
                    case FieldType.INTEGER: {
                        int value = cursor.getInt(cursor.getColumnIndex(column));
                        field.setInt(obj, value);
                        break;
                    }
                    case FieldType.FLOAT: {
                        float value = cursor.getFloat(cursor.getColumnIndex(column));
                        field.setFloat(obj, value);
                        break;
                    }
                    case FieldType.DOUBLE: {
                        double value = cursor.getDouble(cursor.getColumnIndex(column));
                        field.setDouble(obj, value);
                        break;
                    }
                    case FieldType.LONG: {
                        long value = cursor.getLong(cursor.getColumnIndex(column));
                        field.setLong(obj, value);
                        break;
                    }
                    case FieldType.BOOLEAN: {
                        boolean value = cursor.getInt(cursor.getColumnIndex(column)) > 0;
                        field.setBoolean(obj, value);
                        break;
                    }
                    /*case FieldType.DATE: {
                        String value = cursor.getString(cursor.getColumnIndex(column));
                        Date date = EasyUtil.mDateFormat.parser(value);
                        field.set(obj, date);
                        break;
                    }*/
                    default:
                        EasyLibLog.e(TAG + "valuation 不支持该类型：" + type);
                        break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "valuation InstantiationException setAccessible");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "valuation IllegalAccessException type=" + type);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "valuation IllegalArgumentException type=" + type);
        }
        return obj;
    }
}
