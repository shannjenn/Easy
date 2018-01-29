package com.jen.easyui.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:58
 * 说明：时间转换工具
 */

public class DataFormat extends DataFormatManager {

    /**
     * 获取时间格式
     *
     * @return 实例
     */
    @Override
    public SimpleDateFormat getFormat() {
        return super.getFormat();
    }

    /**
     * 时间转字符串
     *
     * @param date 时间
     * @return 格式后时间
     */
    @Override
    public String format(Date date) {
        return super.format(date);
    }

    /**
     * 时间格式化
     *
     * @param str 时间
     * @return 格式后时间
     */
    @Override
    public String format(String str) {
        return super.format(str);
    }

    /**
     * 字符串转时间
     *
     * @param dateStr 时间
     * @return 时间
     */
    @Override
    public Date parser(String dateStr) {
        return super.parser(dateStr);
    }

    /**
     * 获取时间戳
     *
     * @param dateStr 时间
     * @return 时间戳
     */
    @Override
    public long getTime(String dateStr) {
        return super.getTime(dateStr);
    }

    /**
     * 北京时间转本地时间
     *
     * @param time 北京时间
     * @return 本地时间
     */
    @Override
    public String BJ2Loacl(String time) {
        return super.BJ2Loacl(time);
    }

    /**
     * 北京时间转本地时间
     *
     * @param date 北京时间
     * @return 本地时间
     */
    @Override
    public Date BJ2Loacl(Date date) {
        return super.BJ2Loacl(date);
    }

    /**
     * 本地时间转北京时间
     *
     * @param time 本地时间
     * @return 北京时间
     */
    @Override
    public String locad2BJ(String time) {
        return super.locad2BJ(time);
    }

    /**
     * 本地时间转北京时间
     *
     * @param date 本地时间
     * @return 北京时间
     */
    @Override
    public Date locad2BJ(Date date) {
        return super.locad2BJ(date);
    }


    /**
     * 设置默认格式
     *
     * @param dautformat 格式
     */
    @Override
    public void setDefault(String dautformat) {
        super.setDefault(dautformat);
    }
}
