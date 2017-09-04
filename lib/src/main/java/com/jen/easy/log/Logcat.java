package com.jen.easy.log;

import android.util.Log;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志输出
 */
public class Logcat {
    private static String TAG = Logcat.class.getSimpleName();
    /**
     * 设置v-i级别Log是否打印
     */
    public static boolean debugLog_open = true;
    /**
     * 设置w-e级别Log是否打印
     */
    public static boolean error_log_open = true;
    /**
     * 框架日志是否打印
     */
    public static boolean EasyLog_open = true;

    public static void v(String msg) {
        if (debugLog_open)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (debugLog_open)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (debugLog_open)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (error_log_open)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (error_log_open)
            Log.e(TAG, msg);
    }



    public static void v(String tag, String msg) {
        if (debugLog_open)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (debugLog_open)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (debugLog_open)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (error_log_open)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (error_log_open)
            Log.e(tag, msg);
    }
}
