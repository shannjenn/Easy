package com.jen.easy.app;

import android.app.Application;

import com.jen.easy.log.EasyLog;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Application类
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
        if (instance == null)
            instance = this;
//        if (Constant.DB.PASSWORD == null)
//            Constant.DB.PASSWORD = setDBPassword();
    }

//    protected abstract String setDBPassword();
}
