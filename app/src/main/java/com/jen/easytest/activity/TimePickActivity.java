package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.popupwindow.EasyWindow;
import com.jen.easyui.popupwindow.TimePickerBuild;

import java.util.Calendar;

import easybase.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class TimePickActivity extends EasyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pick);
    }


    @Override
    protected void initViews() {
    }


    @EasyBindClick({R.id.time_pick_1, R.id.time_pick_2, R.id.time_pick_3, R.id.time_pick_4, R.id.time_pick_5})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.time_pick_1: {
                EasyWindow.buildTimerPicker(this)
                        .setType(TimePickerBuild.Type.YEAR_MONTH_DAY_WEEK)
                        .setTimePickerListener(new TimePickerBuild.PickerListener() {
                            @Override
                            public void onPick(Calendar calendar) {
                                int year = calendar.get(Calendar.YEAR);
                                int moth = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                            }
                        })
                        .createTimePicker()
                        .showCenter();
                break;
            }
            case R.id.time_pick_2: {
                EasyWindow.buildTimerPicker(this)
                        .setType(TimePickerBuild.Type.YEAR_MONTH_DAY_HOUR_MIN_WEEK)
                        .setInitCalendar(System.currentTimeMillis() - 24 * 3600 * 1000)
                        .setTimePickerListener(new TimePickerBuild.PickerListener() {
                            @Override
                            public void onPick(Calendar calendar) {
                                int year = calendar.get(Calendar.YEAR);
                                int moth = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                            }
                        })
                        .createTimePicker()
                        .showCenter();
                break;
            }
            case R.id.time_pick_3: {
                EasyWindow.buildTimerPicker(this)
                        .setType(TimePickerBuild.Type.YEAR_MONTH_DAY)
                        .setUnit(TimePickerBuild.Unit.YEAR_MONTH_DAY_HOUR_MIN)
                        .setTimePickerListener(new TimePickerBuild.PickerListener() {
                            @Override
                            public void onPick(Calendar calendar) {
                                int year = calendar.get(Calendar.YEAR);
                                int moth = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                            }
                        })
                        .createTimePicker()
                        .showCenter();
                break;
            }
            case R.id.time_pick_4: {
                EasyWindow.buildTimerPicker(this)
                        .setType(TimePickerBuild.Type.YEAR_MONTH_DAY_HOUR_MIN)
                        .setUnit(TimePickerBuild.Unit.YEAR_MONTH_DAY_HOUR_MIN)
                        .setTimePickerListener(new TimePickerBuild.PickerListener() {
                            @Override
                            public void onPick(Calendar calendar) {
                                int year = calendar.get(Calendar.YEAR);
                                int moth = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                            }
                        })
                        .createTimePicker()
                        .showCenter();
                break;
            }
            case R.id.time_pick_5: {
                EasyWindow.buildTimerPicker(this)
                        .setType(TimePickerBuild.Type.HOUR_MIN)
                        .setUnit(TimePickerBuild.Unit.YEAR_MONTH_DAY_HOUR_MIN)
                        .setTimePickerListener(new TimePickerBuild.PickerListener() {
                            @Override
                            public void onPick(Calendar calendar) {
                                int year = calendar.get(Calendar.YEAR);
                                int moth = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                            }
                        })
                        .createTimePicker()
                        .showCenter();
                break;
            }
            default: {

                break;
            }
        }
    }

}
