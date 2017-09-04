package com.jen.easy.log;

import android.util.Log;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：Easy框架日志输出
 */
public class EasyLog {
    private static String TAG = EasyLog.class.getSimpleName();

    public static void d(String msg) {
        if (Logcat.EasyLog_open)
            Log.d(TAG, msg + "  --- " + TAG + " ---");
    }

    public static void i(String msg) {
        if (Logcat.EasyLog_open)
            Log.i(TAG, msg + "  --- " + TAG + " ---");
    }

    public static void w(String msg) {
        if (Logcat.EasyLog_open)
            Log.w(TAG, msg + "  --- " + TAG + " ---");
    }

    public static void e(String msg) {
        if (Logcat.EasyLog_open)
            Log.e(TAG, msg + "  --- " + TAG + " ---");
    }
}