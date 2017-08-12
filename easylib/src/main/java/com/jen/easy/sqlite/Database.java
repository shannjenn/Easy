package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.EasyL;

class Database {
    private int version = 1;
    private final String name = "easy.db";

    private String path;
    private EasyL.DB.DatabaseListener listener;

    Database(Context context) {
        path = context.getDatabasePath(name).getPath();
    }

    /**
     * 创建数据库
     */
    void createDB() {
        SQLiteDatabase.openOrCreateDatabase(path, null);
    }

    /**
     * 升级数据库
     *
     * @param version
     */
    void setVersion(int version) {
        SQLiteDatabase db = getWritableDatabase();
        int oldVersion = db.getVersion();
        if (version < oldVersion) {
            DBLog.w("升级版本不能小于当前版本：" + oldVersion);
        }
        this.version = version;
        if (oldVersion == this.version) {
            return;
        }
        db.setVersion(version);
        if (listener != null) {
            listener.onUpgrade(db, oldVersion, oldVersion);
        }
    }

    int getVersion() {
        return version;
    }

    String getName() {
        return name;
    }

    SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
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

    void setListener(EasyL.DB.DatabaseListener listener) {
        this.listener = listener;
    }
}
