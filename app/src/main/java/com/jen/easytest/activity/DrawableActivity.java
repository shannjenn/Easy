package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easytest.R;
import com.jen.easyui.activity.EasyBaseActivity;
import com.jen.easyui.button.EasyButton;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class DrawableActivity extends EasyBaseActivity {

    @EasyMouse.BIND.ID(R.id.easyButon)
    EasyButton easyButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
//        easyButon.setBackgroundColor(0xffff0000);
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

}
