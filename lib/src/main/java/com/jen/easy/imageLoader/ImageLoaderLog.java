package com.jen.easy.imageLoader;

import com.jen.easy.log.EasyLog;

public class ImageLoaderLog {
    private static final String tag = "EasyImageLoader";

    public static void d(String msg) {
        if (EasyLog.easyImageLoaderPrint)
            EasyLog.d(tag, msg);
    }

    public static void i(String msg) {
        if (EasyLog.easyImageLoaderPrint)
            EasyLog.i(tag, msg);
    }

    public static void w(String msg) {
        if (EasyLog.easyImageLoaderPrint)
            EasyLog.w(tag, msg);
    }

    public static void e(String msg) {
        if (EasyLog.easyImageLoaderPrint)
            EasyLog.e(tag, msg);
    }
}
