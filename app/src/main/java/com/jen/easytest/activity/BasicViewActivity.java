package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyViewID;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.baseview.EasyTopBar;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class BasicViewActivity extends EasyActivity {

    @EasyViewID(R.id.topBar)
    EasyTopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_view);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
        topBar.bindOnBackClick(this);
        initScroll();
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

    private void initScroll(){
    }



}
