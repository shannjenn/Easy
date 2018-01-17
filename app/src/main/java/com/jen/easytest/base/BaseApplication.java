package com.jen.easytest.base;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.jen.easy.EasyMain;
import com.jen.easy.app.EasyApplication;
import com.jen.easy.http.Http;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.imp.LogCrashListener;
import com.jen.easy.sqlite.imp.DatabaseListener;
import com.jen.easytest.R;
import com.jen.easyui.image.ImageLoader;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class BaseApplication extends EasyApplication {
    /**
     * 数据库版本号
     */
    private final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        EasyMain.mLog.start();
        EasyMain.mLog.setListener(logcatCrashListener);

        EasyMain.mDBHelper.create();
        EasyMain.mDBHelper.setVersion(DB_VERSION);
        EasyMain.mDBHelper.setDatabaseListener(databaseListener);
        createTB();

        ImageLoader.getInstance().setDefaultImage(getResources().getDrawable(R.mipmap.ic_launcher));
        EasyMain.mHttp = new Http(5);
    }


    /**
     * 创建表
     */
    private void createTB() {
        if (EasyMain.mDBHelper.getVersion() == 1) {//第一版开始全部执行创建,发版后使用升级操作
            EasyLog.d("创建表------------");
//            EasyMain.mDBHelper.createTB(TablesTatus.class);

        }
    }

    /**
     * 数据库监听
     */
    private DatabaseListener databaseListener = new DatabaseListener() {
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//升级数据库
            EasyLog.d("升级数据库------------");
        }
    };


    /**
     * 异常捕获，返回true用户处理，false系统处理并抛出异常
     */
    private LogCrashListener logcatCrashListener = new LogCrashListener() {
        @Override
        public boolean onBeforeHandleException(Throwable throwable) {
            EasyLog.w("捕获到异常------------");
            Intent intent = new Intent(BaseApplication.getAppContext(), LogCrashActivity.class);
            startActivity(intent);
            return true;
        }
    };
}
