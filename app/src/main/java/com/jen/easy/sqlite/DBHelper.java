package com.jen.easy.sqlite;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.app.EasyApplication;

import java.util.List;
import java.util.Map;

public class DBHelper {
    private static DBHelper dbHelper;
    private static Database database;

    private DBHelper() {
    }

    /**
     * @return
     * @获取实例
     */
    public static DBHelper getInstance() {
        if (EasyApplication.getAppContext() == null) {
            DBLog.w("未继承EasyApplication 该功能不能使用 ");
            return null;
        }
        if (dbHelper == null) {
            dbHelper = new DBHelper();
            database = new Database(EasyApplication.getAppContext());
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
     * @param clazz 传入对象
     */
    public void createTB(Class clazz) {
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
        List<String> primaryKey = (List<String>) fields.get("primaryKey");
        Map<String, Integer> column = (Map<String, Integer>) fields.get("column");

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
            int fieldType = column.get(fieldName);
            String type = ColumnType.getFieldType(fieldType);

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
    public void deleteTB(String tableName) {
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
    public void deleteTB(Class clazz) {
        String tableName = DBReflectMan.getTableName(clazz);
        deleteTB(tableName);
    }

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz
     */
    public void rebuildTB(Class clazz) {
        deleteTB(clazz);
        createTB(clazz);
    }

    public void addColumn(String tableName, String columnName, int columnType) {
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
            db.execSQL("alter table " + tableName + " add " + columnName + ColumnType.getFieldType(columnType));
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
    public void setDatabaseListener(Database.DatabaseListener databaseListener) {

    }
}
