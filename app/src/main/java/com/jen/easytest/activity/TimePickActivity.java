package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.Easy;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.timepick.EasyTimePickerConfig;
import com.jen.easyui.timepick.EasyTimePickListener;
import com.jen.easyui.timepick.EasyTimePickerPopWin;

import java.util.Calendar;

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
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Easy.BIND.Method({R.id.time_pick_1, R.id.time_pick_2, R.id.time_pick_3, R.id.time_pick_4})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.time_pick_1: {
                EasyTimePickerConfig timePickerConfig = new EasyTimePickerConfig(this)
                        .setLoopTextSize(16) // pick view text size
                        .setType(EasyTimePickerConfig.Type.YEAR_MONTH_DAY_WEEK);
                EasyTimePickerPopWin pickerPopWin = new EasyTimePickerPopWin(timePickerConfig);
                pickerPopWin.setTimePickedListener(new EasyTimePickListener() {
                    @Override
                    public void onPick(Calendar calendar) {
                        int year = calendar.get(Calendar.YEAR);
                        int moth = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                    }
                });
                pickerPopWin.showPopWin(this);
                break;
            }
            case R.id.time_pick_2: {
                EasyTimePickerConfig timePickerConfig = new EasyTimePickerConfig(this)
                        .setLoopTextSize(16) // pick view text size
                        .setType(EasyTimePickerConfig.Type.YEAR_MONTH_DAY_HOUR_MIN_WEEK)
                        .setUnit(EasyTimePickerConfig.Unit.YEAR_MONTH_DAY_HOUR_MIN);
                EasyTimePickerPopWin pickerPopWin = new EasyTimePickerPopWin(timePickerConfig);
                pickerPopWin.setTimePickedListener(new EasyTimePickListener() {
                    @Override
                    public void onPick(Calendar calendar) {
                        int year = calendar.get(Calendar.YEAR);
                        int moth = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                    }
                });
                pickerPopWin.showPopWin(this);
                break;
            }
            case R.id.time_pick_3: {
                EasyTimePickerConfig timePickerConfig = new EasyTimePickerConfig(this)
                        .setLoopTextSize(16) // pick view text size
                        .setType(EasyTimePickerConfig.Type.HOURS_MIN)
                        .setUnit(EasyTimePickerConfig.Unit.YEAR_MONTH_HOUR_MIN);
                EasyTimePickerPopWin pickerPopWin = new EasyTimePickerPopWin(timePickerConfig);
                pickerPopWin.setTimePickedListener(new EasyTimePickListener() {
                    @Override
                    public void onPick(Calendar calendar) {
                        int year = calendar.get(Calendar.YEAR);
                        int moth = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                    }
                });
                pickerPopWin.showPopWin(this);
                break;
            }
            case R.id.time_pick_4: {
                EasyTimePickerConfig timePickerConfig = new EasyTimePickerConfig(this)
                        .setLoopTextSize(16) // pick view text size
                        .setType(EasyTimePickerConfig.Type.YEAR_MONTH_DAY_HOUR_MIN)
                        .setUnit(EasyTimePickerConfig.Unit.YEAR_MONTH_HOUR_MIN);
                EasyTimePickerPopWin pickerPopWin = new EasyTimePickerPopWin(timePickerConfig);
                pickerPopWin.setTimePickedListener(new EasyTimePickListener() {
                    @Override
                    public void onPick(Calendar calendar) {
                        int year = calendar.get(Calendar.YEAR);
                        int moth = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        EasyLog.d(year + "-" + moth + "-" + day + " " + hour + ":" + minute);
                    }
                });
                pickerPopWin.showPopWin(this);
                break;
            }
            default: {

                break;
            }
        }
    }

    @Override
    public void success(int flagCode, String flag, Object response) {

    }

    @Override
    public void fail(int flagCode, String flag, String msg) {

    }
}
