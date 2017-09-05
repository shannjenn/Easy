package com.jen.easy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.sqlite.imp.DatabaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:18
 * 说明：数据库助手（结合注释@EasyMouse.HTTP使用）
 */
public class DBHelper extends DBHelperManager {

    /**
     * 新建实例
     */
    public DBHelper(Context context) {
        super(context);
    }

    /**
     * 创建数据库
     */
    @Override
    public void create() {
        super.create();
    }

    /**
     * 读取数据库
     */
    @Override
    public SQLiteDatabase getReadDatabse() {
        return super.getReadDatabse();
    }

    /**
     * 写入数据库
     */
    @Override
    public SQLiteDatabase getWtriteDatabse() {
        return super.getWtriteDatabse();
    }

    /**
     * 创建表
     *
     * @param clazz 类(如：Student.class)
     */
    @Override
    public void createTB(Class clazz) {
        super.createTB(clazz);
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    @Override
    public boolean deleteTB(String tableName) {
        return super.deleteTB(tableName);
    }

    /**
     * 删除表
     *
     * @param clazz 类(如：Student.class)
     */
    @Override
    public void deleteTB(Class clazz) {
        super.deleteTB(clazz);
    }

    /**
     * 删除表然后重建表
     *
     * @param clazz 类(如：Student.class)
     */
    @Override
    public void rebuildTB(Class clazz) {
        super.rebuildTB(clazz);
    }

    /**
     * 增加行
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    @Override
    public void addColumn(String tableName, String columnName, String fieldType) {
        super.addColumn(tableName, columnName, fieldType);
    }

    /**
     * 数据库监听(可以做升级逻辑操作等)
     *
     * @param databaseListener 数据库监听
     */
    @Override
    public void setDatabaseListener(DatabaseListener databaseListener) {
        super.setDatabaseListener(databaseListener);
    }


    /**
     * 设置数据库版本（升级等）
     *
     * @param version 版本号
     */
    @Override
    public void setVersion(int version) {
        super.setVersion(version);
    }

    /**
     * 获取数据库版本
     *
     * @return 版本号
     */
    @Override
    public int getVersion() {
        return super.getVersion();
    }

    /**
     * 获取表名称
     *
     * @return 表名称
     */
    @Override
    public String getTBName(Class clazz) {
        return super.getTBName(clazz);
    }

    /**
     * 获取数据库名称
     *
     * @return 数据库名称
     */
    @Override
    public String getDBName() {
        return super.getDBName();
    }
}
