package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyViewID;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.baseview.EasyItemLayout;
import com.jen.easyui.baseview.EasyShapeTextView;
import com.jen.easyui.baseview.EasyTopBar;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class BasicViewActivity extends EasyActivity {

    @EasyViewID(R.id.topBar)
    EasyTopBar topBar;

    @EasyViewID(R.id.easy_tv_shape)
    EasyShapeTextView easy_tv_shape;
    @EasyViewID(R.id.easy_item_layout)
    EasyItemLayout easy_item_layout;

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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                easy_tv_shape.getShape().setSolidColor(0xffff0000);
                easy_tv_shape.getShape().setCheck(true);
            }
        }, 5000);

        easy_item_layout.setTitle("1111");
        easy_item_layout.setContentText("122211");

        /*easy_tv_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easy_tv_shape.getShape().setSolidColor(0xffff0000);
            }
        });*/
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

    private void initScroll() {

    }


}
