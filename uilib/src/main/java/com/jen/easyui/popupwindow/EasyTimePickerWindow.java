package com.jen.easyui.popupwindow;

import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDateFormatUtil;
import com.jen.easyui.view.loopview.EasyLoopView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * 说明：时间选择器
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public class EasyTimePickerWindow<T> extends EasyWindow<T> {
    private Calendar selectCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();
    private BuildTimePicker<T> buildTimePicker;

    private EasyLoopView yearLoopView;
    private EasyLoopView monthLoopView;
    private EasyLoopView dayLoopView;
    private EasyLoopView hourLoopView;
    private EasyLoopView minuteLoopView;
    private EasyLoopView secLoopView;

    private final List<String> yearList = new ArrayList<>();
    private final List<String> monthList = new ArrayList<>();
    private final List<String> dayList = new ArrayList<>();
    private final List<String> hourList = new ArrayList<>();
    private final List<String> minuteList = new ArrayList<>();
    private final List<String> secList = new ArrayList<>();

    EasyTimePickerWindow(BuildTimePicker<T> buildTimePicker) {
        super(buildTimePicker);
        this.buildTimePicker = buildTimePicker;
        init();
    }

    @Override
    View bindView() {
        View contentView = LayoutInflater.from(this.build.context).inflate(R.layout._easy_popup_time_picker, null);
        yearLoopView = contentView.findViewById(R.id.picker_year);
        monthLoopView = contentView.findViewById(R.id.picker_month);
        dayLoopView = contentView.findViewById(R.id.picker_day);
        hourLoopView = contentView.findViewById(R.id.picker_clock);
        minuteLoopView = contentView.findViewById(R.id.picker_minute);
        secLoopView = contentView.findViewById(R.id.picker_sec);
        return contentView;
    }

    private void init() {
        selectCalendar.setTimeInMillis(buildTimePicker.mInitCalendar.getTimeInMillis());
        switch (buildTimePicker.unit) {
            case NON: {

                break;
            }
            case YEAR_MONTH_DAY_HOUR_MIN: {
                yearLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_YEAR));
                monthLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_MONTH));
                dayLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_DAY));
                hourLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_HOUR));
                minuteLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_MINUTE));
                break;
            }
            case YEAR_MONTH_DAY_HOUR_MIN_SEC: {
                yearLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_YEAR));
                monthLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_MONTH));
                dayLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_DAY));
                hourLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_HOUR));
                minuteLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_MINUTE));
                secLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_SEC));
                break;
            }
            case YEAR_MONTH_HOUR_MIN: {
                yearLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_YEAR));
                monthLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_MONTH));
//                dayLoopView.setUnitText(build.context.getString(build.UNIT_DAY));
                hourLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_HOUR));
                minuteLoopView.setUnitText(this.build.context.getString(buildTimePicker.UNIT_MINUTE));
                break;
            }
        }

        if (buildTimePicker.loopTextSize != null) {
            yearLoopView.setTextSize(buildTimePicker.loopTextSize);
            monthLoopView.setTextSize(buildTimePicker.loopTextSize);
            dayLoopView.setTextSize(buildTimePicker.loopTextSize);
            hourLoopView.setTextSize(buildTimePicker.loopTextSize);
            minuteLoopView.setTextSize(buildTimePicker.loopTextSize);
            secLoopView.setTextSize(buildTimePicker.loopTextSize);
        }

        initPickerViews(); // init year and month loop view

        yearLoopView.setListener(loopViewListener);
        monthLoopView.setListener(loopViewListener);
        dayLoopView.setListener(loopViewListener);
        hourLoopView.setListener(loopViewListener);
        minuteLoopView.setListener(loopViewListener);
        secLoopView.setListener(loopViewListener);

