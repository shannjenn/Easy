package com.jen.easy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;
import com.jen.easy.sqlite.imp.DatabaseListener;

import java.util.List;
import java.util.Map;

abstract class DBHelperManager {
    private final String TAG = "DBHelperManager : ";
    private Database database;

    protected DBHelperManager(Context context) {
        if (database == null) {
            database = new Database(context);
        }
    }

    protected boolean create() {
        return database.createDB();
    }

    /*@Override
    protected void create(String password) {
        database.createDB(password);
    }*/

    /**
     * 读取数据库
     */
    protected SQLiteDatabase getReadDatabse() {
        try {
            return database.getReadableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return null;
        }
    }

    /**
     * 写入数据库
     */
    protected SQLiteDatabase getWtriteDatabse() {
        try {
            return database.getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return null;
        }
    }

    /**
     * 创建表
     *
     * @param clazz 传入对象
     */
    protected void createTB(Class clazz) {
        if (clazz == null) {
            EasyLibLog.w(TAG + "createTB error:classObj is null");
            return;
        }
        String tableName = DBReflectManager.getTableName(clazz);
        /*boolean existTB = database.checkTableExist(db, tableName);
        if (existTB) {
            return;
        }*/

        Map<String, Object> fields = DBReflectManager.getColumnNames(clazz);
        List<String> primaryKey = (List<String>) fields.get(DBReflectManager.PRIMARY_KEY);
        Map<String, String> column_type = (Map<String, String>) fields.get(DBReflectManager.COLUMN_TYPE);

        if (tableName == null) {
            EasyLibLog.w(TAG + "createTB error:tableName is null");
            return;
        } else if (column_type.size() == 0) {
            EasyLibLog.w(TAG + "createTB error:column is null");
            return;
        }

        StringBuffer primaryKeySql = new StringBuffer("");
        StringBuffer fieldSql = new StringBuffer("");

        for (String fieldName : column_type.keySet()) {
            String fieldType = column_type.get(fieldName);
            String type = Constant.FieldType.getDBCoumnType(fieldType);

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

        SQLiteDatabase db = getWtriteDatabse();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            EasyLibLog.d("创建表 if not exists:" + tableName + " column:" + fieldSql.toString()
                    + " primaryKey:" + primaryKeySql + " 成功");
        } catch (SQLiteException e) {
            EasyLibLog.e("创建表：+" + tableName + " 失败  SQLiteException");
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 删除表
     *
     * @param tableName 表名
     */
    protected boolean deleteTB(String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            return false;
        }
        SQLiteDatabase db = getWtriteDatabse();
        try {
            db.beginTransaction();
            db.execSQL("deleteTB DROP TABLE " + tableName);
            db.setTransactionSuccessful();
            EasyLibLog.d("删除表: " + tableName + " 成功");
            return true;
        } catch (SQLiteException e) {
            EasyLibLog.d("删除表: " + tableName + " 失败 SQLiteException");
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }

    /**
     * 删除表
     *
     * @param clazz
     */
    protected void deleteTB(Class clazz) {
        String tableName = DBReflectManager.getTableName(clazz);
        deleteTB(tableName);
    }

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz
     */
    protected void rebuildTB(Class clazz) {
        deleteTB(clazz);
        createTB(clazz);
    }

    /**
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    protected void addColumn(String tableName, String columnName, String fieldType) {
        SQLiteDatabase db = getWtriteDatabse();
        boolean existTB = database.checkTableExist(db, tableName);
        if (!existTB) {
            EasyLibLog.w(TAG + "table : " + tableName + " is not exist");
            return;
        }
        boolean existClumn = database.checkCloumnExist(db, tableName, columnName);
        if (existClumn) {
            EasyLibLog.w(TAG + "columnName : " + columnName + " is exist");
            return;
        }
        try {
            db.beginTransaction();
            db.execSQL("alter table " + tableName + " add " + columnName + Constant.FieldType.getDBCoumnType(fieldType));
            db.setTransactionSuccessful();
            EasyLibLog.d("add table name : " + tableName + " column : " + columnName + " SUCCESS");
        } catch (SQLiteException e) {
            EasyLibLog.w(TAG + "addColumn SQLiteException");
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 数据库监听(可以做升级逻辑操作等)
     *
     * @param databaseListener
     */
    protected void setDatabaseListener(DatabaseListener databaseListener) {
        database.setListener(databaseListener);
    }

    protected void setVersion(int version) {
        database.setVersion(version);
    }

    protected int getVersion() {
        return database.getVersion();
    }

    protected String getTBName(Class clazz) {
        return DBReflectManager.getTableName(clazz);
    }

    protected String getDBName() {
        return database.getName();
    }
}
