package com.jen.easyui.recycler.HScroll;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyRecyclerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 */

public abstract class EasyHScrollRecyclerViewAdapter<T> extends EasyRecyclerAdapter<T> {
    private final String TAG = EasyHScrollRecyclerViewAdapter.class.getSimpleName();
    protected final Map<Integer, EasyHScrollView> mHScrollViews = new HashMap<>();
    protected int mScrollX;
    private EasyHScrollView.ScrollListener mScrollListener;

    private final int DELAYED_TIME = 50;//时间
    private final int H_UPDATE_SCROLL = 100;
    protected Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case H_UPDATE_SCROLL: {
                    EasyHScrollView scrollView = (EasyHScrollView) msg.obj;
                    setScroll(scrollView);
                    break;
                }
            }
        }
    };

    /**
     * @param context .
     * @param data    数据
     */
    protected EasyHScrollRecyclerViewAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public Map<Integer, EasyHScrollView> getHScrollViews() {
        return mHScrollViews;
    }

    public void addEasyHScrollView(EasyHScrollView headHScrollView) {
        mHScrollViews.put(-1, headHScrollView);
        setScroll(headHScrollView);
    }

    public int getScrollX() {
        return mScrollX;
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
            Message message = mHandler.obtainMessage();
            message.what = H_UPDATE_SCROLL;
            message.obj = scrollView;
            mHandler.sendMessageDelayed(message, DELAYED_TIME);
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
        if (scrollView.getScrollX() != mScrollX) {
            scrollView.scrollTo(mScrollX, 0);
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
     * @param l    left
     * @param t    top
     * @param oldl .
     * @param oldt .
     */
    public void recyclerViewOnScrollChanged(EasyHScrollRecyclerView recyclerView, int l, int t, int oldl, int oldt) {
        scrollAllToX();
    }

    public void scrollAllToX() {
        Set<Integer> keys = mHScrollViews.keySet();
        for (int key : keys) {
            EasyHScrollView view = mHScrollViews.get(key);
            if (view != null && view.getScrollX() != mScrollX) {
                view.scrollTo(mScrollX, 0);
            }
        }
    }


    public void setScrollListener(EasyHScrollView.ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    protected abstract int onBindLayout();

    protected abstract int onBindEasyHScrollViewId();

}
