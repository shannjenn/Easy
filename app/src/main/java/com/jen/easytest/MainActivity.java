package com.jen.easytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easytest.activity.BaseViewActivity;
import com.jen.easytest.activity.ShapeViewActivity;
import com.jen.easytest.activity.DialogActivity;
import com.jen.easytest.http.HttpActivity;
import com.jen.easytest.activity.ImageLoaderActivity;
import com.jen.easytest.activity.ImageViewPagerActivity;
import com.jen.easytest.activity.OnclickTestActivity;
import com.jen.easytest.activity.PickerViewActivity;
import com.jen.easytest.activity.popupWindow.PopupWindowActivity;
import com.jen.easytest.activity.SQLiteActivity;
import com.jen.easytest.activity.TabBarActivity;
import com.jen.easytest.activity.TimePickActivity;
import com.jen.easytest.activity.UtilActivity;
import com.jen.easytest.activity.recyclerView.RecyclerViewMainActivity;
import com.jen.easytest.logcatch.LogcatCrashActivity;

import easybase.EasyActivity;

import com.jen.easyui.view.baseview.EasyTopBar;

public class MainActivity extends EasyActivity {

    @EasyBindId(R.id.topBar)
    EasyTopBar topBar;
    @EasyBindId(R.id.http)
    Button http;


    @Override
    public int bindView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        topBar.bindOnBackClick(this);
//        Throw.exception(ExceptionType.ClassCastException,"ClassCastException-*---------*-------");
//        Throw.exception(ExceptionType.NullPointerException,"NullPointerException-*---------*-------");
//        Throw.exception(ExceptionType.RuntimeException,"RuntimeException-*---------*-------");
//        onBindClick(http);
    }


    @EasyBindClick({R.id.tabBar, R.id.baseView, R.id.shapeView, R.id.http, R.id.imageLoader, R.id.dialog, R.id.popupWindow,
            R.id.sqlite, R.id.util, R.id.recycleView, R.id.timePick, R.id.ImageViewPager, R.id.OnclickTest,
            R.id.PickerView, R.id.logcatCrash})
    @Override
    protected void onBindClick(View view) {
        Class clazz = null;
        switch (view.getId()) {
            case R.id.tabBar: {
                clazz = TabBarActivity.class;
                break;
            }
            case R.id.baseView: {
                clazz = BaseViewActivity.class;
                break;
            }
            case R.id.shapeView: {
                clazz = ShapeViewActivity.class;
                break;
            }
            case R.id.dialog: {
                clazz = DialogActivity.class;
                break;
            }
            case R.id.popupWindow: {
                clazz = PopupWindowActivity.class;
                break;
            }
            case R.id.util: {
                clazz = UtilActivity.class;
                break;
            }
            case R.id.recycleView: {
                clazz = RecyclerViewMainActivity.class;
                break;
            }
            case R.id.timePick: {
                clazz = TimePickActivity.class;
                break;
            }
            case R.id.PickerView: {
                clazz = PickerViewActivity.class;
                break;
            }
            case R.id.ImageViewPager: {
                clazz = ImageViewPagerActivity.class;
                break;
            }
            case R.id.OnclickTest: {
                clazz = OnclickTestActivity.class;
                break;
            }


            case R.id.sqlite: {
                clazz = SQLiteActivity.class;
                break;
            }
            case R.id.imageLoader: {
                clazz = ImageLoaderActivity.class;
                break;
            }
            case R.id.http: {
                clazz = HttpActivity.class;
                break;
            }
            case R.id.logcatCrash: {
                clazz = LogcatCrashActivity.class;
                break;
            }
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
