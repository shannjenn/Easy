package com.jen.easy.log;

import android.util.Log;

/**
 * @author Jen
 * @Date 2017年7月13日 下午2:23:52
 * @see
 * @since 1.0.0
 */
public class Logcat {
    public static String TAG = "Logcat";
    /**
     * 设置v-i级别Log是否打印
     */
    private static boolean debugLog = true;
    /**
     * 设置w-e级别Log是否打印
     */
    private static boolean errorLog = true;

    public static void v(String msg) {
        if (debugLog)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (debugLog)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (debugLog)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (errorLog)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (errorLog)
            Log.e(TAG, msg);
    }
}
