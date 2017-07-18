package com.jen.easy.sqlite;

import android.util.Log;

/**
 * Created by Administrator on 2017/7/18.
 */

class DBLog {
    public static final String TAG = DBLog.class.getSimpleName();
    /**
     * 是否打印DBLog(默认打印)
     */
    public static boolean DEBUG = true;

    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg + "  --- DBLog ---");
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg + "  --- DBLog ---");
    }

    public static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg + "  --- DBLog ---");
    }

    public static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg + "  --- DBLog ---");
    }
}
