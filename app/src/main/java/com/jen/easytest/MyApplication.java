package com.jen.easytest;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easy.imageLoader.ImageLoaderConfig;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.LogcatHelper;
import com.jen.easy.log.imp.LogcatListener;
import com.jen.easy.sqlite.imp.DatabaseListener;
import com.jen.easyui.base.EasyApplication;
import com.jen.easyui.base.LogCrashActivity;

import java.io.File;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class MyApplication extends EasyApplication {
    private final int DB_VERSION = 1;//数据库版本号

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
