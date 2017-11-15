package com.jen.easy.log;

import android.util.Log;

import static com.jen.easy.log.EasyLog.length;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public class EasyUILog {
    private static final String TAG = "EasyUILog";

    public static void d(String msg) {
        if (EasyLog.EASY_UI_LOG) {
            if (msg != null && msg.length() > length) {
                int part = 0;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.d(TAG + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.d(TAG + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.d(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void i(String msg) {
        if (EasyLog.EASY_UI_LOG) {
            if (msg != null && msg.length() > length) {
                int part = 0;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.i(TAG + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.i(TAG + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.i(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void w(String msg) {
        if (EasyLog.EASY_UI_LOG) {
            if (msg != null && msg.length() > length) {
                int part = 0;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.w(TAG + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.w(TAG + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.w(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void e(String msg) {
        if (EasyLog.EASY_UI_LOG) {
            if (msg != null && msg.length() > length) {
                int part = 0;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.e(TAG + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(TAG + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }
}
