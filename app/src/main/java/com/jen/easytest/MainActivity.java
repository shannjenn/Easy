package com.jen.easytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easytest.activity.DialogActivity;
import com.jen.easytest.activity.DrawableActivity;
import com.jen.easytest.activity.HttpActivity;
import com.jen.easytest.activity.ImageLoaderActivity;
import com.jen.easytest.activity.TabBarActivity;
import com.jen.easyui.activity.EasyBaseActivity;

public class MainActivity extends EasyBaseActivity {

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


    @EasyMouse.BIND.Method({R.id.tabBar, R.id.drawable, R.id.http, R.id.imageLoader, R.id.dialog})
    @Override
    protected void onBindClick(View view) {
        Class clazz = null;
        switch (view.getId()) {
            case R.id.tabBar: {
                clazz = TabBarActivity.class;
                break;
            }
            case R.id.drawable: {
                clazz = DrawableActivity.class;
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
            default: {

                break;
            }
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
