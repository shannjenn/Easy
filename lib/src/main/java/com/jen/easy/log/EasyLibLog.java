package com.jen.easy.log;

import static com.jen.easy.log.EasyLog.EASY_LIB_LOG;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Easy框架日志输出
 */
public class EasyLibLog {
    private static String TAG = "EasyLibLog";

    public static void d(String msg) {
        if (EASY_LIB_LOG)
            EasyLog.d(TAG, msg);
    }

    public static void i(String msg) {
        if (EASY_LIB_LOG)
            EasyLog.i(TAG, msg);
    }

    public static void w(String msg) {
        if (EASY_LIB_LOG)
            EasyLog.w(TAG, msg);
    }

    public static void e(String msg) {
        if (EASY_LIB_LOG)
            EasyLog.e(TAG, msg);
    }
}