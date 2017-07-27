package com.jen.easy.sqlite;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.app.EasyApplication;

public class DBHelper {
    private static DBHelper dbHelper;
    private DBHelperMan man;

    private DBHelper() {

    }

    /**
     * 获取实例
     *
     * @return
     */
    public static DBHelper getInstance() {
        if (dbHelper == null || dbHelper.man == null) {
            dbHelper = new DBHelper();
        }
        if (dbHelper.man == null) {
            dbHelper.man = DBHelperMan.getInstance(EasyApplication.getAppContext());
        }
        return dbHelper;
    }

    /**
     * 获取实例
     *
     * @param AppContext
     * @return
     */
    public static DBHelper getInstance(Application AppContext) {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }
        if (dbHelper.man == null) {
            dbHelper.man = DBHelperMan.getInstance(AppContext);
        }
        return dbHelper;
    }

    /**
     * 读取数据库
     *
     * @return
     */
    public SQLiteDatabase getReadDatabse() {
        return man.getReadDatabse();
    }

    /**
     * 写入数据库
     *
     * @return
     */
    public SQLiteDatabase getWtriteDatabse() {
        return man.getWtriteDatabse();
    }

    /**
     * 获取数据库名称
     */
    public String getDBName() {
        return DB.DB_NAME;
    }

    /**
     * 获取数据库版本号
     */
    public int getDBVersion() {
        return DB.DB_VERSION;
    }

    /**
     * 设置数据库版本号
     */
    public void setDBVersion(int version) {
        DB.DB_VERSION = version;
    }

    /**
     * 创建表
     *
     * @param clazz 传入对象
     */
    public void createTB(Class clazz) {
        man.createTB(clazz);
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    public void deleteTB(String tableName) {
        man.deleteTB(tableName);
    }

    /**
     * 删除表
     *
     * @param clazz
     */
    public void deleteTB(Class clazz) {
        man.deleteTB(clazz);
    }

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz
     */
    public void rebuildTB(Class clazz) {
        man.rebuildTB(clazz);
    }

    /**
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    public void addColumn(String tableName, String columnName, String fieldType) {
        man.addColumn(tableName, columnName, fieldType);
    }

    /**
     * 数据库监听(可以做升级逻辑操作等)
     *
     * @param databaseListener
     */
    public void setDatabaseListener(Database.DatabaseListener databaseListener) {
        man.setDatabaseListener(databaseListener);
    }
}
