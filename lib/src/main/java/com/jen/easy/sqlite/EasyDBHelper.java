package com.jen.easy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.sqlite.imp.DatabaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:18
 * 说明：数据库助手（结合注释@Easy.HTTP使用）
 */
public class EasyDBHelper extends EasyDBHelperManager {

    /**
     * 新建实例
     */
    public EasyDBHelper(Context context) {
        super(context);
    }

    /**
     * 创建数据库
     */
    @Override
    public boolean init() {
        return super.init();
    }

    /**
     * 读取数据库
     */
    @Override
    public SQLiteDatabase getReadDatabase() {
        return super.getReadDatabase();
    }

    /**
     * 写入数据库
     */
    @Override
    public SQLiteDatabase getWriteDatabase() {
        return super.getWriteDatabase();
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
     * 增加列
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param clazz  如：String.class
     */
    @Override
    public void addColumn(String tableName, String columnName, Class clazz) {
        super.addColumn(tableName, columnName, clazz);
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
