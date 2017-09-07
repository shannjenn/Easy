package com.jen.easy.log;

import android.util.Log;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志输出
 */
public class Logcat {
    private static final String TAG = Logcat.class.getSimpleName();
    static final int length = 4000;
    /**
     * 日志是否打印
     */
    public static boolean Logcat_open = true;
    /**
     * 框架日志是否打印
     */
    public static boolean EasyLog_open = true;

    public static void d(String msg) {
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
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
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
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
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
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
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
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


    public static void v(String tag, String msg) {
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.e(tag + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(tag + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(tag, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void d(String tag, String msg) {
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.e(tag + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(tag + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(tag, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void i(String tag, String msg) {
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.e(tag + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(tag + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(tag, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void w(String tag, String msg) {
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.e(tag + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(tag + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(tag, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void e(String tag, String msg) {
        if (Logcat_open) {
            if (msg != null && msg.length() > length) {
                int part = 1;
                for (int i = 0; i < msg.length(); i += length) {
                    if (i + length < msg.length()) {
                        Log.e(tag + "_" + part, msg.substring(i, i + length) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(tag + "_" + part, msg.substring(i, msg.length()) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(tag, msg + " --- " + TAG + " --- ");
            }
        }
    }
}
