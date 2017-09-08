package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.EasyUtil;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLog;

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
     * param clazz 要查找的对象
     * param id
     * return
     */
    protected Object searchById(Class clazz, String id) {
        if (clazz == null || id == null) {
            EasyLog.w(TAG + "searchById clazz is null or id is null");
            return null;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w(TAG + "searchById tableName is null");
            return null;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primaryKey = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(COLUMN_FIELD);

        if (primaryKey.size() == 0) {
            EasyLog.w(TAG + "searchById primaryKey is null");
            return null;
        }
        SQLiteDatabase db = database.getReadableDatabase();
        String selection = primaryKey.get(0) + "=?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        Object obj = valuation(clazz, column_field, cursor);
        cursor.close();
        db.close();
        return obj;
    }

    /**
     * 按条件查询
     * <p>
     * param clazz         (not null)
     * param selection     查询条件(not null)
     * param selectionArgs 条件参数(not null)
     * param orderBy       排序
     * param page          页数
     * param pageNo        大于0分页,小于等于0不分页
     * return
     */
    protected Object searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo) {
        List<Object> objs = new ArrayList<>();
        if (clazz == null) {
            EasyLog.w(TAG + "searchByWhere clazz is null or id is null");
            return objs;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w(TAG + "searchByWhere tableName is null");
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
        SQLiteDatabase db = database.getReadableDatabase();
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
        db.close();
        return objs;
    }

    /**
     * 按条件查询
     * <p>
     * param clazz         (not null)
     * param selection     查询条件(not null)
     * param selectionArgs 条件参数(not null)
     * param orderBy       排序
     * return
     */
    protected Object searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy) {
        return searchByWhere(clazz, selection, selectionArgs, orderBy, 0, 0);
    }

    /**
     * 查询所有
     * <p>
     * param clazz
     * return
     */
    protected Object searchAll(Class clazz) {
        return searchByWhere(clazz, null, null, null, 0, 0);
    }

    /**
     * 查询所有
     * <p>
     * param clazz
     * param orderBy
     * return
     */
    protected Object searchAll(Class clazz, String orderBy) {
        return searchByWhere(clazz, null, null, orderBy, 0, 0);
    }


    /**
     * 插入数据
     * <p>
     * param obj
     */
    protected boolean insert(Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.w(TAG + "insert obj is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List<Object>) obj;
                if (list.size() <= 0) {
                    EasyLog.w(TAG + "insert 数据为空");
                    return false;
                }
                String tableName = getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLog.w(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(list.get(0).getClass());
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = cntentValues(list.get(i), objectMap);
                    db.insert(tableName, null, values);
                }
            } else if (obj instanceof Object[]) {
                Object[] objs = (Object[]) obj;
                if (objs.length <= 0) {
                    EasyLog.w(TAG + "insert 数据为空");
                    return false;
                }
                String tableName = getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLog.w(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(objs[0].getClass());
                for (int i = 0; i < objs.length; i++) {
                    ContentValues values = cntentValues(objs[i], objectMap);
                    db.insert(tableName, null, values);
                }
            } else if (obj instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) obj;
                if (map.size() <= 0) {
                    EasyLog.w(TAG + "insert 数据为空");
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
                        EasyLog.w(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                        return false;
                    }
                    ContentValues values = cntentValues(value, objectMap);
                    db.insert(tableName, null, values);
                }
            } else {
                String tableName = getTableName(obj.getClass());
                if (tableName == null) {
                    EasyLog.w(TAG + "insert 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(obj.getClass());
                ContentValues values = cntentValues(obj, objectMap);
                db.insert(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "insert SQLException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 插入或者更新数据
     * <p>
     * param obj
     */
    protected boolean replace(Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.w(TAG + "replace obj is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List<Object>) obj;
                if (list.size() <= 0) {
                    EasyLog.w(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLog.w(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(list.get(0).getClass());
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = cntentValues(list.get(i), objectMap);
                    db.replace(tableName, null, values);
                }
            } else if (obj instanceof Object[]) {
                Object[] objs = (Object[]) obj;
                if (objs.length <= 0) {
                    EasyLog.w(TAG + "replace 数据为空");
                    return false;
                }
                String tableName = getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLog.w(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(objs[0].getClass());
                for (int i = 0; i < objs.length; i++) {
                    ContentValues values = cntentValues(objs[i], objectMap);
                    db.replace(tableName, null, values);
                }
            } else if (obj instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) obj;
                if (map.size() <= 0) {
                    EasyLog.w(TAG + "replace 数据为空");
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
                        EasyLog.w(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                        return false;
                    }
                    ContentValues values = cntentValues(value, objectMap);
                    db.replace(tableName, null, values);
                }
            } else {
                String tableName = getTableName(obj.getClass());
                if (tableName == null) {
                    EasyLog.w(TAG + "replace 插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                Map<String, Object> objectMap = DBReflectManager.getColumnNames(obj.getClass());
                ContentValues values = cntentValues(obj, objectMap);
                db.replace(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "replace SQLException");
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
            EasyLog.w(TAG + "delete obj is null");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w(TAG + "delete tableName is null");
            return false;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        if (primarys.size() == 0) {
            EasyLog.w(TAG + "delete primary is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(tableName, primarys.get(0) + "=?", new String[]{id});
            db.setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "delete SQLException");
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
            EasyLog.w(TAG + "delete obj is null");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w(TAG + "delete tableName is null");
            return false;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        if (primarys.size() == 0) {
            EasyLog.w(TAG + "delete primary is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            for (int i = 0; i < ids.size(); i++) {
                db.delete(tableName, primarys.get(0) + "=?", new String[]{ids.get(i)});
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLog.e(TAG + "delete SQLException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 删除对象
     * <p>
     * param clazz
     * param ids
     */
    protected boolean delete(List<Object> objects) {
        if (objects == null || objects.size() == 0) {
            EasyLog.w(TAG + "delete obj is null");
            return false;
        }
        String tableName = getTableName(objects.get(0).getClass());
        if (tableName == null) {
            EasyLog.w(TAG + "delete tableName is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            for (int i = 0; i < objects.size(); i++) {
                Map<String, String> primaryKeys_values = DBReflectManager.getPrimaryKeysValues(objects.get(i));
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
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLog.e(TAG + "delete SQLException");
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }


    protected boolean delete(Class clazz, String whereCause, String[] selectionArgs) {
        if (clazz == null || whereCause == null || selectionArgs == null || selectionArgs.length == 0) {
            EasyLog.w(TAG + "delete obj or selection or selectionArgs is error");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w(TAG + "delete tableName is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(tableName, whereCause, selectionArgs);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLog.e(TAG + "delete SQLException");
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
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "execSQL SQLException");
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
                    values.put(column, EasyUtil.dateFormat.format((Date) value));
                } else if (column_foreignKey.containsKey(column)) {//其他类型用外键处理（比如：对象）
                    if (type.contains(Constant.FieldType.OBJECT)) {
                        EasyLog.e(TAG + "cntentValues class java.lang.Object");
                    } else if (type.contains(Constant.FieldType.MAP)) {
                        EasyLog.e(TAG + "cntentValues Constant.FieldType.MAP");
                    } else if (type.contains(Constant.FieldType.ARRAY)) {
                        EasyLog.e(TAG + "cntentValues Constant.FieldType.ARRAY");
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
            EasyLog.e(TAG + "cntentValues IllegalAccessException");
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
    private Object valuation(Class clazz, Map<String, Field> column_field, Cursor cursor) {
        Object obj = null;
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
                        Date date = EasyUtil.dateFormat.parser(value);
                        field.set(obj, date);
                        break;
                    }
                    default:
                        EasyLog.e(TAG + "valuation 不支持该类型：" + type);
                        break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "valuation InstantiationException type=" + type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "valuation IllegalAccessException type=" + type);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "valuation IllegalArgumentException type=" + type);
        }
        return obj;
    }
}
