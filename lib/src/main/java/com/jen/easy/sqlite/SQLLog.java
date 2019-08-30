package com.jen.easy.sqlite;

import com.jen.easy.log.EasyLog;

public class SQLLog {

    public static void d(String msg) {
        if (EasyLog.SQLPrint)
            EasyLog.d(EasyLog.TAG_SQL, msg);
    }

    public static void i(String msg) {
        if (EasyLog.SQLPrint)
            EasyLog.i(EasyLog.TAG_SQL, msg);
    }

    public static void w(String msg) {
        if (EasyLog.SQLPrint)
            EasyLog.w(EasyLog.TAG_SQL, msg);
    }

    public static void e(String msg) {
        if (EasyLog.SQLPrint)
            EasyLog.e(EasyLog.TAG_SQL, msg);
    }
}
