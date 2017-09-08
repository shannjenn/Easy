package com.jen.easy.sqlite;

import android.content.Context;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：数据库表操作类（结合注释@EasyMouse.DB使用）
 */
public class DBDao extends DBDaoManager {

    public DBDao(Context context) {
        super(context);
    }

    /**
     * 按ID查询
     *
     * @param clazz 要查找的类(如：Student.class)
     * @param id    ID
     * @return 类
     */
    @Override
    public Object searchById(Class clazz, String id) {
        return super.searchById(clazz, id);
    }

    /**
     * 按条件分页查询
     * <p>
     *
     * @param clazz         要查找的类(如：Student.class)
     * @param selection     查询条件
     * @param selectionArgs 条件参数
     * @param orderBy       排序
     * @param page          页数
     * @param pageNo        大于0分页,小于等于0不分页
     * @return 类列表
     */
    @Override
    public Object searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy, int page, int pageNo) {
        return super.searchByWhere(clazz, selection, selectionArgs, orderBy, page, pageNo);
    }

    /**
     * 按条件查询
     * <p>
     *
     * @param clazz         要查找的类(如：Student.class)
     * @param selection     查询条件
     * @param selectionArgs 条件参数
     * @param orderBy       排序
     * @return 类列表
     */
    @Override
    public Object searchByWhere(Class clazz, String selection, String[] selectionArgs, String orderBy) {
        return super.searchByWhere(clazz, selection, selectionArgs, orderBy);
    }

    /**
     * 查询所有
     * <p>
     *
     * @param clazz 要查找的类(如：Student.class)
     * @return 类列表
     */
    @Override
    public Object searchAll(Class clazz) {
        return super.searchAll(clazz);
    }

    /**
     * 查询所有并排序
     * <p>
     *
     * @param clazz   要查找的类(如：Student.class)
     * @param orderBy 排序
     * @return 类列表
     */
    @Override
    public Object searchAll(Class clazz, String orderBy) {
        return super.searchAll(clazz, orderBy);
    }

    /**
     * 插入数据
     * <p>
     *
     * @param obj 类
     * @return 是否要成功
     */
    @Override
    public boolean insert(Object obj) {
        return super.insert(obj);
    }

    /**
     * 插入或者更新数据
     * <p>
     *
     * @param obj 类
     * @return 是否要成功
     */
    @Override
    public boolean replace(Object obj) {
        return super.replace(obj);
    }

    /**
     * 按ID删除
     * <p>
     *
     * @param clazz 类(如：Student.class)
     * @param id    ID
     * @return 是否要成功
     */
    @Override
    public boolean delete(Class clazz, String id) {
        return super.delete(clazz, id);
    }

    /**
     * 按ID删除多个
     * <p>
     *
     * @param clazz 类(如：Student.class)
     * @param ids   IDS
     * @return 是否要成功
     */
    @Override
    public boolean delete(Class clazz, List<String> ids) {
        return super.delete(clazz, ids);
    }

    /**
     * 按条件删除
     *
     * @param clazz         类(如：Student.class)
     * @param whereCause    条件语句
     * @param selectionArgs 条件参数
     * @return 是否要成功
     */
    @Override
    public boolean delete(Class clazz, String whereCause, String[] selectionArgs) {
        return super.delete(clazz, whereCause, selectionArgs);
    }

    /**
     * 删除多个对象
     *
     * @param objects 对象
     * @return 是否要成功
     */
    @Override
    protected boolean delete(List<Object> objects) {
        return super.delete(objects);
    }

    /**
     * 自定义SQL语句
     *
     * @param sql 语句
     * @return 是否要成功
     */
    @Override
    public boolean execSQL(String sql) {
        return super.execSQL(sql);
    }

}
