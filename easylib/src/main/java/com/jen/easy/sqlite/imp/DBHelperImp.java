package com.jen.easy.sqlite.imp;

import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.EasyListener;

public interface DBHelperImp {

    /**
     * 创建数据库
     */
    void create();

    /**
     * 创建加密数据库
     * @return
     */
    void create(String password);

    /**
     * 读取数据库
     *
     * @return
     */
    SQLiteDatabase getReadDatabse();

    /**
     * 写入数据库
     *
     * @return
     */
    SQLiteDatabase getWtriteDatabse();

    /**
     * 创建表
     *
     * @param clazz 传入对象
     */
    void createTB(Class clazz);

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    boolean deleteTB(String tableName);

    /**
     * 删除表
     *
     * @param clazz
     */
    void deleteTB(Class clazz);

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz
     */
    void rebuildTB(Class clazz);

    /**
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    void addColumn(String tableName, String columnName, String fieldType);

    /**
     * 数据库监听(可以做升级逻辑操作等)
     *
     * @param databaseListener
     */
    void setDatabaseListener(EasyListener.DB.DatabaseListener databaseListener);

    /**
     * 升级数据库
     *
     * @param version
     */
    void setVersion(int version);

    /**
     * 获取数据库版本
     *
     * @return
     */
    int getVersion();

    /**
     * 获取数据库名称
     *
     * @return
     */
    String getName();
}
