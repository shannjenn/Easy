package com.jen.easyui.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 * 说明：
 */
public class EasyHScrollView extends HorizontalScrollView {
    private ScrollListener scrollListener;

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
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollListener != null) {
            scrollListener.OnScrollChanged(l, t);
        }
    }

    public interface ScrollListener {
        void OnScrollChanged(int x, int y);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }
}
