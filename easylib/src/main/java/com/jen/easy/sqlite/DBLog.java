package com.jen.easy.sqlite;

import android.util.Log;

import com.jen.easy.log.Logcat;

/**
 * Created by Jen on 2017/7/18.
 */

class DBLog {
    private static final String TAG = "DB_LOG";

    static void d(String msg) {
        if (Logcat.DB_LOG)
            Log.d(TAG, msg + "  --- " + TAG + " ---");
    }

    static void i(String msg) {
        if (Logcat.DB_LOG)
            Log.i(TAG, msg + "  --- " + TAG + " ---");
    }

    static void w(String msg) {
        if (Logcat.DB_LOG)
            Log.w(TAG, msg + "  --- " + TAG + " ---");
    }

    static void e(String msg) {
        if (Logcat.DB_LOG)
            Log.e(TAG, msg + "  --- " + TAG + " ---");
    }
}
