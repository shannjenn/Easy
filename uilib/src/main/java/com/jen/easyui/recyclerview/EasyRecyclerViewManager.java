package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jen.easyui.R;


/**
 * 作者：ShannJenn
 * 时间：2017/09/08.
 */

public abstract class EasyRecyclerViewManager extends RecyclerView {
    /*头部item数量*/
    private int headItemCount;
    /*底部item数量*/
    private int footItemCount;
    private RefreshListener refreshListener;
    private LoadMoreListener loadMoreListener;
    private int headerLayout = R.layout._easy_recycler_header;
    private int footLayout = R.layout._easy_recycler_foot;
    private boolean isRefreshing;
    private boolean isLoading;


    protected EasyRecyclerViewManager(Context context) {
        super(context);
        init(context);
    }

    protected EasyRecyclerViewManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        addOnScrollListener(onScrollListener);
        setOnTouchListener(onTouchListener);
    }

    /**
     * item布局是否固定大小
     *
     * @param hasFixedSize 是否固定大小
     */
    @Override
    public void setHasFixedSize(boolean hasFixedSize) {
        super.setHasFixedSize(hasFixedSize);
    }

    /**
     * 设置List布局
     *
     * @param orientation 排列方式(VERTICAL、HORIZONTAL)
     */
    protected void setLinearLayoutManager(int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(orientation);
        super.setLayoutManager(layoutManager);
    }

    /**
     * 设置Grid布局
     *
     * @param orientation 排列方式(VERTICAL、HORIZONTAL)
     * @param size        数量
     */
    protected void setGridLayoutManager(int orientation, int size) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), size);
        layoutManager.setOrientation(orientation);
        super.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        updateHeaderFooter();
    }

    /**
     * 下拉刷新
     */
    public interface RefreshListener {
        void onRefresh();
    }

    /**
     * 上拉加载更多
     */
    public interface LoadMoreListener {
        void onLoadMore();
    }

    protected void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    protected void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    protected void refreshFinish() {
        Adapter adapter = getAdapter();
        if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
            ((EasyRecyclerBaseAdapterManager) adapter).setHeaderVisible(false);
        }
        isRefreshing = false;
    }

    protected void loadMoreFinish() {
        Adapter adapter = getAdapter();
        if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
            ((EasyRecyclerBaseAdapterManager) adapter).setFootVisible(false);
        }
        isLoading = false;
    }

    protected void setHeadView(int headerLayout) {
        this.headerLayout = headerLayout;
        updateHeaderFooter();
    }

    protected void setFootView(int footLayout) {
        this.footLayout = footLayout;
        updateHeaderFooter();
    }

    protected int getHeadItemCount() {
        return headItemCount;
    }

    /**
     * 显示头部
     *
     * @param showHeader
     */
    protected void showHeader(boolean showHeader) {
        this.headItemCount = showHeader ? 1 : 0;
        updateHeaderFooter();
    }

    protected int getFootItemCount() {
        return footItemCount;
    }

    /**
     * 显示底部
     *
     * @param showFooter
     */
    protected void showFooter(boolean showFooter) {
        this.footItemCount = showFooter ? 1 : 0;
        updateHeaderFooter();
    }

    /**
     * 更新头部和尾部
     */
    private void updateHeaderFooter() {
        Adapter adapter = getAdapter();
        if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
            if (headItemCount > 0)
                ((EasyRecyclerBaseAdapterManager) adapter).mHeaderLayout = headerLayout;
            else
                ((EasyRecyclerBaseAdapterManager) adapter).mHeaderLayout = 0;
            if (footItemCount > 0)
                ((EasyRecyclerBaseAdapterManager) adapter).mFootLayout = footLayout;
            else
                ((EasyRecyclerBaseAdapterManager) adapter).mFootLayout = 0;
        }
    }

    /**
     * 滑动到顶部
     *
     * @param position
     */
    protected void scrollPositionToHeader(int position) {
        position += headItemCount;
        int count = getChildCount();
        int firstItem = getChildLayoutPosition(getChildAt(0));
        int lastItem = getChildLayoutPosition(getChildAt(count - 1));
        if (position < firstItem) {
            scrollToPosition(position);
        } else if (position <= lastItem) {
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < count) {
                int top = getChildAt(movePosition).getTop();
                scrollBy(0, top);
                if (footItemCount > 0) {
                    Adapter adapter = getAdapter();
                    if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
                        ((EasyRecyclerBaseAdapterManager) adapter).setFootVisible(false);
                    }
                }
            }
        } else {
            scrollToPosition(position);
        }
    }

    /**
     * 滚动监听,加载更多、下拉刷新
     */
    private OnScrollListener onScrollListener = new OnScrollListener() {
        int lastVisibleItem;
        int firstVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Adapter adapter = getAdapter();
            if (newState == RecyclerView.SCROLL_STATE_IDLE && firstVisibleItem == 0 && scrolledUp) {//顶部
                scrolledUp = false;
                if (isRefreshing) {
                    return;
                }
                if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
                    ((EasyRecyclerBaseAdapterManager) adapter).setHeaderVisible(true);
                    isRefreshing = true;
                }
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            } else if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {//底部
                if (isLoading) {
                    return;
                }
                if (adapter instanceof EasyRecyclerBaseAdapterManager) {
                    ((EasyRecyclerBaseAdapterManager) adapter).setFootVisible(true);
                    isLoading = true;
                }
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItem = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
        }
    };

    private boolean scrolledUp = false;//向上滑动
    private OnTouchListener onTouchListener = new OnTouchListener() {
        private float y1 = 0;
        private float y2 = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    if (y1 == 0)
                        y1 = event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    y2 = event.getY();
                    if (y2 - y1 > 10) {
                        scrolledUp = true;
                        y1 = 0;
                        y2 = 0;
                    }
                    break;
                }
                default: {

                    break;
                }
            }
            return false;
        }
    };

}
