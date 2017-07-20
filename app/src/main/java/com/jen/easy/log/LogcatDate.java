package com.jen.easy.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jen
 * @Date 2017年7月13日 下午2:23:52
 * @see
 * @since 1.0.0
 */
class LogcatDate {

    static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }

    static String getDateEN() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }

}