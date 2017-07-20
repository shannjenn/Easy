package com.jen.easy.app;

import android.app.Application;

/**
 * 继承该Application
 * Created by Jen on 2017/7/20.
 */

public class EasyApplication extends Application {
    private static EasyApplication instance;

    public static Application getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
