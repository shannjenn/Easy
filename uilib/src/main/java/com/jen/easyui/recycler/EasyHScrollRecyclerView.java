package com.jen.easyui.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 */

public class EasyHScrollRecyclerView extends RecyclerView {

    public EasyHScrollRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public EasyHScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
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

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof EasyHScrollRecyclerViewAdapter) {
            super.setAdapter(adapter);
        } else {
            try {
                throw new RuntimeException("未引用：" + EasyHScrollRecyclerViewAdapter.class.getName());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollAllItem(EasyHScrollRecyclerView.this.getAdapter());
    }

    private void scrollAllItem(Adapter adapter) {
        if (adapter instanceof EasyHScrollRecyclerViewAdapter) {
            EasyHScrollRecyclerViewAdapter easyHScrollRecyclerViewAdapter = (EasyHScrollRecyclerViewAdapter) adapter;
            ArrayList hScrollViews = easyHScrollRecyclerViewAdapter.getHScrollViews();
            for (int i = 0; i < hScrollViews.size(); i++) {
                EasyHScrollView view = (EasyHScrollView) hScrollViews.get(i);
                if (view.getScrollX() != easyHScrollRecyclerViewAdapter.getScrollX()) {
                    view.scrollTo(easyHScrollRecyclerViewAdapter.getScrollX(), 0);
                }
            }
        }
    }
}