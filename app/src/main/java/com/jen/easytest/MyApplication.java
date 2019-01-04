package com.jen.easytest;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.jen.easy.http.Http;
import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easy.imageLoader.ImageLoaderConfig;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.imp.LogCrashListener;
import com.jen.easy.sqlite.imp.DatabaseListener;
import com.jen.easytest.model.ImageLoaderModel;
import com.jen.easytest.sqlite.Student;
import com.jen.easyui.base.LogCrashActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class MyApplication extends Application {
    private static MyApplication mApp;
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
//        createTB();
//
//        EasyMain.mHttp = new Http(5);

        ImageLoaderConfig config = new ImageLoaderConfig(this)
                .imgHeight(1024)
                .imgWidth(1024)
                .defaultImage(getResources().getDrawable(R.mipmap.ic_launcher))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().init(config);
    }


    /**
     * 创建表
     */
    private void createTB() {
//        if (EasyMain.mDBHelper.getVersion() == 1) {//第一版开始全部执行创建,发版后使用升级操作
//            EasyLog.d("创建表------------");
//            EasyMain.mDBHelper.createTB(Student.class);
//            EasyMain.mDBHelper.createTB(ImageLoaderModel.class);
//
//        }
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
//            EasyLog.w("捕获到异常---------------------");
//            throwable.printStackTrace();
            Intent intent = new Intent(MyApplication.getAppContext(), LogCrashActivity.class);
            startActivity(intent);
            return false;
        }
    };

    public static Application getAppContext() {
        return mApp;
    }
}
