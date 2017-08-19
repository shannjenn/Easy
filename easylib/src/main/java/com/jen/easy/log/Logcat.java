package com.jen.easy.log;

import android.util.Log;

/**
 * @author Jen
 * @Date 2017年7月13日 下午2:23:52
 * @see
 * @since 1.0.0
 */
public class Logcat {
    private static String TAG = Logcat.class.getSimpleName();
    /**
     * 设置v-i级别Log是否打印
     */
    public static boolean DEBUG_LOG = true;
    /**
     * 设置w-e级别Log是否打印
     */
    public static boolean ERROR_LOG = true;
    /**
     * 框架日志是否打印
     */
    public static boolean EASY_LOG = true;

    public static void v(String msg) {
        if (DEBUG_LOG)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (DEBUG_LOG)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (DEBUG_LOG)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (ERROR_LOG)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (ERROR_LOG)
            Log.e(TAG, msg);
    }



    public static void v(String tag, String msg) {
        if (DEBUG_LOG)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG_LOG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG_LOG)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (ERROR_LOG)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (ERROR_LOG)
            Log.e(tag, msg);
    }
}
