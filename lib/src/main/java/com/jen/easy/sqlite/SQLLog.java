package com.jen.easy.sqlite;

import com.jen.easy.log.EasyLog;

public class SQLLog {
    private static final String tag = "EasySQL";

    public static void d(String msg) {
        if (EasyLog.easySQLPrint)
            EasyLog.d(tag, msg);
    }

    public static void i(String msg) {
        if (EasyLog.easySQLPrint)
            EasyLog.i(tag, msg);
    }

    public static void w(String msg) {
        if (EasyLog.easySQLPrint)
            EasyLog.w(tag, msg);
    }

    public static void e(String msg) {
        if (EasyLog.easySQLPrint)
            EasyLog.e(tag, msg);
    }
}