//        setBackgroundDrawable(new BitmapDrawable());
//        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setTouchable(true);
        setFocusable(true);

        switch (buildTimePicker.type) {
            case YEAR_MONTH: {
                dayLoopView.setVisibility(View.GONE);
                hourLoopView.setVisibility(View.GONE);
                minuteLoopView.setVisibility(View.GONE);
                secLoopView.setVisibility(View.GONE);
                break;
            }
            case MONTH_DAY_HOUR_MIN:
            case MONTH_DAY_HOUR_MIN_WEEK: {
                yearLoopView.setVisibility(View.GONE);
                secLoopView.setVisibility(View.GONE);
                break;
            }
            case HOUR_MIN: {
                yearLoopView.setVisibility(View.GONE);
                monthLoopView.setVisibility(View.GONE);
                dayLoopView.setVisibility(View.GONE);
                secLoopView.setVisibility(View.GONE);
                break;
            }
            case YEAR_MONTH_DAY_HOUR_MIN:
            case YEAR_MONTH_DAY_HOUR_MIN_SEC:
            case YEAR_MONTH_DAY_HOUR_MIN_WEEK: {
                break;
            }
            case YEAR_MONTH_DAY:
            case YEAR_MONTH_DAY_WEEK: {
                hourLoopView.setVisibility(View.GONE);
                minuteLoopView.setVisibility(View.GONE);
                secLoopView.setVisibility(View.GONE);
                break;
            }
        }
    }

    /**
     * Init year and month loop view, Let the day loop view be handled
     * separately
     */
    private void initPickerViews() {
        int minYear = buildTimePicker.mMinCalendar.get(Calendar.YEAR);
        int maxYear = buildTimePicker.mMaxCalendar.get(Calendar.YEAR);
        int yearCount = maxYear - minYear + 1;
        for (int i = 0; i < yearCount; i++) {
            yearList.add(format2LenStr(minYear + i));
        }
        for (int j = 0; j < 12; j++) {
            monthList.add(format2LenStr(j + 1));
        }
        for (int k = 0; k < 24; k++) {
            hourList.add(format2LenStr(k));
        }
        for (int z = 0; z < 60; z++) {
            String val = format2LenStr(z);
            minuteList.add(val);
            secList.add(val);
        }
        yearLoopView.setData(yearList);
        yearLoopView.setInitPosition(selectCalendar.get(Calendar.YEAR) - minYear);
        monthLoopView.setData(monthList);
        monthLoopView.setInitPosition(selectCalendar.get(Calendar.MONTH));
        hourLoopView.setData(hourList);
        hourLoopView.setInitPosition(selectCalendar.get(Calendar.HOUR_OF_DAY));
        minuteLoopView.setData(minuteList);
        minuteLoopView.setInitPosition(selectCalendar.get(Calendar.MINUTE));
        secLoopView.setData(secList);
        secLoopView.setInitPosition(selectCalendar.get(Calendar.SECOND));
        initDayPickerView(); // init day loop view
    }

    /**
     * Init day item
     */
    private void initDayPickerView() {
        synchronized (this) {
            int dayMaxInMonth = selectCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<String> list = new ArrayList<>();
            switch (buildTimePicker.type) {
                case YEAR_MONTH_DAY_HOUR_MIN:
                case YEAR_MONTH_DAY_HOUR_MIN_SEC:
                case YEAR_MONTH_DAY:
                case YEAR_MONTH:
                case MONTH_DAY_HOUR_MIN:
                case HOUR_MIN: {
                    for (int i = 0; i < dayMaxInMonth; i++) {
                        list.add(format2LenStr(i + 1));
                    }
                    dayList.clear();
                    dayList.addAll(list);
                    break;
                }
                case YEAR_MONTH_DAY_HOUR_MIN_WEEK:
                case MONTH_DAY_HOUR_MIN_WEEK:
                case YEAR_MONTH_DAY_WEEK: {
                    EasyDateFormatUtil formatUtil = new EasyDateFormatUtil();
                    Calendar calendar = Calendar.getInstance(Locale.CHINA);
                    calendar.setTimeInMillis(selectCalendar.getTimeInMillis());
                    formatUtil.setFormat("dd EEEE");
                    calendar.add(Calendar.DAY_OF_MONTH, 1 - selectCalendar.get(Calendar.DAY_OF_MONTH));
                    int i = 0;
                    do {
                        String day = formatUtil.format(calendar);
                        list.add(day);
                        i++;
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    } while (i < dayMaxInMonth);
                    break;
                }
            }
            dayList.clear();
            dayList.addAll(list);
            dayLoopView.setData(dayList);
            dayLoopView.setInitPosition(selectCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        }
    }

    private EasyLoopView.EasyLoopViewListener loopViewListener = new EasyLoopView.EasyLoopViewListener() {
        @Override
        public void onItemSelect(EasyLoopView loopView, int item) {
            if (loopView == yearLoopView) {
                int minYear = buildTimePicker.mMinCalendar.get(Calendar.YEAR);
                int selectY = selectCalendar.get(Calendar.YEAR);
                selectCalendar.add(Calendar.YEAR, item - (selectY - minYear));
                initDayPickerView();
            } else if (loopView == monthLoopView) {
                int selectM = selectCalendar.get(Calendar.MONTH);
                selectCalendar.add(Calendar.MONTH, item - selectM);
                initDayPickerView();
            } else if (loopView == dayLoopView) {
                int selectDay = selectCalendar.get(Calendar.DAY_OF_MONTH);
                selectCalendar.add(Calendar.DAY_OF_MONTH, item + 1 - selectDay);
            } else if (loopView == hourLoopView) {
                int selectHour = selectCalendar.get(Calendar.HOUR_OF_DAY);
                selectCalendar.add(Calendar.HOUR_OF_DAY, item - selectHour);
            } else if (loopView == minuteLoopView) {
                int selectMinute = selectCalendar.get(Calendar.MINUTE);
                selectCalendar.add(Calendar.MINUTE, item - selectMinute);
            } else if (loopView == secLoopView) {
                int selectSec = selectCalendar.get(Calendar.SECOND);
                selectCalendar.add(Calendar.SECOND, item - selectSec);
            }
        }
    };

    @Override
    public void onClick(View v) {
//        super.onClick(v);
        if (buildTimePicker.pickerListener == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.iv_left || i == R.id.tv_left) {
            dismiss();
        } else if (i == R.id.iv_right || i == R.id.tv_right) {
            buildTimePicker.pickerListener.onPick(this, selectCalendar);
        }
    }

    /**
     * Transform int to String with prefix "0" if less than 10
     *
     * @param num .
     * @return .
     */
    public static String format2LenStr(int num) {
        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    @Override
    public void showBottom() {
        super.showBottom();
    }

    @Override
    public void showCenter() {
        super.showCenter();
    }
}
