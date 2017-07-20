package com.jen.easy.sqlite;

import android.util.Log;

/**
 * Created by Administrator on 2017/7/18.
 */

class DBLog {
    static final String TAG = DBLog.class.getSimpleName();
    /**
     * 是否打印DBLog(默认打印)
     */
    static boolean DEBUG = true;

    static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg + "  --- DBLog ---");
    }

    static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg + "  --- DBLog ---");
    }

    static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg + "  --- DBLog ---");
    }

    static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg + "  --- DBLog ---");
    }
}
