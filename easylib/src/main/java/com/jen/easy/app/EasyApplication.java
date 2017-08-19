package com.jen.easy.app;

import android.app.Application;

import com.jen.easy.log.EasyLog;
import com.jen.easy.sqlite.DBConstant;

/**
 * 继承该Application
 * Created by Jen on 2017/7/20.
 */

public abstract class EasyApplication extends Application {
    private static EasyApplication instance;

    public static Application getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EasyLog.d("EasyApplication onCreate----");
        instance = this;
        DBConstant.PASSWORD = setDBPassword();
    }

    protected abstract String setDBPassword();
}
