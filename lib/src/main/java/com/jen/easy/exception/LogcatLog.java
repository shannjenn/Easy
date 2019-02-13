package com.jen.easy.exception;

public class LogcatLog extends Log {
    private static final String tag = TAG.EasyLogcat;

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void w(String msg) {
        w(tag, msg);
    }

    public static void e(String msg) {
        e(tag, msg);
    }
}
