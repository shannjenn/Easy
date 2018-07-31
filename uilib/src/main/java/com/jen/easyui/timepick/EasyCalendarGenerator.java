package com.jen.easyui.timepick;

import java.util.Calendar;
import java.util.Locale;


/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
class EasyCalendarGenerator {
    private final int DEFAULT_YEAR_LIMIT_MIN = 3;
    private final int DEFAULT_YEAR_LIMIT_MAX = 5;

    private int minYearLimit; // 可显示的年数量, 当前时间,往前
    private int maxYearLimit; // 可显示的年数量, 当前时间,往后

    private Calendar mInitCalendar;
    private Calendar mMinCalendar;
    private Calendar mMaxCalendar;

    EasyCalendarGenerator() {
        mInitCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();

        mMaxCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();
        mMaxCalendar.setTimeInMillis(mInitCalendar.getTimeInMillis());
        mMaxCalendar.add(Calendar.YEAR, DEFAULT_YEAR_LIMIT_MAX);

        mMinCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();
        mMinCalendar.setTimeInMillis(mInitCalendar.getTimeInMillis());
        mMinCalendar.add(Calendar.YEAR, -DEFAULT_YEAR_LIMIT_MIN);
    }

    /**
     * 获取初始日期
     */
    public Calendar getInitCalendar() {
        return mInitCalendar;
    }

    /**
     * 设置初始日期
     */
    public void setInitCalendar(long timeInMillis) {
        mInitCalendar.setTimeInMillis(timeInMillis);
    }

    public Calendar getMaxCalendar() {
        return mMaxCalendar;
    }

    public Calendar getMinCalendar() {
        return mMinCalendar;
    }

    public void setMinYearLimit(int minYearLimit) {
        int limit = minYearLimit - this.minYearLimit;
        this.minYearLimit = minYearLimit;
        mMinCalendar.add(Calendar.YEAR, -limit);
    }

    public void setMaxYearLimit(int maxYearLimit) {
        int limit = maxYearLimit - this.maxYearLimit;
        this.maxYearLimit = maxYearLimit;
        mMaxCalendar.add(Calendar.YEAR, limit);
    }
}
