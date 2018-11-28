package com.jen.easytest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jen.easy.log.EasyLog;

/**
 * 作者：ShannJenn
 * 时间：2018/11/26.
 * 说明：
 */
public class OnclickTestView extends android.support.v7.widget.AppCompatTextView {

    public OnclickTestView(Context context) {
        super(context);
    }

    public OnclickTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnclickTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = super.dispatchTouchEvent(event);
        EasyLog.d("OnclickTestView view dispatchTouchEvent  ------- " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        EasyLog.d("OnclickTestView view onTouchEvent  ------- " + ret);
        return ret;
    }
}
