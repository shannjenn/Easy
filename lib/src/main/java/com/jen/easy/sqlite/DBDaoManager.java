package com.jen.easy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.EasyUtil;
import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLog;
import com.jen.easy.sqlite.imp.DBDaoImp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jen.easy.sqlite.DBReflectManager.COLUMN_FIELD;
import static com.jen.easy.sqlite.DBReflectManager.getTableName;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

public class DBDaoManager implements DBDaoImp {
    private Database database;

    public DBDaoManager(Context context) {
        database = new Database(context);
    }


    /**
     * @param clazz 要查找的对象
     * @param id
     * @return
     */
    @Override
    public Object searchById(Class clazz, String id) {
        if (clazz == null || id == null) {
            EasyLog.w("clazz is null or id is null");
            return null;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w("tableName is null");
            return null;
        }
        Map<String, Object> objectMap = DBReflectManager.getFields(clazz);
        List<String> primaryKey = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(COLUMN_FIELD);

        if (primaryKey.size() == 0) {
            EasyLog.w("primaryKey is null");
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
     *
     * @param clazz         (not null)
     * @param selection     查询条件(not null)
     * @param selectionArgs 条件参数(not null)
     * @param orderBy       排序
     * @param page          页数
     * @param pageNo        大于0分页,小于等于0不分页
     * @return
     */
    @Override
    public List<Object> searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo) {
        List<Object> objs = new ArrayList<>();
        if (clazz == null) {
            EasyLog.w("clazz is null or id is null");
            return objs;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w("tableName is null");
            return objs;
        }
        Map<String, Object> objectMap = DBReflectManager.getFields(clazz);
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
     *
     * @param clazz         (not null)
     * @param selection     查询条件(not null)
     * @param selectionArgs 条件参数(not null)
     * @param orderBy       排序
     * @return
     */
    @Override
    public List<Object> searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy) {
        return searchByWhere(clazz, selection, selectionArgs, orderBy, 0, 0);
    }

    /**
     * 查询所有
     *
     * @param clazz
     * @return
     */
    @Override
    public List<Object> searchAll(Class clazz) {
        return searchByWhere(clazz, null, null, null, 0, 0);
    }

    /**
     * 查询所有
     *
     * @param clazz
     * @param orderBy
     * @return
     */
    @Override
    public List<Object> searchAll(Class clazz, String orderBy) {
        return searchByWhere(clazz, null, null, orderBy, 0, 0);
    }


    /**
     * 插入数据
     *
     * @param obj
     */
    @Override
    public boolean insert(Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.w("obj is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List<Object>) obj;
                if (list.size() <= 0) {
                    EasyLog.w("数据为空");
                    return false;
                }
                String tableName = getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLog.w("插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = cntentValues(list.get(i));
                    db.insert(tableName, null, values);
                }
            } else if (obj instanceof Object[]) {
                Object[] objs = (Object[]) obj;
                if (objs.length <= 0) {
                    EasyLog.w("数据为空");
                    return false;
                }
                String tableName = getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLog.w("插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                for (int i = 0; i < objs.length; i++) {
                    ContentValues values = cntentValues(objs[i]);
                    db.insert(tableName, null, values);
                }
            } else if (obj instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) obj;
                if (map.size() <= 0) {
                    EasyLog.w("数据为空");
                    return false;
                }
                String tableName = null;
                for (Object value : map.values()) {
                    if (tableName == null) {
                        tableName = DBReflectManager.getTableName(value.getClass());
                    }
                    if (tableName == null) {
                        EasyLog.w("插入表名为空，请检查是否已经注释表明");
                        return false;
                    }
                    ContentValues values = cntentValues(value);
                    db.insert(tableName, null, values);
                }
            } else {
                String tableName = getTableName(obj.getClass());
                if (tableName == null) {
                    EasyLog.w("插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                ContentValues values = cntentValues(obj);
                db.insert(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 插入或者更新数据
     *
     * @param obj
     */
    @Override
    public boolean replace(Object obj) {
        if (obj == null || obj instanceof Class) {
            EasyLog.w("obj is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List<Object>) obj;
                if (list.size() <= 0) {
                    EasyLog.w("数据为空");
                    return false;
                }
                String tableName = getTableName(list.get(0).getClass());
                if (tableName == null) {
                    EasyLog.w("插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                for (int i = 0; i < list.size(); i++) {
                    ContentValues values = cntentValues(list.get(i));
                    db.replace(tableName, null, values);
                }
            } else if (obj instanceof Object[]) {
                Object[] objs = (Object[]) obj;
                if (objs.length <= 0) {
                    EasyLog.w("数据为空");
                    return false;
                }
                String tableName = getTableName(objs[0].getClass());
                if (tableName == null) {
                    EasyLog.w("插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                for (int i = 0; i < objs.length; i++) {
                    ContentValues values = cntentValues(objs[i]);
                    db.replace(tableName, null, values);
                }
            } else if (obj instanceof Map) {
                Map<Object, Object> map = (Map<Object, Object>) obj;
                if (map.size() <= 0) {
                    EasyLog.w("数据为空");
                    return false;
                }
                String tableName = null;
                for (Object value : map.values()) {
                    if (tableName == null) {
                        tableName = DBReflectManager.getTableName(value.getClass());
                    }
                    if (tableName == null) {
                        EasyLog.w("插入表名为空，请检查是否已经注释表明");
                        return false;
                    }
                    ContentValues values = cntentValues(value);
                    db.replace(tableName, null, values);
                }
            } else {
                String tableName = getTableName(obj.getClass());
                if (tableName == null) {
                    EasyLog.w("插入表名为空，请检查是否已经注释表明");
                    return false;
                }
                ContentValues values = cntentValues(obj);
                db.replace(tableName, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 删除
     *
     * @param clazz
     * @param id
     */
    @Override
    public boolean delete(Class clazz, String id) {
        if (clazz == null || id == null) {
            EasyLog.w("obj is null");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w("tableName is null");
            return false;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        if (primarys.size() == 0) {
            EasyLog.w("primary is null");
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
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 删除
     *
     * @param clazz
     * @param ids
     */
    @Override
    public boolean delete(Class clazz, List<Object> ids) {
        if (clazz == null || ids == null || ids.size() == 0) {
            EasyLog.w("obj is null");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w("tableName is null");
            return false;
        }
        Map<String, Object> objectMap = DBReflectManager.getColumnNames(clazz);
        List<String> primarys = (List<String>) objectMap.get(DBReflectManager.PRIMARY_KEY);
        if (primarys.size() == 0) {
            EasyLog.w("primary is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            for (int i = 0; i < ids.size(); i++) {
                db.delete(tableName, primarys.get(0) + "=?", new String[]{ids.get(i).toString()});
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    @Override
    public boolean delete(Class clazz, String selection, String[] selectionArgs) {
        if (clazz == null || selection == null || selectionArgs == null || selectionArgs.length == 0) {
            EasyLog.w("obj or selection or selectionArgs is error");
            return false;
        }
        String tableName = getTableName(clazz);
        if (tableName == null) {
            EasyLog.w("tableName is null");
            return false;
        }
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(tableName, selection, selectionArgs);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 自定义操作
     *
     * @param sql
     */
    @Override
    public boolean execSQL(String sql) {
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 自定义操作
     *
     * @param sql
     * @param bindArgs 参数的值
     */
    @Override
    public boolean execSQL(String sql, String[] bindArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }


    /**
     * 数据库存值
     *
     * @param obj
     * @return
     */
    private ContentValues cntentValues(Object obj) {
        Map<String, Object> objectMap = DBReflectManager.getFields(obj.getClass());
        Map<String, Field> column_field = (Map<String, Field>) objectMap.get(COLUMN_FIELD);

        ContentValues values = new ContentValues();
        try {
            for (String column : column_field.keySet()) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();
                if (type.equals(Constant.FieldType.CHAR)) {
                    char value = (char) field.getChar(obj);
                    values.put(column, value + "");
                } else if (type.equals(Constant.FieldType.STRING)) {
                    String value = (String) field.get(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.BYTE)) {
                    byte value = field.getByte(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.SHORT)) {
                    short value = field.getShort(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.INTEGER)) {
                    int value = field.getInt(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.FLOAT)) {
                    float value = field.getFloat(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.DOUBLE)) {
                    double value = field.getDouble(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.LONG)) {
                    long value = field.getLong(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.BOOLEAN)) {
                    boolean value = field.getBoolean(obj);
                    values.put(column, value);
                } else if (type.equals(Constant.FieldType.DATE)) {
                    Date value = (Date) field.get(obj);
                    values.put(column, value == null ? null : EasyUtil.DATA.format(value));
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
    private Object valuation(Class clazz, Map<String, Field> column_field, Cursor cursor) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            for (String column : column_field.keySet()) {
                Field field = column_field.get(column);
                field.setAccessible(true);
                String type = field.getGenericType().toString();

                if (type.equals(Constant.FieldType.CHAR)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.STRING)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.BYTE)) {
                    int value = cursor.getInt(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.SHORT)) {
                    short value = cursor.getShort(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.INTEGER)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.FLOAT)) {
                    float value = cursor.getFloat(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.DOUBLE)) {
                    double value = cursor.getDouble(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.LONG)) {
                    long value = cursor.getLong(cursor.getColumnIndex(column));
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.BOOLEAN)) {
                    boolean value = cursor.getInt(cursor.getColumnIndex(column)) > 0;
                    field.set(obj, value);
                } else if (type.equals(Constant.FieldType.DATE)) {
                    String value = cursor.getString(cursor.getColumnIndex(column));
                    Date date = EasyUtil.DATA.parser(value);
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
