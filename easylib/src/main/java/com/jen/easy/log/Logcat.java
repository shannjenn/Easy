package com.jen.easy.log;

import android.util.Log;

/**
 * @author Jen
 * @Date 2017年7月13日 下午2:23:52
 * @see
 * @since 1.0.0
 */
public class Logcat {
    private static String TAG = "Logcat";
    /**
     * 设置v-i级别Log是否打印
     */
    public static boolean DEBUG_LOG = true;
    /**
     * 设置w-e级别Log是否打印
     */
    public static boolean ERROR_LOG = true;
    /**
     * BindLog(默认打印)
     */
    public static boolean BIND_LOG = true;
    /**
     * HttpLog(默认打印)
     */
    public static boolean HTTP_LOG = true;
    /**
     * DBLog(默认打印)
     */
    public static boolean DB_LOG = true;

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
}
