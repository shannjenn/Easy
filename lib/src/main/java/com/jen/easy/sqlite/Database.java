package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.EasyListener;
import com.jen.easy.log.EasyLog;

import java.io.File;

class Database {
    private static final String name = "easy.db";
    private String path;
    private EasyListener.DB.DatabaseListener listener;

    Database(Context context) {
        path = context.getDatabasePath(name).getPath();
        File parent = new File(path).getParentFile();
        if (!parent.exists()) {
            boolean ret = parent.mkdirs();
            if (!ret)
                EasyLog.w("创建文件失败");
        }
    }

    /**
     * 创建数据库
     */
    SQLiteDatabase createDB() {
        File file = new File(path);
        if (file.exists()) {
            return null;
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        return db;
    }

    /**
     * 创建数据库
     */
    /*void createDB(String password) {
        File file = new File(path);
        if (file.exists()) {
            return;
        }
        SQLiteDatabase.openOrCreateDatabase(path, null);
        DB.PASSWORD = password;
    }*/

    /**
     * 升级数据库
     *
     * @param version
     */
    void setVersion(int version) {
        SQLiteDatabase db = getWritableDatabase();
        int oldVersion = db.getVersion();
        if (version < oldVersion) {
            EasyLog.w("升级版本不能小于当前版本：" + oldVersion);
        }
        if (oldVersion == version) {
            return;
        }
        db.setVersion(version);
        if (listener != null) {
            listener.onUpgrade(db, oldVersion, oldVersion);
        }
        db.close();
    }

    int getVersion() {
        return getReadableDatabase().getVersion();
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

    void setListener(EasyListener.DB.DatabaseListener listener) {
        this.listener = listener;
    }

}
