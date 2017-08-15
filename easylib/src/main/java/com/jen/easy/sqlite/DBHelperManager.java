package com.jen.easy.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.jen.easy.EasyListener;
import com.jen.easy.constant.FieldType;
import com.jen.easy.sqlite.imp.DBHelperImp;

import java.util.List;
import java.util.Map;

public class DBHelperManager implements DBHelperImp {
    private Database database;

    public DBHelperManager(Context context) {
        if (database == null) {
            database = new Database(context);
        }
    }

    @Override
    public void create() {
        database.createDB();
    }

    /*@Override
    public void create(String password) {
        database.createDB(password);
    }*/

    /**
     * 读取数据库
     *
     * @return
     */
    @Override
    public SQLiteDatabase getReadDatabse() {
        return database.getReadableDatabase();
    }

    /**
     * 写入数据库
     *
     * @return
     */
    @Override
    public SQLiteDatabase getWtriteDatabse() {
        return database.getWritableDatabase();
    }

    /**
     * 创建表
     *
     * @param clazz 传入对象
     */
    @Override
    public void createTB(Class clazz) {
        if (clazz == null) {
            DBLog.w("createTB error:classObj is null");
            return;
        }
        SQLiteDatabase db = getWtriteDatabse();
        String tableName = DBReflectManager.getTableName(clazz);
        boolean existTB = database.checkTableExist(db, tableName);
        if (existTB) {
            return;
        }

        DBLog.d("createTB");
        Map<String, Object> fields = DBReflectManager.getColumnNames(clazz);
        List<String> primaryKey = (List<String>) fields.get(DBReflectManager.PRIMARY_KEY);
        Map<String, String> column = (Map<String, String>) fields.get(DBReflectManager.COLUMN_TYPE);

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
    @Override
    public boolean deleteTB(String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            return false;
        }
        SQLiteDatabase db = getWtriteDatabse();
        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE " + tableName);
            db.setTransactionSuccessful();
            DBLog.d("delete table name : " + tableName + " SUCCESS");
            return true;
        } catch (SQLException e) {
            DBLog.w("table:" + tableName + " delete error or no exist");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    /**
     * 删除表
     *
     * @param clazz
     */
    @Override
    public void deleteTB(Class clazz) {
        String tableName = DBReflectManager.getTableName(clazz);
        deleteTB(tableName);
    }

    /**
     * 重建表(注意：该操作删除所有数据)
     *
     * @param clazz
     */
    @Override
    public void rebuildTB(Class clazz) {
        deleteTB(clazz);
        createTB(clazz);
    }

    /**
     * @param tableName  表名
     * @param columnName 列名
     * @param fieldType  FieldType
     */
    @Override
    public void addColumn(String tableName, String columnName, String fieldType) {
        SQLiteDatabase db = getWtriteDatabse();
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
    @Override
    public void setDatabaseListener(EasyListener.DB.DatabaseListener databaseListener) {
        database.setListener(databaseListener);
    }

    @Override
    public void setVersion(int version) {
        database.setVersion(version);
    }

    @Override
    public int getVersion() {
        return database.getVersion();
    }

    @Override
    public String getName() {
        return database.getName();
    }
}
