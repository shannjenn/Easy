package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.sqlite.imp.DatabaseListener;

import java.io.File;

class EasyDatabase {
    private static final String name = "easy.db";
    private String path;
    private DatabaseListener listener;

    EasyDatabase(Context context) {
        path = context.getDatabasePath(name).getPath();
        File parent = new File(path).getParentFile();
        if (!parent.exists()) {
            boolean ret = parent.mkdirs();
            if (!ret) {
                SQLLog.e("创建数据库文件夹失败");
            }
        }
    }

    /**
     * 创建数据库
     */
    boolean init() {
        File file = new File(path);
        if (file.exists()) {
            SQLLog.d("数据库存在");
            return true;
        }
        try {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
            SQLLog.d("数据库创建成功");
            db.close();
            return true;
        } catch (SQLiteException e) {
            SQLLog.e("SQLiteException 数据库创建失败:\n");
            e.printStackTrace();
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
            return;
        }
        try {
            int oldVersion = db.getVersion();
            if (version < oldVersion) {
                SQLLog.e("升级版本不能小于当前版本：" + oldVersion);
            }
            if (oldVersion == version) {
                return;
            }
            db.setVersion(version);
            if (listener != null) {
                listener.onUpgrade(db, oldVersion, version);
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    int getVersion() {
        try {
            SQLiteDatabase db = getReadableDatabase();
            int version = db.getVersion();
            db.close();
            return version;
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
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
            SQLLog.e("SQLiteException checkTableExist 表不存在:\n");
            e.printStackTrace();
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
//            SQLLog.exception(ExceptionType.SQLiteException, "checkColumnExist 列不存在");
        }
        return exist;
    }

    void setListener(DatabaseListener listener) {
        this.listener = listener;
    }

}
