package com.jen.easytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easytest.activity.TabBarActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @EasyMouse.BIND.Method({R.id.tabBar})
    private void onClick(View view) {
        Class clazz = null;
        switch (view.getId()) {
            case R.id.tabBar: {
                clazz = TabBarActivity.class;
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
