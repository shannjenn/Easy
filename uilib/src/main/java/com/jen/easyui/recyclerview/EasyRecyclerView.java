package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jen.easyui.R;

/**
 * 作者：ShannJenn
 * 时间：2017/09/08.
 */

public class EasyRecyclerView extends RecyclerView {
    private boolean showHeader;
    private boolean showFoot;
    private RefreshListener refreshListener;
    private LoadMoreListener loadMoreListener;
    protected int mHeaderLayout = R.layout._easy_recycler_header;
    protected int mFootLayout = R.layout._easy_recycler_foot;
    private boolean isRefreshing;
    private boolean isLoading;

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

    public EasyRecyclerView(Context context) {
        super(context);
        init();
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addOnScrollListener(onScrollListener);
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
    public void setLinearLayoutManager(int orientation) {
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
    public void setGridLayoutManager(int orientation, int size) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), size);
        layoutManager.setOrientation(orientation);
        super.setLayoutManager(layoutManager);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        int lastVisibleItem;
        int firstVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Adapter adapter = getAdapter();
            if (newState == RecyclerView.SCROLL_STATE_IDLE && firstVisibleItem == 0) {//顶部
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
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {//底部
                if (isLoading) {
                    return;
                }
                if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
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
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
            if (showHeader)
                ((EasyRecyclerBaseAdapterManager) adapter).mHeaderLayout = mHeaderLayout;
            if (showFoot)
                ((EasyRecyclerBaseAdapterManager) adapter).mFootLayout = mFootLayout;
        }
    }

    /**
     * 必须设置showhead 为true
     *
     * @param refreshListener
     */
    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    /**
     * showFoot 为true
     *
     * @param loadMoreListener
     */
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void refreshFinish() {
        Adapter adapter = getAdapter();
        if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
            ((EasyRecyclerBaseAdapterManager) adapter).setHeaderVisible(false);
        }
        isRefreshing = false;
    }

    public void loadMoreFinish() {
        Adapter adapter = getAdapter();
        if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
            ((EasyRecyclerBaseAdapterManager) adapter).setFootVisible(false);
        }
        isLoading = false;
    }

    public void setHeadView(int headerLayout) {
        mHeaderLayout = headerLayout;
        if (showHeader) {
            Adapter adapter = getAdapter();
            if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
                ((EasyRecyclerBaseAdapterManager) adapter).mHeaderLayout = mHeaderLayout;
            }
        }
    }

    public void setFootView(int footLayout) {
        mFootLayout = footLayout;
        Adapter adapter = getAdapter();
        if (showHeader) {
            if (adapter != null && adapter instanceof EasyRecyclerBaseAdapterManager) {
                ((EasyRecyclerBaseAdapterManager) adapter).mFootLayout = mFootLayout;
            }
        }
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public void setShowFoot(boolean showFoot) {
        this.showFoot = showFoot;
    }
}
