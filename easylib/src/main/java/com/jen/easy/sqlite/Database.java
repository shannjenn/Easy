package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jen.easy.sqlite.listener.DatabaseListener;

class Database extends SQLiteOpenHelper {
    private DatabaseListener listener;

    Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        DBLog.d("Database name=" + name + " version=" + version);
    }

    Database(Context context) {
        this(context, DB.DB_NAME, null, DB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBLog.d("Database onCreate");
        if (listener != null)
            listener.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBLog.d("Database onUpgrade oldVersion=" + oldVersion + " newVersion=" + newVersion);
        if (listener != null) {
            listener.onUpgrade(db, oldVersion, newVersion);
        }
    }

    /**
     * 增加指定表字段
     *
     * @param tableName  表名
     * @param columnName 列名
     * @param columnType 类型
     * @author Jenn 2017-4-25 下午5:21:08
     * @since 1.0.0
     */
    public void addColumnName(SQLiteDatabase db, String tableName, String columnName, String columnType) {
        try {
            db.beginTransaction();
            db.execSQL("alter table " + tableName + " add " + columnName + " " + columnType);
            db.setTransactionSuccessful();
            DBLog.d("table:" + tableName + " add column " + columnName + " SUCCESS");
        } catch (SQLException e) {
            e.printStackTrace();
            DBLog.w("Database add columnName error");
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 检测表是否存在
     *
     * @param db
     * @param tableName
     * @return
     */
    boolean checkTableExist(SQLiteDatabase db, String tableName) {
        boolean exist = false;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            exist = cursor != null;
            if (cursor != null)
                cursor.close();
            return exist;
        } catch (SQLiteException e) {
//            e.printStackTrace();
            exist = false;
        }
        return exist;
    }

    /**
     * 检测列是否存在
     *
     * @param db
     * @param tableName
     * @return
     */
    boolean checkCloumnExist(SQLiteDatabase db, String tableName, String columnName) {
        boolean exist = false;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            exist = cursor != null && cursor.getColumnIndex(columnName) != -1;
            if (cursor != null)
                cursor.close();
            return exist;
        } catch (SQLiteException e) {
//            e.printStackTrace();
        }
        return exist;
    }

    void setListener(DatabaseListener listener) {
        this.listener = listener;
    }
}
