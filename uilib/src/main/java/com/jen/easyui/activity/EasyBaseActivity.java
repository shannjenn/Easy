package com.jen.easyui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.EasyMain;

public abstract class EasyBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        EasyMain.mBindView.bind(this);
        intDataBefreView();
        initViews();
        onClick();
        loadDataAfterView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.mBindView.unbind(this);
    }

    protected abstract void intDataBefreView();

    protected abstract void initViews();

    protected abstract void loadDataAfterView();

    protected abstract void onClick();

}
