package com.jen.easyui.base;

import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.log.EasyLog;
import com.jen.easy.sqlite.EasyDBHelper;
import com.jen.easy.sqlite.imp.DatabaseListener;

public class DBManager {
    private final String TAG = DBManager.class.getSimpleName();
    private final int DB_VERSION = 1;//数据库版本号

    private static DBManager manager;
    private EasyDBHelper dbHelper;

    public static DBManager getIns() {
        if (manager == null) {
            synchronized (DBManager.class) {
                if (manager == null) {
                    manager = new DBManager();
                }
            }
        }
        return manager;
    }

    private DBManager() {
        dbHelper = new EasyDBHelper(EasyApplication.getAppContext());
    }

    /**
     * 创建数据库
     */
    public synchronized void createDB() {
        dbHelper.init();
        dbHelper.setDatabaseListener(databaseListener);
        dbHelper.setVersion(DB_VERSION);
    }


    /**
     * 创建表
     */
    public synchronized void createTB() {
        if (dbHelper.getVersion() == 1) {//第一版开始全部执行创建,发版后使用升级操作
            EasyLog.d(TAG, "创建表------------");
//            dbHelper.createTB(UserTemp.class);
//            dbHelper.createTB(FundAccountInfo.class);
//            dbHelper.createTB(ClientCashSumInfo.class);
        }
    }

    /**
     * 数据库监听
     */
    private DatabaseListener databaseListener = new DatabaseListener() {
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//升级数据库
            EasyLog.d(TAG, "升级数据库------------");
        }
    };
}
