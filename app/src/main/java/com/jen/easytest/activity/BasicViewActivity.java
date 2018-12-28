package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyViewID;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.baseview.EasyItemLayout;
import com.jen.easyui.baseview.EasyRotateView;
import com.jen.easyui.baseview.EasyShapeTextView;
import com.jen.easyui.baseview.EasyTopBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class BasicViewActivity extends EasyActivity {

    @EasyViewID(R.id.topBar)
    EasyTopBar topBar;

//    @EasyViewID(R.id.easy_tv_shape)
//    EasyShapeTextView easy_tv_shape;
//    @EasyViewID(R.id.easy_item_layout)
//    EasyItemLayout easy_item_layout;
    //    @EasyViewID(R.id.rote_view)
//    EasyRotateView rote_view;
//    @EasyViewID(R.id.rote_view2)
//    EasyRotateView rote_view2;

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
//                easy_tv_shape.getShape().setCheck(true);
            }
        }, 5000);

//        easy_item_layout.setTitle("1111");
//        easy_item_layout.setContentText("122211");

        /*easy_tv_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easy_tv_shape.getShape().setSolidColor(0xffff0000);
            }
        });*/

        List<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");
        list.add("DDD");
        list.add("EEE");
        list.add("FFF");
        list.add("GGG");
        list.add("HHHH");
        list.add("III");
        list.add("JJJ");
        list.add("KKK");
//        loop_view.setData(list);

//        rote_view.appendText("测试",0xff00ffff);
//        rote_view.update();
//        rote_view2.appendText("测试",0xff00ffff);
//        rote_view2.update();
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
