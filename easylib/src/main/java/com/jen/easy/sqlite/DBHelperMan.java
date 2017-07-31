package com.jen.easy.sqlite;

import android.app.Application;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.app.EasyApplication;
import com.jen.easy.constant.FieldType;
import com.jen.easy.sqlite.listener.DatabaseListener;

import java.util.List;
import java.util.Map;

class DBHelperMan {
    private static DBHelperMan dbHelper;
    private Database database;

    private DBHelperMan(Application AppContext) {
        if (database == null) {
            database = new Database(AppContext);
        }
    }

    /**
     * 获取实例
     *
     * @return
     */
    static DBHelperMan getInstance() {
        if (EasyApplication.getAppContext() == null) {
            DBLog.w("未继承EasyApplication 该功能不能使用 ");
            return null;
        }
        if (dbHelper == null) {
            dbHelper = new DBHelperMan(EasyApplication.getAppContext());
        }
        return dbHelper;
    }

    /**
     * 获取实例
     *
     * @param AppContext
     * @return
     */
    static DBHelperMan getInstance(Application AppContext) {
        if (AppContext == null) {
            DBLog.w("AppContext is null ");
            return null;
        }
        if (dbHelper == null) {
            dbHelper = new DBHelperMan(AppContext);
        }
        return dbHelper;
    }

    /**
     * 读取数据库
     *
     * @return
     */
    SQLiteDatabase getReadDatabse() {
        return database.getReadableDatabase();
    }

    /**
     * 写入数据库
     *
     * @return
     */
    SQLiteDatabase getWtriteDatabse() {
        return database.getWritableDatabase();
    }

    /**
     * 创建表
     *
     * @param clazz 传入对象
     */
    void createTB(Class clazz) {
        if (clazz == null) {
            DBLog.w("createTB error:classObj is null");
            return;
        }
        SQLiteDatabase db = dbHelper.getWtriteDatabse();
        String tableName = DBReflectMan.getTableName(clazz);
        boolean existTB = database.checkTableExist(db, tableName);
        if (existTB) {
            return;
        }

        DBLog.d("createTB");
        Map<String, Object> fields = DBReflectMan.getColumnNames(clazz);
        List<String> primaryKey = (List<String>) fields.get(DBReflectMan.PRIMARY_KEY);
        Map<String, String> column = (Map<String, String>) fields.get(DBReflectMan.COLUMN_TYPE);

        if (tableName == null) {
            DBLog.w("createTB error:tableName is null");
            return;
        } else if (column.size() == 0) {
            DBLog.w("createTB error:column is null");
            return;
        }

        StringBuffer primaryKeySql = new StringBuffer("");
        StringBuffer fieldSql = new StringBuffer("");

        for (String fieldName : column.keySet()) {
            String fieldType = column.get(fieldName);
            String type = FieldType.getDBCoumnType(fieldType);

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
            DBLog.w("dbHelper is null");
            return;
        }
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            DBLog.d("create table name : " + tableName + " column : " + fieldSql.toString()
                    + " primaryKey : " + primaryKeySql + " SUCCESS");
        } catch (SQLException e) {
            DBLog.w("create table:" + tableName + " error");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    void deleteTB(String tableName) {
        SQLiteDatabase db = dbHelper.getWtriteDatabse();
        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE " + tableName);
            db.setTransactionSuccessful();
            DBLog.d("delete table name : " + tableName + " SUCCESS");
        } catch (SQLException e) {
            DBLog.w("table:" + tableName + " delete error or no exist");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删除表
     *
     * @param clazz
     */
    void deleteTB(Class clazz) {
        String tableName = DBReflectMan.getTableName(clazz);
        deleteTB(tableName);
    }

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz
     */
    void rebuildTB(Class clazz) {
        deleteTB(clazz);
        createTB(clazz);
    }

    /**
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    void addColumn(String tableName, String columnName, String fieldType) {
        SQLiteDatabase db = dbHelper.getWtriteDatabse();
        boolean existTB = database.checkTableExist(db, tableName);
        if (!existTB) {
            DBLog.w("table : " + tableName + " is not exist");
            return;
        }
        boolean existClumn = database.checkCloumnExist(db, tableName, columnName);
        if (existClumn) {
            DBLog.w("columnName : " + columnName + " is exist");
            return;
        }
        try {
            db.beginTransaction();
            db.execSQL("alter table " + tableName + " add " + columnName + FieldType.getDBCoumnType(fieldType));
            db.setTransactionSuccessful();
            DBLog.d("add table name : " + tableName + " column : " + columnName + " SUCCESS");
        } catch (SQLException e) {
            DBLog.w("table:" + tableName + " delete error or no exist");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 数据库监听(可以做升级逻辑操作等)
     *
     * @param databaseListener
     */
    void setDatabaseListener(DatabaseListener databaseListener) {
        database.setListener(databaseListener);
    }
}
