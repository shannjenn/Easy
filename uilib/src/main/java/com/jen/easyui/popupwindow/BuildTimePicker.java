package com.jen.easyui.popupwindow;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowDismissListener;
import com.jen.easyui.popupwindow.listener.WindowTopBarListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public class BuildTimePicker<T> extends Build<T> {
    public Integer loopTextSize;
    public Type type = Type.YEAR_MONTH_DAY_HOUR_MIN;
    public Unit unit = Unit.NON;

    public final int UNIT_YEAR = R.string._easy_time_unit_year;
    public final int UNIT_MONTH = R.string._easy_time_unit_month;
    public final int UNIT_DAY = R.string._easy_time_unit_day;
    public final int UNIT_HOUR = R.string._easy_time_unit_hour;
    public final int UNIT_MINUTE = R.string._easy_time_unit_minute;
    public final int UNIT_SEC = R.string._easy_time_unit_sec;

    public final int DEFAULT_YEAR_LIMIT_MIN = 3;
    public final int DEFAULT_YEAR_LIMIT_MAX = 5;

    public int minYearLimit; // 可显示的年数量, 当前时间,往前
    public int maxYearLimit; // 可显示的年数量, 当前时间,往后

    public Calendar mInitCalendar;
    public Calendar mMinCalendar;
    public Calendar mMaxCalendar;

    public PickerListener pickerListener;

    BuildTimePicker(Context context) {
        super(context);
        mInitCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();

        mMaxCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();
        mMaxCalendar.setTimeInMillis(mInitCalendar.getTimeInMillis());
        mMaxCalendar.add(Calendar.YEAR, DEFAULT_YEAR_LIMIT_MAX);

        mMinCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();
        mMinCalendar.setTimeInMillis(mInitCalendar.getTimeInMillis());
        mMinCalendar.add(Calendar.YEAR, -DEFAULT_YEAR_LIMIT_MIN);
    }

    /**
     * String
     *
     * @return .
     */
    public EasyTimePickerWindow createTimePicker() {
        return new EasyTimePickerWindow(this);
    }

    /**
     * 模式，年月日时分，年月日，时分，月日时分
     */
    public enum Type {
        YEAR_MONTH_DAY_HOUR_MIN, YEAR_MONTH_DAY_HOUR_MIN_SEC, YEAR_MONTH_DAY, YEAR_MONTH, MONTH_DAY_HOUR_MIN, HOUR_MIN,
        YEAR_MONTH_DAY_HOUR_MIN_WEEK, YEAR_MONTH_DAY_WEEK, MONTH_DAY_HOUR_MIN_WEEK
    }

    /**
     * 单位
     */
    public enum Unit {
        NON, YEAR_MONTH_DAY_HOUR_MIN, YEAR_MONTH_DAY_HOUR_MIN_SEC, YEAR_MONTH_HOUR_MIN
    }

    public BuildTimePicker setType(Type type) {
        this.type = type;
        return this;
    }

    public BuildTimePicker setUnit(Unit unit) {
        this.unit = unit;
        return this;
    }

    /**
     * 轴大小
     */
    public BuildTimePicker setLoopTextSize(Integer textSize) {
        this.loopTextSize = textSize;
        return this;
    }

    /**
     * 设置初始日期
     */
    public BuildTimePicker setInitCalendar(long timeInMillis) {
        mInitCalendar.setTimeInMillis(timeInMillis);
        return this;
    }

    public BuildTimePicker setMinYearLimit(int minYearLimit) {
        int limit = minYearLimit - this.minYearLimit;
        this.minYearLimit = minYearLimit;
        mMinCalendar.add(Calendar.YEAR, -limit);
        return this;
    }

    public BuildTimePicker setMaxYearLimit(int maxYearLimit) {
        int limit = maxYearLimit - this.maxYearLimit;
        this.maxYearLimit = maxYearLimit;
        mMaxCalendar.add(Calendar.YEAR, limit);
        return this;
    }

    public BuildTimePicker setTimePickerListener(PickerListener pickerListener) {
        this.pickerListener = pickerListener;
        return this;
    }

    @Override
    public BuildTimePicker<T> setFlagCode(int flagCode) {
        super.setFlagCode(flagCode);
        return this;
    }

    @Override
    public BuildTimePicker<T> setShowTopBar(boolean showTopBar) {
        super.setShowTopBar(showTopBar);
        return this;
    }

    @Override
    public BuildTimePicker<T> setHeight(int height) {
        super.setHeight(height);
        return this;
    }

    @Override
    public BuildTimePicker<T> setWidth(int width) {
        super.setWidth(width);
        return this;
    }

    @Override
    public BuildTimePicker<T> setTopBarListener(WindowTopBarListener topBarListener) {
        super.setTopBarListener(topBarListener);
        return this;
    }

    @Override
    public BuildTimePicker<T> setDismissListener(WindowDismissListener dismissListener) {
        super.setDismissListener(dismissListener);
        return this;
    }

    @Override
    public BuildTimePicker<T> setStyleTopBar(StyleTopBar styleTopBar) {
        super.setStyleTopBar(styleTopBar);
        return this;
    }

    @Override
    public BuildTimePicker<T> setStyleAnim(StyleAnim animStyle) {
        super.setStyleAnim(animStyle);
        return this;
    }

    @Override
    public BuildTimePicker<T> setShowAlpha(float showAlpha) {
        super.setShowAlpha(showAlpha);
        return this;
    }

    @Override
    public BuildTimePicker<T> setBackground(Drawable background) {
        super.setBackground(background);
        return this;
    }

    @Override
    public BuildTimePicker<T> setData(List<T> data) {
        super.setData(data);
        return this;
    }

    public interface PickerListener {
        void onPick(Calendar calendar);
    }
}
