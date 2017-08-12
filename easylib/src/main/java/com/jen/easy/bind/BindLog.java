package com.jen.easy.bind;

import android.util.Log;

import com.jen.easy.log.Logcat;

/**
 * Created by Jen on 2017/7/18.
 */

class BindLog {
    private static final String TAG = "BIND_LOG";

    static void d(String msg) {
        if (Logcat.BIND_LOG)
            Log.d(TAG, msg + "  --- " + TAG + " ---");
    }

    static void i(String msg) {
        if (Logcat.BIND_LOG)
            Log.i(TAG, msg + "  --- " + TAG + " ---");
    }

    static void w(String msg) {
        if (Logcat.BIND_LOG)
            Log.w(TAG, msg + "  --- " + TAG + " ---");
    }

    static void e(String msg) {
        if (Logcat.BIND_LOG)
            Log.e(TAG, msg + "  --- " + TAG + " ---");
    }
}
