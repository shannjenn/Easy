package com.jen.easy.aop;

import com.jen.easy.log.EasyLog;

public class AopLog {
    private static final String tag = "EasyAOP";

    public static void d(String msg) {
        if (EasyLog.easyAOPPrint)
            EasyLog.d(tag, msg);
    }

    public static void i(String msg) {
        if (EasyLog.easyAOPPrint)
            EasyLog.i(tag, msg);
    }

    public static void w(String msg) {
        if (EasyLog.easyAOPPrint)
            EasyLog.w(tag, msg);
    }

    public static void e(String msg) {
        if (EasyLog.easyAOPPrint)
            EasyLog.e(tag, msg);
    }
}
