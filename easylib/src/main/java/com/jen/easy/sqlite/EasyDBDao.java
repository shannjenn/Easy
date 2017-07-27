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

public abstract class EasyDBDao {


    /**
     * @param clazz 要查找的对象
     * @param id
     * @return
     */
    public static Object searchById(Class clazz, String id) {
        return EasyDBDaoMan.searchById(clazz, id);
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
        return EasyDBDaoMan.searchByWhere(clazz, selection, selectionArgs, orderBy, page, pageNo);
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
        return EasyDBDaoMan.searchByWhere(clazz, selection, selectionArgs, orderBy, 0, 0);
    }


    /**
     * 插入数据
     *
     * @param obj
     */
    public static void insert(Object obj) {
        EasyDBDaoMan.insert(obj);
    }

    /**
     * 插入或者更新数据
     *
     * @param obj
     */
    public static void replace(Object obj) {
        EasyDBDaoMan.replace(obj);
    }


    /**
     * 删除
     *
     * @param clazz
     * @param id
     */
    public static void delete(Class clazz, String id) {
        EasyDBDaoMan.delete(clazz, id);
    }

    /**
     * 按条件删除
     *
     * @param clazz
     * @param selection
     * @param selectionArgs
     */
    public static void delete(Class clazz, String selection, String[] selectionArgs) {
        EasyDBDaoMan.delete(clazz, selection, selectionArgs);
    }

    /**
     * 自定义操作
     *
     * @param sql
     */
    public static void execSQL(String sql) {
        EasyDBDaoMan.execSQL(sql);
    }

    /**
     * 自定义操作
     *
     * @param sql
     * @param bindArgs 参数的值
     */
    public static void execSQL(String sql, String[] bindArgs) {
        EasyDBDaoMan.execSQL(sql, bindArgs);
    }
}
