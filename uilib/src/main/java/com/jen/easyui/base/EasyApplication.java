package com.jen.easyui.base;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jen.easy.log.imp.LogCrashListener;
import com.jen.easy.sqlite.imp.DatabaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class EasyApplication extends Application {
    private final String TAG = EasyApplication.class.getSimpleName();
    private static EasyApplication mApp;
    private final int DB_VERSION = 1;//数据库版本号

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        EasyMain.init(this);
//        EasyMain.mLog.start();
//        EasyMain.mLog.setListener(logcatCrashListener);
//
//        EasyMain.mDBHelper.init();
//        EasyMain.mDBHelper.setVersion(DB_VERSION);
//        EasyMain.mDBHelper.setDatabaseListener(databaseListener);
        createTB();

//        EasyMain.mHttp = new Http(5);
    }


    /**
     * 创建表
     */
    private void createTB() {
//        if (EasyMain.mDBHelper.getVersion() == 1) {//第一版开始全部执行创建,发版后使用升级操作
//            Log.d(TAG, "创建表------------");
////            EasyMain.mDBHelper.createTB(TablesTatus.class);
//
//        }
    }

    /**
     * 数据库监听
     */
    private DatabaseListener databaseListener = new DatabaseListener() {
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//升级数据库
            Log.d(TAG, "升级数据库------------");
        }
    };


    /**
     * 异常捕获，返回true用户处理，false系统处理并抛出异常
     */
    private LogCrashListener logcatCrashListener = new LogCrashListener() {
        @Override
        public boolean onBeforeHandleException(Throwable throwable) {
//            Log.w("捕获到异常------------");
//            throwable.printStackTrace();
            Intent intent = new Intent(EasyApplication.getAppContext(), LogCrashActivity.class);
            startActivity(intent);
            return false;
        }
    };

    public static Application getAppContext() {
        return mApp;
    }

}
