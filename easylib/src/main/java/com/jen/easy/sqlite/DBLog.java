package com.jen.easy.sqlite;

import android.util.Log;

import com.jen.easy.log.Logcat;

/**
 * Created by Jen on 2017/7/18.
 */

class DBLog {
    private static final String TAG = DBLog.class.getSimpleName();

    static void d(String msg) {
        if (Logcat.DB_LOG)
            Log.d(TAG, msg + "  --- DBLog ---");
    }

    static void i(String msg) {
        if (Logcat.DB_LOG)
            Log.i(TAG, msg + "  --- DBLog ---");
    }

    static void w(String msg) {
        if (Logcat.DB_LOG)
            Log.w(TAG, msg + "  --- DBLog ---");
    }

    static void e(String msg) {
        if (Logcat.DB_LOG)
            Log.e(TAG, msg + "  --- DBLog ---");
    }
}
