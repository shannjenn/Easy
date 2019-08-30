package com.jen.easy.imageLoader;

import com.jen.easy.log.EasyLog;

public class ImageLoaderLog {
    
    public static void d(String msg) {
        if (EasyLog.imageLoaderPrint)
            EasyLog.d(EasyLog.TAG_IMAGE_LOADER, msg);
    }

    public static void i(String msg) {
        if (EasyLog.imageLoaderPrint)
            EasyLog.i(EasyLog.TAG_IMAGE_LOADER, msg);
    }

    public static void w(String msg) {
        if (EasyLog.imageLoaderPrint)
            EasyLog.w(EasyLog.TAG_IMAGE_LOADER, msg);
    }

    public static void e(String msg) {
        if (EasyLog.imageLoaderPrint)
            EasyLog.e(EasyLog.TAG_IMAGE_LOADER, msg);
    }
}
