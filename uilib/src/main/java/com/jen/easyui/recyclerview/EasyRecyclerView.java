package com.jen.easyui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 作者：ShannJenn
 * 时间：2017/09/08.
 */

public class EasyRecyclerView extends EasyRecyclerViewManager {
    public EasyRecyclerView(Context context) {
        super(context);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setHasFixedSize(boolean hasFixedSize) {
        super.setHasFixedSize(hasFixedSize);
    }

    @Override
    public void setLinearLayoutManager(int orientation) {
        super.setLinearLayoutManager(orientation);
    }

    @Override
    public void setGridLayoutManager(int orientation, int size) {
        super.setGridLayoutManager(orientation, size);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void scrollPositionToHeader(int position) {
        super.scrollPositionToHeader(position);
    }

    @Override
    public void setRefreshListener(RefreshListener refreshListener) {
        super.setRefreshListener(refreshListener);
    }

    @Override
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        super.setLoadMoreListener(loadMoreListener);
    }

    @Override
    public void refreshFinish() {
        super.refreshFinish();
    }

    @Override
    public void loadMoreFinish() {
        super.loadMoreFinish();
    }

    @Override
    public void setHeadView(int headerLayout) {
        super.setHeadView(headerLayout);
    }

    @Override
    public void setFootView(int footLayout) {
        super.setFootView(footLayout);
    }

    @Override
    public int getHeadItems() {
        return super.getHeadItems();
    }

    @Override
    public void showHeader(boolean showHeader) {
        super.showHeader(showHeader);
    }

    @Override
    public int getFootItems() {
        return super.getFootItems();
    }

    @Override
    public void showFooter(boolean showFooter) {
        super.showFooter(showFooter);
    }
}
