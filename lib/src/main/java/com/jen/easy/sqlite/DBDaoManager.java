package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.EasyUtil;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.R.attr.type;
import static com.jen.easy.sqlite.DBReflectManager.COLUMN_FIELD;
import static com.jen.easy.sqlite.DBReflectManager.FOREIGN_KEY;
import static com.jen.easy.sqlite.DBReflectManager.getTableName;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

abstract class DBDaoManager {
    private final String TAG = "DBDaoManager : ";
    private Database database;

    protected DBDaoManager(Context context) {
        database = new Database(context);
    }


    /**
     * @param clazz 要查找的对象
     * @param id ID
     * @return 对象
     */
    protected <T> T searchById(Class<T> clazz, String id) {
        if (clazz == null || id == null) {
            EasyLibLog.e(TAG + "searchById clazz is null or id is null");
            return null;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "searchById tableName is null");
            return null;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primaryKey = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(COLUMN_FIELD);

        if (primaryKey.size() == 0) {
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
            String selection = primaryKey.get(0) + "=?";
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
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "searchByWhere tableName is null");
            return objs;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(DBReflectManager.COLUMN_FIELD);
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
     * @param orderBy 排序
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
                List<Object> list = (List<Object>) t;
                if (list.size() <= 0) {
                    EasyLibLog.e(TAG + "insert 插入数据为空");
                    return false;
                }
                String tableName = getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(list.get(0).getClass());
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = cntentValues(list.get(i), objectMap);
                    db.insert(tableName, null, values);
                }
            } else if (t instanceof Object[]) {
                Object[] objs = (Object[]) t;
                if (objs.length <= 0) {
                    EasyLibLog.e(TAG + "insert 数据为空");
                    return false;
                }
                String tableName = getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(objs[0].getClass());
                for (int i = 0; i < objs.length; i++) {
                    ContentValues values = cntentValues(objs[i], objectMap);
                    db.insert(tableName, null, values);
                }
            } else if (t instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) t;
                if (map.size() <= 0) {
                    EasyLibLog.e(TAG + "insert 数据为空");
                    return false;
                }
                String tableName = null;
                Map<String, Object> objectMap = null;
                for (Object value : map.values()) {
                    if (tableName == null) {
                        tableName = DBReflectManager.getTableName(value.getClass());
                        objectMap = DBReflectManager.getColumnNames(value.getClass());
                    }
                    if (tableName == null) {
                        EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                        return false;
                    }
                    ContentValues values = cntentValues(value, objectMap);
                    db.insert(tableName, null, values);
                }
            } else {
                String tableName = getTableName(t.getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(t.getClass());
                ContentValues values = cntentValues(t, objectMap);
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
                List<Object> list = (List<Object>) t;
                if (list.size() <= 0) {
                    EasyLibLog.e(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(list.get(0).getClass());
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = cntentValues(list.get(i), objectMap);
                    db.replace(tableName, null, values);
                }
            } else if (t instanceof Object[]) {
                Object[] objs = (Object[]) t;
                if (objs.length <= 0) {
                    EasyLibLog.e(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(objs[0].getClass());
                for (int i = 0; i < objs.length; i++) {
                    ContentValues values = cntentValues(objs[i], objectMap);
                    db.replace(tableName, null, values);
                }
            } else if (t instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) t;
                if (map.size() <= 0) {
                    EasyLibLog.e(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = null;
                Map<String, Object> objectMap = null;
                for (Object value : map.values()) {
                    if (tableName == null) {
                        tableName = DBReflectManager.getTableName(value.getClass());
                        objectMap = DBReflectManager.getColumnNames(value.getClass());
                    }
                    if (tableName == null) {
                        EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                        return false;
                    }
                    ContentValues values = cntentValues(value, objectMap);
                    db.replace(tableName, null, values);
                }
            } else {
                String tableName = getTableName(t.getClass());
                if (tableName == null) {
                    EasyLibLog.e(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(t.getClass());
                ContentValues values = cntentValues(t, objectMap);
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
            EasyLibLog.e(TAG + "delete obj is null");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "delete tableName is null");
            return false;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        if (primarys.size() == 0) {
            EasyLibLog.e(TAG + "delete primary is null");
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
            db.delete(tableName, primarys.get(0) + "=?", new String[]{id});
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "delete SQLiteException");
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
            EasyLibLog.e(TAG + "delete obj is null");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLibLog.e(TAG + "delete tableName is null");
            return false;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        if (primarys.size() == 0) {
            EasyLibLog.e(TAG + "delete primary is null");
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
            for (int i = 0; i < ids.size(); i++) {
                db.delete(tableName, primarys.get(0) + "=?", new String[]{ids.get(i)});
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
        String tableName = null;
        if (t instanceof List && ((List) t).size() > 0) {
            getTableName(((List) t).get(0).getClass());
        } else {
            getTableName(t.getClass());
        }
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
                for (int i = 0; i < ((List) t).size(); i++) {
                    Map<String, String> primaryKeys_values = DBReflectManager.getPrimaryKeysValues(((List) t).get(i));
                    if (primaryKeys_values.size() == 0)
                        continue;
                    int index = 0;
                    StringBuffer whereCause = new StringBuffer("");
                    String[] selectArg = new String[primaryKeys_values.size()];
                    for (String key : primaryKeys_values.keySet()) {
                        if (index > 0) {
                            whereCause.append(" and ");
                        }
                        whereCause.append(key + "=?");
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
                StringBuffer whereCause = new StringBuffer("");
                String[] selectArg = new String[primaryKeys_values.size()];
                for (String key : primaryKeys_values.keySet()) {
                    if (index > 0) {
                        whereCause.append(" and ");
                    }
                    whereCause.append(key + "=?");
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
        String tableName = getTableName(clazz);
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
    private ContentValues cntentValues(Object obj, Map<String, Object> objectMap) {
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(COLUMN_FIELD);
        Map<String, String> column_foreignKey = (Map<String, String>) objectMap.get(FOREIGN_KEY);

        ContentValues values = new ContentValues();
        try {
            for (String column : column_field.keySet()) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();
                Object value = field.get(obj);
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
                } else if (value instanceof Date) {
                    values.put(column, EasyUtil.mDateFormat.format((Date) value));
                } else if (column_foreignKey.containsKey(column)) {//其他类型用外键处理（比如：对象）
                    if (type.contains(Constant.FieldType.OBJECT)) {
                        EasyLibLog.e(TAG + "cntentValues class java.lang.Object");
                    } else if (type.contains(Constant.FieldType.MAP)) {
                        EasyLibLog.e(TAG + "cntentValues Constant.FieldType.MAP");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {
                        EasyLibLog.e(TAG + "cntentValues Constant.FieldType.ARRAY");
                    } else if (value instanceof List) {
                        List<Object> list = (List<Object>) value;
                        StringBuffer foreignKeyValue = new StringBuffer("");
                        for (int i = 0; i < list.size(); i++) {
                            String foreignValue = DBReflectManager.getPrimaryKeyValue(list.get(i), column_foreignKey.get(column));
                            if (foreignValue == null)
                                break;
                            if (i == 0) {
                                foreignKeyValue.append(foreignValue);
                            } else {
                                foreignKeyValue.append(",");
                                foreignKeyValue.append(foreignValue);
                            }
                        }
                        String keyValue = foreignKeyValue.toString().trim();
                        if (keyValue.length() > 0) {
                            values.put(column, keyValue);
                        }
                    } else if (type.contains(Constant.FieldType.CLASS)) {
                        String foreignValue = DBReflectManager.getPrimaryKeyValue(value, column_foreignKey.get(column));
                        if (foreignValue != null) {
                            values.put(column, foreignValue);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "cntentValues IllegalAccessException");
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
        try {
            obj = clazz.newInstance();
            for (String column : column_field.keySet()) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();

                switch (type) {
                    case Constant.FieldType.STRING: {
                        String value = cursor.getString(cursor.getColumnIndex(column));
                        field.set(obj, value);
                        break;
                    }
                    case Constant.FieldType.INTEGER: {
                        int value = cursor.getInt(cursor.getColumnIndex(column));
                        field.setInt(obj, value);
                        break;
                    }
                    case Constant.FieldType.FLOAT: {
                        float value = cursor.getFloat(cursor.getColumnIndex(column));
                        field.setFloat(obj, value);
                        break;
                    }
                    case Constant.FieldType.DOUBLE: {
                        double value = cursor.getDouble(cursor.getColumnIndex(column));
                        field.setDouble(obj, value);
                        break;
                    }
                    case Constant.FieldType.LONG: {
                        long value = cursor.getLong(cursor.getColumnIndex(column));
                        field.setLong(obj, value);
                        break;
                    }
                    case Constant.FieldType.BOOLEAN: {
                        boolean value = cursor.getInt(cursor.getColumnIndex(column)) > 0;
                        field.setBoolean(obj, value);
                        break;
                    }
                    case Constant.FieldType.DATE: {
                        String value = cursor.getString(cursor.getColumnIndex(column));
                        Date date = EasyUtil.mDateFormat.parser(value);
                        field.set(obj, date);
                        break;
                    }
                    default:
                        EasyLibLog.e(TAG + "valuation 不支持该类型：" + type);
                        break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "valuation InstantiationException type=" + type);
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
