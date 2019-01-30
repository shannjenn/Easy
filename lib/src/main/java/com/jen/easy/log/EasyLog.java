package com.jen.easy.log;

import android.util.Log;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志输出
 */
public class EasyLog {
    private static final String TAG = "EasyLog";
    private static final int maxLength = 2000;

    /**
     * 日志是否打印
     */
    public static boolean open = true;

    public static void d(String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.d(TAG, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.d(TAG, msg.substring(i, i + maxLength));
                    } else {
                        Log.d(TAG, msg.substring(i, length));
                    }
                }
                Log.d(TAG, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.d(TAG, msg);
            }
        }
    }

    public static void i(String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.i(TAG, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.i(TAG, msg.substring(i, i + maxLength));
                    } else {
                        Log.i(TAG, msg.substring(i, length));
                    }
                }
                Log.i(TAG, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.i(TAG, msg);
            }
        }
    }

    public static void w(String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.w(TAG, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.w(TAG, msg.substring(i, i + maxLength));
                    } else {
                        Log.w(TAG, msg.substring(i, length));
                    }
                }
                Log.w(TAG, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.w(TAG, msg);
            }
        }
    }

    public static void e(String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.e(TAG, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.e(TAG, msg.substring(i, i + maxLength));
                    } else {
                        Log.e(TAG, msg.substring(i, length));
                    }
                }
                Log.e(TAG, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.e(TAG, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.d(tag, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.d(tag, msg.substring(i, i + maxLength));
                    } else {
                        Log.d(tag, msg.substring(i, length));
                    }
                }
                Log.d(tag, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.i(tag, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.i(tag, msg.substring(i, i + maxLength));
                    } else {
                        Log.i(tag, msg.substring(i, length));
                    }
                }
                Log.i(tag, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.w(tag, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.w(tag, msg.substring(i, i + maxLength));
                    } else {
                        Log.w(tag, msg.substring(i, length));
                    }
                }
                Log.w(tag, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.w(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (open) {
            if (msg != null && msg.length() > maxLength) {
                Log.e(tag, "超长Log打印：开始 ------------------------------ ");
                int length = msg.length();
                for (int i = 0; i < length; i += maxLength) {
                    if (i + maxLength < length) {
                        Log.e(tag, msg.substring(i, i + maxLength));
                    } else {
                        Log.e(tag, msg.substring(i, length));
                    }
                }
                Log.e(tag, "超长Log打印：结束 ------------------------------ ");
            } else {
                Log.e(tag, msg);
            }
        }
    }
}
