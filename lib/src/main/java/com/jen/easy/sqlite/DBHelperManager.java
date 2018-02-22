package com.jen.easy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLibLog;
import com.jen.easy.sqlite.imp.DatabaseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class DBHelperManager {
    private final String TAG = "DBHelperManager : ";
    private Database database;

    DBHelperManager(Context context) {
        if (database == null) {
            database = new Database(context);
        }
    }

    protected boolean create() {
        return database.createDB();
    }

    /**
     * 读取数据库
     */
    protected SQLiteDatabase getReadDatabase() {
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
    protected SQLiteDatabase getWriteDatabase() {
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
        if (tableName == null) {
            EasyLibLog.w(TAG + "createTB error:tableName is null");
            return;
        }
        /*boolean existTB = database.checkTableExist(db, tableName);
        if (existTB) {
            return;
        }*/

        List<String> primaryKey = new ArrayList<>();
        Map<String, String> column_type = new HashMap<>();
        DBReflectManager.getColumnNames(clazz, primaryKey, column_type, null);

        if (column_type.size() == 0) {
            EasyLibLog.w(TAG + "createTB error:column is null");
            return;
        }

        StringBuilder primaryKeySql = new StringBuilder("");
        StringBuilder fieldSql = new StringBuilder("");

        Set<String> sets = column_type.keySet();
        for (String fieldName : sets) {
            String fieldType = column_type.get(fieldName);
            String type = Constant.FieldType.getDBColumnType(fieldType);
            if (type == null) {
                EasyLibLog.e("创建表失败，不支持该类型：" + fieldName + " 请用@EasyMouse.DB.Column(noColumn = true)注释该变量");
                return;
            }

            fieldSql.append(fieldName);
            fieldSql.append(" ");
            fieldSql.append(type);
            fieldSql.append(",");
        }

        int size = primaryKey.size();
        for (int i = 0; i < size; i++) {
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
        if (primaryKeySql.length() == 0) {
            fieldSql.deleteCharAt(fieldSql.length() - 1);
        }

        final String sql = "create table if not exists " + tableName +
                "(" +
                fieldSql.toString() +
                primaryKeySql.toString() +
                ")";

        SQLiteDatabase db = getWriteDatabase();
        try {
            db.beginTransaction();
            db.execSQL(sql);
            db.setTransactionSuccessful();
            EasyLibLog.d("创建表:" + tableName + " 列明:" + fieldSql + " 主键:" + primaryKeySql + " 成功");
        } catch (SQLiteException e) {
            EasyLibLog.e("创建表：" + tableName + " 失败  SQLiteException");
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
        SQLiteDatabase db = getWriteDatabase();
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
     * @param clazz 类
     */
    protected void deleteTB(Class clazz) {
        String tableName = DBReflectManager.getTableName(clazz);
        deleteTB(tableName);
    }

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz 类
     */
    protected void rebuildTB(Class clazz) {
        deleteTB(clazz);
        createTB(clazz);
    }

    /**
     * 增加列
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    protected void addColumn(String tableName, String columnName, String fieldType) {
        SQLiteDatabase db = getWriteDatabase();
        boolean existTB = database.checkTableExist(db, tableName);
        if (!existTB) {
            EasyLibLog.w(TAG + "table : " + tableName + " is not exist");
            return;
        }
        boolean existColumn = database.checkColumnExist(db, tableName, columnName);
        if (existColumn) {
            EasyLibLog.w(TAG + "value : " + columnName + " is exist");
            return;
        }
        String type = Constant.FieldType.getDBColumnType(fieldType);
        if (type == null) {
            EasyLibLog.e("增加列失败，不支持该类型：" + fieldType + " 请用@EasyMouse.DB.Column(noColumn = true)注释该变量");
            return;
        }
        try {
            db.beginTransaction();
            db.execSQL("alter table " + tableName + " add " + columnName + type);
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
     * @param databaseListener 数据库监听
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
