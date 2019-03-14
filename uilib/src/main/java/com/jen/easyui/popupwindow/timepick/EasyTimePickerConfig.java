package com.jen.easyui.popupwindow.timepick;

import android.content.Context;

import com.jen.easyui.R;


/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public class EasyTimePickerConfig {
    Integer loopTextSize;
    Context context;
    final EasyCalendarGenerator calendarGenerator = new EasyCalendarGenerator();
    Type type = Type.YEAR_MONTH_DAY_HOUR_MIN;
    Unit unit = Unit.NON;

    final int UNIT_YEAR = R.string._easy_time_unit_year;
    final int UNIT_MONTH = R.string._easy_time_unit_month;
    final int UNIT_DAY = R.string._easy_time_unit_day;
    final int UNIT_HOUR = R.string._easy_time_unit_hour;
    final int UNIT_MINUTE = R.string._easy_time_unit_minute;

    /**
     * 模式，年月日时分，年月日，时分，月日时分
     */
    public enum Type {
        YEAR_MONTH_DAY_HOUR_MIN, YEAR_MONTH_DAY, YEAR_MONTH, MONTH_DAY_HOUR_MIN, HOUR_MIN,
        YEAR_MONTH_DAY_HOUR_MIN_WEEK, YEAR_MONTH_DAY_WEEK, MONTH_DAY_HOUR_MIN_WEEK
    }

    /**
     * 单位
     */
    public enum Unit {
        NON, YEAR_MONTH_DAY_HOUR_MIN, YEAR_MONTH_HOUR_MIN
    }

    public EasyTimePickerConfig(Context context) {
        this.context = context;
    }

    public EasyTimePickerConfig setType(Type type) {
        this.type = type;
        return this;
    }

    public EasyTimePickerConfig setUnit(Unit unit) {
        this.unit = unit;
        return this;
    }

    /**
     * 设置初始化日期
     */
    public EasyTimePickerConfig setCurrentDate(long timeInMillis) {
        calendarGenerator.setInitCalendar(timeInMillis);
        return this;
    }

    /**
     * 最小年限
     */
    public EasyTimePickerConfig setMinYearLimit(int limit) {
        calendarGenerator.setMinYearLimit(limit);
        return this;
    }

    /**
     * 最大年限
     */
    public EasyTimePickerConfig setMaxYearLimit(int limit) {
        calendarGenerator.setMaxYearLimit(limit);
        return this;
    }

    /**
     * 轴大小
     */
    public EasyTimePickerConfig setLoopTextSize(Integer textSize) {
        this.loopTextSize = textSize;
        return this;
    }

}
