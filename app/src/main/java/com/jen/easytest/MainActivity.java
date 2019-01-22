package com.jen.easytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easytest.activity.BasicViewActivity;
import com.jen.easytest.activity.DialogActivity;
import com.jen.easytest.activity.HttpActivity;
import com.jen.easytest.activity.ImageLoaderActivity;
import com.jen.easytest.activity.ImageViewPagerActivity;
import com.jen.easytest.activity.OnclickTestActivity;
import com.jen.easytest.activity.PickerViewActivity;
import com.jen.easytest.activity.PopupWindowActivity;
import com.jen.easytest.activity.SQLiteActivity;
import com.jen.easytest.activity.TabBarActivity;
import com.jen.easytest.activity.TimePickActivity;
import com.jen.easytest.activity.UtilActivity;
import com.jen.easytest.activity.recyclerView.RecyclerViewMainActivity;
import com.jen.easyui.base.EasyActivity;

public class MainActivity extends EasyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    @EasyBindClick({R.id.tabBar, R.id.basicView, R.id.http, R.id.imageLoader, R.id.dialog, R.id.popupWindow,
            R.id.sqlite, R.id.util, R.id.recycleView, R.id.timePick, R.id.ImageViewPager, R.id.OnclickTest,
            R.id.PickerView})
    @Override
    protected void onBindClick(View view) {
        Class clazz = null;
        switch (view.getId()) {
            case R.id.tabBar: {
                clazz = TabBarActivity.class;
                break;
            }
            case R.id.basicView: {
                clazz = BasicViewActivity.class;
                break;
            }
            case R.id.http: {
                clazz = HttpActivity.class;
                break;
            }
            case R.id.imageLoader: {
                clazz = ImageLoaderActivity.class;
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
            case R.id.sqlite: {
                clazz = SQLiteActivity.class;
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
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
