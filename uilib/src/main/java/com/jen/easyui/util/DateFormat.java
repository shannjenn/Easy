package com.jen.easyui.util;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:58
 * 说明：时间转换工具
 */

public class DateFormat extends DateFormatManager {

    /**
     * 设置时间格式
     *
     * @param dateFormat 格式(如:yyyy-MM-dd HH:mm:ss)
     */
    public void setFormat(@NonNull String dateFormat) {
        super.setFormat(dateFormat);
    }

    /**
     * @param time 时间
     * @return
     */
    public String format(long time) {
        return super.format(time);
    }

    /**
     * 时间转字符串
     *
     * @param timeStamp 时间戳
     * @return 格式化时间
     */
    public String format(String timeStamp) {
        return super.format(timeStamp);
    }

    /**
     * @param date 日期
     * @return
     */
    public String format(@NonNull Date date) {
        return super.format(date);
    }

    /**
     * @param calendar 日期
     * @return
     */
    public String format(@NonNull Calendar calendar) {
        return super.format(calendar);
    }


    /**
     * @param formatDate 如(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public Date parseToDate(String formatDate) {
        return super.parseToDate(formatDate);
    }

    /**
     * @param formatDate 如(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public long parseToMillisecond(String formatDate) {
        return super.parseToMillisecond(formatDate);
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param time 时间
     * @return 格式化时间
     */
    public String beiJingToLocal(long time) {
        return super.beiJingToLocal(time);
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param date 时间
     * @return 时间
     */
    public Date beiJingToLocal(Date date) {
        return super.beiJingToLocal(date);
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param time 时间
     * @return 时间
     */
    public String localToBeiJing(long time) {
        return localToBeiJing(time);
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param date 时间
     * @return 时间
     */
    public Date localToBeiJing(Date date) {
        return super.localToBeiJing(date);
    }
}
