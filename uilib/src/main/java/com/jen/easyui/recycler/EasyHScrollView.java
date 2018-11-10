package com.jen.easyui.recycler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 * 说明：
 */
public class EasyHScrollView extends HorizontalScrollView {
    private ScrollListener scrollListener;
    private boolean onTouch;
    private final int H_TOUCH = 100;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onTouch = false;
        }
    };

    public EasyHScrollView(Context context) {
        super(context);
    }

    public EasyHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mHandler.removeMessages(H_TOUCH);
        onTouch = true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("EasyLog", l + " t= " + t + " oldl= " + oldl + " oldt= " + oldt);
        mHandler.removeMessages(H_TOUCH);
        mHandler.sendEmptyMessageDelayed(H_TOUCH, 300);
        if (onTouch && scrollListener != null) {
            scrollListener.OnScrollChanged(l, t);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        Log.d("EasyLog", scrollX + " " + scrollY + " " + clampedX + " " + clampedY);
        if (onTouch && scrollListener != null) {
            scrollListener.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        }
    }

    public interface ScrollListener {
        void OnScrollChanged(int x, int y);

        void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }
}
