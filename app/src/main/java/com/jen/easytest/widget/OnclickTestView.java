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
    float xDown = 0;
    float yDown = 0;
    float xUp = 0;
    float yUp = 0;

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
    public boolean onTouchEvent(MotionEvent ev) {
//        boolean ret = super.onTouchEvent(ev);
//        EasyLog.d("OnclickTestView view onTouchEvent  ------- " + ret);
//        return ret;
        boolean ret = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = ev.getX();
                yDown = ev.getY();
//                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
//                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_UP:
                xUp = ev.getX();
                yUp = ev.getY();
                if (Math.abs((xDown - xUp)) <= 20 && Math.abs((yDown - yUp)) <= 20) {
                    performClick();
                    ret = false;
                } else {
//                    super.dispatchTouchEvent(ev);
                }
                break;
            default:
//                super.dispatchTouchEvent(ev);
                break;
        }
        EasyLog.d("OnclickTestView view onTouchEvent  ------- " + ret);
        return ret;
//        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
