package com.jen.easy.util.imp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jen on 2017/7/20.
 */

public interface DataFormatImp {

    /**
     * 获取时间格式
     *
     * @return
     */
    SimpleDateFormat getFormat();

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    String format(Date date);


    /**
     * 时间格式转换
     *
     * @param str
     * @return
     */
    String format(String str);

    /**
     * 时间转字符串
     *
     * @param dateStr
     * @return
     */
    Date parser(String dateStr);

    /**
     * 获取时间
     *
     * @param dateStr
     * @return
     */
    long getTime(String dateStr);


    /**
     * 设置时间格式
     *
     * @param dautformat
     */
    void setDefault(String dautformat);

    /**
     * 北京时间转当地时间
     *
     * @param time
     * @return
     */
    String BJ2Loacl(String time);

    /**
     * 北京时间转当地时间
     *
     * @param date
     * @return
     */
    Date BJ2Loacl(Date date);

    /**
     * 当地时间转北京时间
     *
     * @param time
     * @return
     */
    String locad2BJ(String time);

    /**
     * 当地时间转北京时间
     *
     * @param date
     * @return
     */
    Date locad2BJ(Date date);

}
