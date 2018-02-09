package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.log.EasyLibLog;
import com.jen.easy.sqlite.imp.DatabaseListener;

import java.io.File;

class Database {
    private final String TAG = "Database : ";
    private static final String name = "easy.db";
    private String path;
    private DatabaseListener listener;

    Database(Context context) {
        path = context.getDatabasePath(name).getPath();
        File parent = new File(path).getParentFile();
        if (!parent.exists()) {
            boolean ret = parent.mkdirs();
            if (!ret) {
                EasyLibLog.w(TAG + "创建数据库文件夹失败");
            }
        }
    }

    /**
     * 创建数据库
     */
    boolean createDB() {
        File file = new File(path);
        if (file.exists()) {
            EasyLibLog.i(TAG + "数据库已经存在");
            return true;
        }
        try {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "数据库创建失败");
        }
        return false;
    }

    /**
     * 升级数据库
     *
     * @param version 版本号
     */
    void setVersion(int version) {
        SQLiteDatabase db;
        try {
            db = getWritableDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return;
        }
        try {
            int oldVersion = db.getVersion();
            if (version < oldVersion) {
                EasyLibLog.w(TAG + "升级版本不能小于当前版本：" + oldVersion);
            }
            if (oldVersion == version) {
                return;
            }
            db.setVersion(version);
            if (listener != null) {
                listener.onUpgrade(db, oldVersion, oldVersion);
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteException");
        } finally {
            db.close();
        }
    }

    int getVersion() {
        try {
            return getReadableDatabase().getVersion();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
            EasyLibLog.e(TAG + "SQLiteCantOpenDatabaseException");
            return -1;
        }
    }

    String getName() {
        return name;
    }

    SQLiteDatabase getReadableDatabase() throws SQLiteCantOpenDatabaseException {
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    SQLiteDatabase getWritableDatabase() throws SQLiteCantOpenDatabaseException {
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * 检测表是否存在
     *
     * @param db        数据库
     * @param tableName 表名
     * @return 是否存在
     */
    boolean checkTableExist(SQLiteDatabase db, String tableName) {
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            if (cursor != null) {
                cursor.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLiteException e) {
//            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检测列是否存在
     *
     * @param db        数据库
     * @param tableName 表名
     * @return 是否存在
     */
    boolean checkColumnExist(SQLiteDatabase db, String tableName, String columnName) {
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
