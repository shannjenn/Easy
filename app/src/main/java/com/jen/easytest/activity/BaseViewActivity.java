package com.jen.easytest.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;

import easybase.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class BaseViewActivity extends EasyActivity {

    @EasyBindId(R.id.tv_text)
    TextView tv_text;

//    @EasyBindId(R.id.easy_tv_shape)
//    EasyShapeTextView easy_tv_shape;
//    @EasyBindId(R.id.easy_item_layout)
//    EasyItemLayout easy_item_layout;
    //    @EasyBindId(R.id.rote_view)
//    EasyRotateView rote_view;
//    @EasyBindId(R.id.rote_view2)
//    EasyRotateView rote_view2;

    @Override
    public int bindView() {
        return R.layout.activity_base_view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        start(tv_text);
    }


    public ValueAnimator start(final View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 30.0f, 0.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setTarget(view);   //这个地方是一定要设置的 不然不知道是哪个对象的  设置是哪个对象使用此动画
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        return valueAnimator;
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
