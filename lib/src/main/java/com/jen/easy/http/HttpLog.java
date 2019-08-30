package com.jen.easy.http;

import com.jen.easy.log.EasyLog;

public class HttpLog {
    
    public static void d(String msg) {
        if (EasyLog.httpPrint)
            EasyLog.d(EasyLog.TAG_HTTP, msg);
    }

    public static void i(String msg) {
        if (EasyLog.httpPrint)
            EasyLog.i(EasyLog.TAG_HTTP, msg);
    }

    public static void w(String msg) {
        if (EasyLog.httpPrint)
            EasyLog.w(EasyLog.TAG_HTTP, msg);
    }

    public static void e(String msg) {
        if (EasyLog.httpPrint)
            EasyLog.e(EasyLog.TAG_HTTP, msg);
    }

}
