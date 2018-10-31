package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class BasicViewActivity extends EasyActivity {

//    @EasyViewID(R.id.easy_tabbar_txtimg)
//    EasyTabBarBottom easy_tabbar_txtimg;

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
