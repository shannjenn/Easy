package com.jen.easyui.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 */

public abstract class EasyHScrollRecyclerViewAdapter<T> extends EasyRecyclerBaseAdapter<T> {
    private final String TAG = EasyHScrollRecyclerViewAdapter.class.getSimpleName();
    protected final Map<Integer, EasyHScrollView> mHScrollViews = new HashMap<>();
    protected int mScrollX;
    private EasyHScrollView.ScrollListener mScrollListener;

    public Map<Integer, EasyHScrollView> getHScrollViews() {
        return mHScrollViews;
    }

    public void addEasyHScrollView(EasyHScrollView easyHScrollView) {
        mHScrollViews.put(-1, easyHScrollView);
        setScroll(easyHScrollView);
    }

    public int getScrollX() {
        return mScrollX;
    }

    /**
     * @param context
     * @param data    数据
     */
    protected EasyHScrollRecyclerViewAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = onBindLayout();
        if (layout == 0) {
            Log.w(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        if (view == null) {
            Log.w(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        return bindHolder(view);
    }

    @Override
    public void onBindViewHolder(EasyHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        View v = holder.itemView.findViewById(onBindEasyHScrollViewId());
        if (v instanceof EasyHScrollView) {
            EasyHScrollView scrollView = (EasyHScrollView) v;
            mHScrollViews.put(position, scrollView);
            setScroll(scrollView);
        } else {
            try {
                throw new RuntimeException("未引用：" + EasyHScrollView.class.getName() + ",请正确调用onBindEasyHScrollViewId");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    private void setScroll(final EasyHScrollView scrollView) {
        if (scrollView.getScaleX() != mScrollX) {
            scrollView.smoothScrollTo(mScrollX, 0);
        }
        scrollView.setScrollListener(new EasyHScrollView.ScrollListener() {
            @Override
            public void OnScrollChanged(int x, int y) {
                mScrollX = x;
                scrollAllToX();
                if (mScrollListener != null) {
                    mScrollListener.OnScrollChanged(x, y);
                }
            }

            @Override
            public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
                if (mScrollListener != null) {
                    mScrollListener.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
                }
            }

            @Override
            public void onItemClick(int position) {
                if (mScrollListener != null) {
                    mScrollListener.onItemClick(position);
                }
            }
        });
    }

    /**
     * EasyHScrollRecyclerView 滑动
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    public void recyclerViewOnScrollChanged(EasyHScrollRecyclerView recyclerView, int l, int t, int oldl, int oldt) {
        scrollAllToX();
    }

    public void scrollAllToX() {
        Set<Integer> keys = mHScrollViews.keySet();
        for (int key : keys) {
            EasyHScrollView view = mHScrollViews.get(key);
            if (view != null && view.getScrollX() != mScrollX) {
                view.smoothScrollTo(mScrollX, 0);
            }
        }
    }


    public void setScrollListener(EasyHScrollView.ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    protected abstract int onBindLayout();

    protected abstract int onBindEasyHScrollViewId();

}
