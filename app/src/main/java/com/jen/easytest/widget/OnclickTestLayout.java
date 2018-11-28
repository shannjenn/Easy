package com.jen.easytest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.jen.easy.log.EasyLog;

/**
 * 作者：ShannJenn
 * 时间：2018/11/26.
 * 说明：
 */
public class OnclickTestLayout extends RelativeLayout {

    public OnclickTestLayout(Context context) {
        super(context);
    }

    public OnclickTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnclickTestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = super.dispatchTouchEvent(event);
        EasyLog.d("OnclickTestView layout dispatchTouchEvent  ============================= " + ret);
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        EasyLog.d("OnclickTestView layout onInterceptTouchEvent  ============================= " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        EasyLog.d("OnclickTestLayout layout onTouchEvent  ============================= " + ret);
        return ret;
    }
}
