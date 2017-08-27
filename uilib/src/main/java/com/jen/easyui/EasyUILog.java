package com.jen.easyui;

import android.util.Log;

/**
 * Created by Jen on 2017/8/25.
 */

public class EasyUILog {
    private static String TAG = EasyUILog.class.getSimpleName();
    public static final boolean EASY_UI_LOG = true;

    public static void d(String msg) {
        if (EASY_UI_LOG)
            Log.d(TAG, msg + "  --- " + TAG + " ---");
    }

    public static void i(String msg) {
        if (EASY_UI_LOG)
            Log.i(TAG, msg + "  --- " + TAG + " ---");
    }

    public static void w(String msg) {
        if (EASY_UI_LOG)
            Log.w(TAG, msg + "  --- " + TAG + " ---");
    }

    public static void e(String msg) {
        if (EASY_UI_LOG)
            Log.e(TAG, msg + "  --- " + TAG + " ---");
    }
}
