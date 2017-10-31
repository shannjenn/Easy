package com.jen.easytest;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.EasyMain;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        EasyMain.BIND.bind(this);
        initViews();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }

    protected abstract void initViews();
    protected abstract void initDatas();

}
