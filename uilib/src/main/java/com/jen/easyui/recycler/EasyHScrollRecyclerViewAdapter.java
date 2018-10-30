package com.jen.easyui.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 */

public abstract class EasyHScrollRecyclerViewAdapter<T> extends EasyRecyclerBaseAdapter<T> {
    private final String TAG = EasyHScrollRecyclerViewAdapter.class.getSimpleName();
    private final ArrayList<EasyHScrollView> mHScrollViews = new ArrayList<>();
    private int mScrollX;

    ArrayList<EasyHScrollView> getHScrollViews() {
        return mHScrollViews;
    }

    int getScrollX() {
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

        View v = view.findViewById(onBindEasyHScrollViewId());
        if (v instanceof EasyHScrollView) {
            EasyHScrollView scrollView = (EasyHScrollView) v;
            if (!mHScrollViews.contains(scrollView)) {
                mHScrollViews.add(scrollView);
                scrollView.setScrollListener(new EasyHScrollView.ScrollListener() {
                    @Override
                    public void OnScrollChanged(int x, int y) {
                        mScrollX = x;
                        for (int i = 0; i < mHScrollViews.size(); i++) {
                            EasyHScrollView easyHScrollView = mHScrollViews.get(i);
                            if (easyHScrollView.getScrollX() != mScrollX) {
                                easyHScrollView.scrollTo(mScrollX, 0);
                            }
                        }
                    }
                });
            }
        } else {
            try {
                throw new RuntimeException("未引用：" + EasyHScrollView.class.getName() + ",请正确调用onBindEasyHScrollViewId");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bindHolder(view);
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    protected abstract int onBindLayout();

    protected abstract int onBindEasyHScrollViewId();


}
