package com.jen.easy.http;

import com.jen.easy.log.EasyLog;

public class HttpLog {
    private static final String tag = "EasyHttp";

    public static void d(String msg) {
        if (EasyLog.easyHttpPrint)
            EasyLog.d(tag, msg);
    }

    public static void i(String msg) {
        if (EasyLog.easyHttpPrint)
            EasyLog.i(tag, msg);
    }

    public static void w(String msg) {
        if (EasyLog.easyHttpPrint)
            EasyLog.w(tag, msg);
    }

    public static void e(String msg) {
        if (EasyLog.easyHttpPrint)
            EasyLog.e(tag, msg);
    }

}
