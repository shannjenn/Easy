package com.jen.easy.sqlite.imp;

import java.util.List;

/**
 * 数据增删改查
 * Created by Jen on 2017/7/20.
 */

public interface DBDaoImp {


    /**
     * @param clazz 要查找的对象
     * @param id
     * @return
     */
    Object searchById(Class clazz, String id);

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
    List<Object> searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo);

    /**
     * 按条件查询
     *
     * @param clazz
     * @param selection     查询条件
     * @param selectionArgs 条件参数
     * @param orderBy       排序（null不进行排序）
     * @return
     */
    List<Object> searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy);

    /**
     * 插入数据
     *
     * @param obj
     */
    boolean insert(Object obj);

    /**
     * 插入或者更新数据
     *
     * @param obj
     */
    boolean replace(Object obj);


    /**
     * 删除
     *
     * @param clazz
     * @param id
     */
    boolean delete(Class clazz, String id);

    /**
     * 删除
     *
     * @param clazz
     * @param ids
     */
    boolean delete(Class clazz, List<Object> ids);

    /**
     * 按条件删除
     *
     * @param clazz
     * @param selection
     * @param selectionArgs
     */
    boolean delete(Class clazz, String selection, String[] selectionArgs);

    /**
     * 自定义操作
     *
     * @param sql
     */
    boolean execSQL(String sql);

    /**
     * 自定义操作
     *
     * @param sql
     * @param bindArgs 参数的值
     */
    boolean execSQL(String sql, String[] bindArgs);
}
