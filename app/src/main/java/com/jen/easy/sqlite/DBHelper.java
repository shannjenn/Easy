package com.jen.easy.sqlite;

import android.app.Application;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.sqlite.imp.EasyColumn;

import java.util.List;
import java.util.Map;

public class DBHelper {
    private static DBHelper dbHelper;
    private static Database database;

    private DBHelper() {

    }

    /**
     * 获取实例
     *
     * @return
     */
    public static DBHelper getInstance(Application appContext) {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
            database = new Database(appContext);
        }
        return dbHelper;
    }

    /**
     * 读取数据库
     *
     * @return
     */
    public SQLiteDatabase getReadDatabse() {
        return database.getReadableDatabase();
    }

    /**
     * 写入数据库
     *
     * @return
     */
    public SQLiteDatabase getWtriteDatabse() {
        return database.getWritableDatabase();
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
     * @param classObj 传入对象
     */
    public void createTB(Class<?> classObj) {
        if (classObj == null) {
            DBLog.e("createTB error:classObj is null");
            return;
        }
        DBLog.d("createTB");
        String tableName = DBMan.getTableName(classObj);
        Map<String, Object> fields = DBMan.getFieldNames(classObj);
        List<String> primaryKey = (List<String>) fields.get("primaryKey");
        Map<String, Integer> column = (Map<String, Integer>) fields.get("column");

        if (tableName == null) {
            DBLog.e("createTB error:tableName is null");
            return;
        } else if (column.size() == 0) {
            DBLog.e("createTB error:column is null");
            return;
        }

        StringBuffer primaryKeySql = new StringBuffer("");
        StringBuffer fieldSql = new StringBuffer("");

        for (String fieldName : column.keySet()) {
            int fieldType = column.get(fieldName);
            String type = getFieldType(fieldType);

            fieldSql.append(fieldName);
            fieldSql.append(" ");
            fieldSql.append(type);
            fieldSql.append(",");
        }

        for (int i = 0; i < primaryKey.size(); i++) {
            if (i == 0) {
                primaryKeySql.append("primary key (");
                primaryKeySql.append(primaryKey.get(i));
            }
            if (i > 0) {
                primaryKeySql.append(",");
                primaryKeySql.append(primaryKey.get(i));
            }
            if (i + 1 == primaryKey.size()) {
                primaryKeySql.append(")");
            }
        }

        final String sql = "create table if not exists " + tableName +
                "(" +
                fieldSql.toString() +
                primaryKeySql.toString() +
                ")";

        if (dbHelper == null) {
            DBLog.d("dbHelper is null");
            return;
        }
        SQLiteDatabase db = dbHelper.getWtriteDatabse();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            DBLog.d("create table name : " + tableName + " column : " + fieldSql.toString()
                    + " primaryKey : " + primaryKeySql + " SUCCESS");
        } catch (SQLException e) {
            DBLog.d("create table:" + tableName + " error");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 获取字段类型
     *
     * @param fieldType
     * @return
     */
    public String getFieldType(int fieldType) {
        String type = "TEXT";
        switch (fieldType) {
            case EasyColumn.TYPE0:
                type = "TEXT";
                break;
            case EasyColumn.TYPE1:
                type = "INTEEGER";
                break;
            case EasyColumn.TYPE2:
                type = "REAL";
                break;
            case EasyColumn.TYPE3:
                type = "NULL";
                break;
            case EasyColumn.TYPE4:
                type = "NULL";
                break;
        }
        return type;
    }

}
