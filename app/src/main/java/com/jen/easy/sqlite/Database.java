package com.jen.easy.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

class Database extends SQLiteOpenHelper {
    private DatabaseListener listener;

    public Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        DBLog.d("Database name=" + name + " version=" + version);
    }

    public Database(Context context) {
        this(context, DB.DB_NAME, null, DB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBLog.d("Database onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBLog.d("Database onUpgrade oldVersion=" + oldVersion + " newVersion=" + newVersion);
        if (listener != null) {
            listener.onUpgrade(db, oldVersion, newVersion);
        }
        /*try {
            db.beginTransaction();
            if (oldVersion <= 1 && newVersion >= 2) {
                final String LCount = "LCount";
                boolean LCountExist = checkColumnExist(db, TB.TB_StudyProgress, LCount);
                if (!LCountExist) {
                    db.execSQL("alter table StudyProgress add LCount integer");
                    DBLog.d("增加 学习进度表 已学总数字段：LCount 成功!!");
                }

                final String RCount = "RCount";
                boolean RCountExist = checkColumnExist(db, TB.TB_StudyProgress, RCount);
                if (!RCountExist) {
                    db.execSQL("alter table StudyProgress add RCount integer");
                    DBLog.d("增加 学习进度表 已读总数字段：RCount 成功!!");
                }
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            DBLog.e("Database onUpgrade error");
        }
        db.endTransaction();*/
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
            DBLog.w("table:" + tableName + " add column " + columnName + " SUCCESS");
        } catch (SQLException e) {
            e.printStackTrace();
            DBLog.e("Database add columnName error");
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
    public boolean checkTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
        boolean exist = cursor != null;
        if (cursor != null)
            cursor.close();
        return exist;
    }

    /**
     * 检测列是否存在
     *
     * @param db
     * @param tableName
     * @return
     */
    public boolean checkCloumnExist(SQLiteDatabase db, String tableName, String columnName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
        boolean exist = cursor != null && cursor.getColumnIndex(columnName) != -1;
        if (cursor != null)
            cursor.close();
        return exist;
    }

    interface DatabaseListener {
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    public void setListener(DatabaseListener listener) {
        this.listener = listener;
    }
}
