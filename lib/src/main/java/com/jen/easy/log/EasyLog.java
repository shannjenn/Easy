package com.jen.easy.log;

import android.util.Log;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志输出
 */
public class EasyLog {
    private static final String TAG = "EasyLog";
    private static final int maxLength = 4000;
    /**
     * 日志是否打印
     */
    public static boolean LOGCAT_OPEN = true;
    /**
     * Lib框架日志是否打印
     */
    public static boolean EASY_LIB_LOG = true;
    /**
     * UI框架日志是否打印
     */
    public static boolean EASY_UI_LOG = true;

    public static void d(String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.d(TAG + "_" + part, msg.substring(i, i + maxLength) + " --- " + TAG + " --- ");
                    } else {
                        Log.d(TAG + "_" + part, msg.substring(i, length) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.d(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void i(String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.i(TAG + "_" + part, msg.substring(i, i + maxLength) + " --- " + TAG + " --- ");
                    } else {
                        Log.i(TAG + "_" + part, msg.substring(i, length) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.i(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void w(String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.w(TAG + "_" + part, msg.substring(i, i + maxLength) + " --- " + TAG + " --- ");
                    } else {
                        Log.w(TAG + "_" + part, msg.substring(i, length) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.w(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void e(String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.e(TAG + "_" + part, msg.substring(i, i + maxLength) + " --- " + TAG + " --- ");
                    } else {
                        Log.e(TAG + "_" + part, msg.substring(i, length) + " --- " + TAG + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(TAG, msg + " --- " + TAG + " --- ");
            }
        }
    }

    public static void d(String tag, String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.d(tag + "_" + part, msg.substring(i, i + maxLength) + " --- " + tag + " --- ");
                    } else {
                        Log.d(tag + "_" + part, msg.substring(i, length) + " --- " + tag + " --- ");
                    }
                    part++;
                }
            } else {
                Log.d(tag, msg + " --- " + tag + " --- ");
            }
        }
    }

    public static void i(String tag, String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.i(tag + "_" + part, msg.substring(i, i + maxLength) + " --- " + tag + " --- ");
                    } else {
                        Log.i(tag + "_" + part, msg.substring(i, length) + " --- " + tag + " --- ");
                    }
                    part++;
                }
            } else {
                Log.i(tag, msg + " --- " + tag + " --- ");
            }
        }
    }

    public static void w(String tag, String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.w(tag + "_" + part, msg.substring(i, i + maxLength) + " --- " + tag + " --- ");
                    } else {
                        Log.w(tag + "_" + part, msg.substring(i, length) + " --- " + tag + " --- ");
                    }
                    part++;
                }
            } else {
                Log.w(tag, msg + " --- " + tag + " --- ");
            }
        }
    }

    public static void e(String tag, String msg) {
        if (LOGCAT_OPEN) {
            if (msg != null && msg.length() > maxLength) {
                int part = 0;
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.e(tag + "_" + part, msg.substring(i, i + maxLength) + " --- " + tag + " --- ");
                    } else {
                        Log.e(tag + "_" + part, msg.substring(i, length) + " --- " + tag + " --- ");
                    }
                    part++;
                }
            } else {
                Log.e(tag, msg + " --- " + tag + " --- ");
            }
        }
    }
}
