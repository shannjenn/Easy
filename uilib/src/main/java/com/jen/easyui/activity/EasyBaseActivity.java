package com.jen.easyui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        intDataBeforeView();
        initViews();
        loadDataAfterView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.mBindView.unbind(this);
    }

    protected abstract void intDataBeforeView();

    protected abstract void initViews();

    protected abstract void loadDataAfterView();

    protected abstract void onBindClick(View view);

}
