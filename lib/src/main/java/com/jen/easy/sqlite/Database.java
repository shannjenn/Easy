package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.jen.easy.log.EasyLog;
import com.jen.easy.sqlite.imp.DatabaseListener;

import java.io.File;

class Database {
    private final String TAG = Database.class.getSimpleName() + " : ";
    private static final String name = "easy.db";
    private String path;
    private DatabaseListener listener;

    Database(Context context) {
        path = context.getDatabasePath(name).getPath();
        File parent = new File(path).getParentFile();
        if (!parent.exists()) {
            boolean ret = parent.mkdirs();
            if (!ret) {
                EasyLog.w(TAG + "创建数据库文件夹失败");
            }
        }
    }

    /**
     * 创建数据库
     */
    boolean createDB() {
        File file = new File(path);
        if (file.exists()) {
            EasyLog.i(TAG + "数据库已经存在");
            return true;
        }
        try {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "数据库创建失败");
        }
        return false;
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
        try {
            SQLiteDatabase db = getWritableDatabase();
            int oldVersion = db.getVersion();
            if (version < oldVersion) {
                EasyLog.w(TAG + "升级版本不能小于当前版本：" + oldVersion);
            }
            if (oldVersion == version) {
                return;
            }
            db.setVersion(version);
            if (listener != null) {
                listener.onUpgrade(db, oldVersion, oldVersion);
            }
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
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
