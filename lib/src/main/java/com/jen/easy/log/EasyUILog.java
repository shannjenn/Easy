package com.jen.easy.log;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public class EasyUILog {
    private static final String TAG = "EasyUILog";

    public static void d(String msg) {
        if (EasyLog.EASY_UI_LOG)
            EasyLog.d(TAG, msg);
    }

    public static void i(String msg) {
        if (EasyLog.EASY_UI_LOG)
            EasyLog.i(TAG, msg);
    }

    public static void w(String msg) {
        if (EasyLog.EASY_UI_LOG)
            EasyLog.w(TAG, msg);
    }

    public static void e(String msg) {
        if (EasyLog.EASY_UI_LOG)
            EasyLog.e(TAG, msg);
    }
}
