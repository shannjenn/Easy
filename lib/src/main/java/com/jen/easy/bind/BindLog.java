package com.jen.easy.bind;

import com.jen.easy.log.EasyLog;

public class BindLog {
    private static final String tag = "BindLog";

    public static void d(String msg) {
        if (EasyLog.easyBindPrint)
            EasyLog.d(tag, msg);
    }

    public static void i(String msg) {
        if (EasyLog.easyBindPrint)
            EasyLog.i(tag, msg);
    }

    public static void w(String msg) {
        if (EasyLog.easyBindPrint)
            EasyLog.w(tag, msg);
    }

    public static void e(String msg) {
        if (EasyLog.easyBindPrint)
            EasyLog.e(tag, msg);
    }
}
