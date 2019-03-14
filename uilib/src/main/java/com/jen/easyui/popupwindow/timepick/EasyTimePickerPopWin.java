package com.jen.easyui.popupwindow.timepick;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jen.easyui.R;
import com.jen.easyui.view.loopview.EasyLoopView;
import com.jen.easyui.util.EasyDateFormatUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * 说明：时间选择器
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public class EasyTimePickerPopWin extends PopupWindow implements OnClickListener {
    private Calendar selectCalendar = (Calendar) Calendar.getInstance(Locale.CHINA).clone();
    private EasyTimePickerConfig config;
    private EasyTimePickListener timePickedListener;

    //    private Button cancelBtn;
//    private Button confirmBtn;
    private EasyLoopView yearLoopView;
    private EasyLoopView monthLoopView;
    private EasyLoopView dayLoopView;
    private EasyLoopView hourLoopView;
    private EasyLoopView minuteLoopView;
    private View pickerContainerV;

    private final List<String> yearList = new ArrayList<>();
    private final List<String> monthList = new ArrayList<>();
    private final List<String> dayList = new ArrayList<>();
    private final List<String> hourList = new ArrayList<>();
    private final List<String> minuteList = new ArrayList<>();

    public EasyTimePickerPopWin(EasyTimePickerConfig timePickerConfig) {
        this.config = timePickerConfig;
        selectCalendar.setTimeInMillis(timePickerConfig.calendarGenerator.getInitCalendar().getTimeInMillis());
        initView(timePickerConfig.type);
    }

    private void initView(EasyTimePickerConfig.Type type) {
        View contentView = LayoutInflater.from(config.context).inflate(R.layout._easy_time_picker, null);
        Button cancelBtn = (Button) contentView.findViewById(R.id.btn_cancel);
        Button confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm);
        LinearLayout loop_contains = (LinearLayout) contentView.findViewById(R.id.loop_contains);
        yearLoopView = (EasyLoopView) contentView.findViewById(R.id.picker_year);
        monthLoopView = (EasyLoopView) contentView.findViewById(R.id.picker_month);
        dayLoopView = (EasyLoopView) contentView.findViewById(R.id.picker_day);
        hourLoopView = (EasyLoopView) contentView.findViewById(R.id.picker_clock);
        minuteLoopView = (EasyLoopView) contentView.findViewById(R.id.picker_minute);
        pickerContainerV = contentView.findViewById(R.id.container_picker);

        switch (config.unit) {
            case NON: {

                break;
            }
            case YEAR_MONTH_DAY_HOUR_MIN: {
                yearLoopView.setUnitText(config.context.getString(config.UNIT_YEAR));
                monthLoopView.setUnitText(config.context.getString(config.UNIT_MONTH));
                dayLoopView.setUnitText(config.context.getString(config.UNIT_DAY));
                hourLoopView.setUnitText(config.context.getString(config.UNIT_HOUR));
                minuteLoopView.setUnitText(config.context.getString(config.UNIT_MINUTE));
                break;
            }
            case YEAR_MONTH_HOUR_MIN: {
                yearLoopView.setUnitText(config.context.getString(config.UNIT_YEAR));
                monthLoopView.setUnitText(config.context.getString(config.UNIT_MONTH));
//                dayLoopView.setUnitText(config.context.getString(config.UNIT_DAY));
                hourLoopView.setUnitText(config.context.getString(config.UNIT_HOUR));
                minuteLoopView.setUnitText(config.context.getString(config.UNIT_MINUTE));
                break;
            }
        }

        if (config.loopTextSize != null) {
            yearLoopView.setTextSize(config.loopTextSize);
            monthLoopView.setTextSize(config.loopTextSize);
            dayLoopView.setTextSize(config.loopTextSize);
            hourLoopView.setTextSize(config.loopTextSize);
            minuteLoopView.setTextSize(config.loopTextSize);
        }

        initPickerViews(); // init year and month loop view

        yearLoopView.setListener(loopViewListener);
        monthLoopView.setListener(loopViewListener);
        dayLoopView.setListener(loopViewListener);
        hourLoopView.setListener(loopViewListener);
        minuteLoopView.setListener(loopViewListener);

        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        pickerContainerV.setOnClickListener(this);
        contentView.setOnClickListener(this);

        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.easyTimerPiker);
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setTouchable(true);
        setFocusable(true);

        switch (config.type) {
            case YEAR_MONTH: {
                dayLoopView.setVisibility(View.GONE);
                hourLoopView.setVisibility(View.GONE);
                minuteLoopView.setVisibility(View.GONE);
                break;
            }
            case MONTH_DAY_HOUR_MIN:
            case MONTH_DAY_HOUR_MIN_WEEK: {
                yearLoopView.setVisibility(View.GONE);
                break;
            }
            case HOUR_MIN: {
                yearLoopView.setVisibility(View.GONE);
                monthLoopView.setVisibility(View.GONE);
                dayLoopView.setVisibility(View.GONE);
                break;
            }
            case YEAR_MONTH_DAY_HOUR_MIN:
            case YEAR_MONTH_DAY_HOUR_MIN_WEEK: {
                break;
            }
            case YEAR_MONTH_DAY:
            case YEAR_MONTH_DAY_WEEK: {
                hourLoopView.setVisibility(View.GONE);
                minuteLoopView.setVisibility(View.GONE);
                break;
            }
        }
    }

    /**
     * Init year and month loop view, Let the day loop view be handled
     * separately
     */
    private void initPickerViews() {
        int minYear = config.calendarGenerator.getMinCalendar().get(Calendar.YEAR);
        int maxYear = config.calendarGenerator.getMaxCalendar().get(Calendar.YEAR);
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
            minuteList.add(format2LenStr(z));
        }
        yearLoopView.setData(yearList);
        yearLoopView.setInitPosition(selectCalendar.get(Calendar.YEAR) - minYear);
        monthLoopView.setData(monthList);
        monthLoopView.setInitPosition(selectCalendar.get(Calendar.MONTH));
        hourLoopView.setData(hourList);
        hourLoopView.setInitPosition(selectCalendar.get(Calendar.HOUR_OF_DAY));
        minuteLoopView.setData(minuteList);
        minuteLoopView.setInitPosition(selectCalendar.get(Calendar.MINUTE));

        initDayPickerView(); // init day loop view
    }

    /**
     * Init day item
     */
    private void initDayPickerView() {
        synchronized (this) {
            int dayMaxInMonth = selectCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<String> list = new ArrayList<>();
            switch (config.type) {
                case YEAR_MONTH_DAY_HOUR_MIN:
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
            dayLoopView.setData((ArrayList) dayList);
            dayLoopView.setInitPosition(selectCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        }
    }


    /**
     * Show date picker popWindow
     *
     * @param activity
     */
    public void showPopWin(Activity activity) {
        if (null != activity) {
            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);
            showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            trans.setDuration(240);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());
            pickerContainerV.startAnimation(trans);
        }
    }

    /**
     * Show date picker popWindow
     *
     * @param activity
     */
    public void showPopWin(Activity activity, View view) {
        if (null != activity) {
            TranslateAnimation trans = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                    0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0);
            showAtLocation(view, Gravity.BOTTOM, 0, 0);
            trans.setDuration(240);
            trans.setInterpolator(new AccelerateDecelerateInterpolator());
            pickerContainerV.startAnimation(trans);
        }
    }

    /**
     * Dismiss date picker popWindow
     */
    public void dismissPopWin() {
        TranslateAnimation trans = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        trans.setDuration(200);
        trans.setInterpolator(new AccelerateInterpolator());
        trans.setAnimationListener(new EasyTimePickAnimationListener(this));
        pickerContainerV.startAnimation(trans);
    }

    @Override
    public void onClick(View v) {
        if (null != timePickedListener) {
            int i = v.getId();
            if (i == R.id.btn_confirm) {
                long selectTime = selectCalendar.getTimeInMillis();
                long initTime = config.calendarGenerator.getInitCalendar().getTimeInMillis();
                if (selectTime != initTime) {
                    timePickedListener.onPick(selectCalendar);
                }
            } else {

            }
            dismissPopWin();
        }
    }

    private EasyLoopView.EasyLoopViewListener loopViewListener = new EasyLoopView.EasyLoopViewListener() {
        @Override
        public void onItemSelect(EasyLoopView loopView, int item) {
            if (loopView == yearLoopView) {
                int minYear = config.calendarGenerator.getMinCalendar().get(Calendar.YEAR);
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
            }
        }
    };

    /**
     * Transform int to String with prefix "0" if less than 10
     *
     * @param num
     * @return
     */
    public static String format2LenStr(int num) {
        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    public void setTimePickedListener(EasyTimePickListener timePickedListener) {
        this.timePickedListener = timePickedListener;
    }
}
