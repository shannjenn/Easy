package com.jen.easy.bind;

import com.jen.easy.log.EasyLog;

public class BindLog {
    public static void d(String msg) {
        if (EasyLog.bindPrint)
            EasyLog.d(EasyLog.TAG_BIND, msg);
    }

    public static void i(String msg) {
        if (EasyLog.bindPrint)
            EasyLog.i(EasyLog.TAG_BIND, msg);
    }

    public static void w(String msg) {
        if (EasyLog.bindPrint)
            EasyLog.w(EasyLog.TAG_BIND, msg);
    }

    public static void e(String msg) {
        if (EasyLog.bindPrint)
            EasyLog.e(EasyLog.TAG_BIND, msg);
    }
}
