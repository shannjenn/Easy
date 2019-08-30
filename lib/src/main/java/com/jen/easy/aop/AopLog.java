package com.jen.easy.aop;

import com.jen.easy.log.EasyLog;

public class AopLog {

    public static void d(String msg) {
        if (EasyLog.AOPPrint)
            EasyLog.d(EasyLog.TAG_AOP, msg);
    }

    public static void i(String msg) {
        if (EasyLog.AOPPrint)
            EasyLog.i(EasyLog.TAG_AOP, msg);
    }

    public static void w(String msg) {
        if (EasyLog.AOPPrint)
            EasyLog.w(EasyLog.TAG_AOP, msg);
    }

    public static void e(String msg) {
        if (EasyLog.AOPPrint)
            EasyLog.e(EasyLog.TAG_AOP, msg);
    }
}
