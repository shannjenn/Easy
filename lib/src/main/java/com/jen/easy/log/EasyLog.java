package com.jen.easy.log;

import android.util.Log;

/**
 * @author Jen
 * @Date 2017年7月13日 下午2:23:52
 * @see
 * @since 1.0.0
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