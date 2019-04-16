package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.view.baseview.EasyTopBar;
import com.jen.easyui.view.shapeview.EasyShapeTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class ShapeViewActivity extends EasyActivity {

//    @EasyBindId(R.id.shapeTextView)
//    EasyShapeTextView shapeTextView;

//    @EasyBindId(R.id.easy_tv_shape)
//    EasyShapeTextView easy_tv_shape;
//    @EasyBindId(R.id.easy_item_layout)
//    EasyItemLayout easy_item_layout;
    //    @EasyBindId(R.id.rote_view)
//    EasyRotateView rote_view;
//    @EasyBindId(R.id.rote_view2)
//    EasyRotateView rote_view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_view);
    }


    @Override
    protected void initViews() {
    }


//    @EasyBindClick({R.id.shapeTextView_check, R.id.shapeTextView_button})
//    @Override
//    protected void onBindClick(View view) {
//        switch (view.getId()) {
//            case R.id.shapeTextView_check: {
//                EasyLog.d("shapeTextView_check click---------------");
//                break;
//            }
//            case R.id.shapeTextView_button: {
//                EasyLog.d("shapeTextView_button click---------------");
//                break;
//            }
//        }
//    }


}
