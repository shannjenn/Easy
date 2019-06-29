package com.jen.easyui.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 作者：ShannJenn
 * 时间：2017/09/08.
 */

public class EasyRecyclerView extends RecyclerView {

    public EasyRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
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
        super.setAdapter(adapter);
    }

}