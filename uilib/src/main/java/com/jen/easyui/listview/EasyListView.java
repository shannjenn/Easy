package com.jen.easyui.listview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;
import java.util.Map;

/**
 * 列表控件
 * 作者：ShannJenn
 * 时间：2017/09/08.
 */

public class EasyListView extends RecyclerView {
    private EasyRecyclerAdapter adapter;
    private boolean isSheLayoutManager;

    public EasyListView(Context context) {
        super(context);
    }

    public EasyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*@Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        isSheLayoutManager = true;
    }*/

    /**
     * item布局是否固定大小
     *
     * @param hasFixedSize 是否固定大小
     */
    @Override
    public void setHasFixedSize(boolean hasFixedSize) {
        super.setHasFixedSize(hasFixedSize);
    }

    public <T> void setAdaper(List<T> datas, int layout) {
        if (!isSheLayoutManager) {
            setLinearLayoutManager(VERTICAL);
        }
        adapter = new EasyRecyclerAdapter(datas, layout);
        setAdapter(adapter);
    }

    public <T> void setAdaper(List<T> datas, Map<Integer, Integer> viewType_layouts) {
        if (!isSheLayoutManager) {
            setLinearLayoutManager(VERTICAL);
        }
        adapter = new EasyRecyclerAdapter(datas, viewType_layouts);
        setAdapter(adapter);
    }


    /**
     * 设置List布局
     *
     * @param orientation 排列方式(VERTICAL、HORIZONTAL)
     */
    public void setLinearLayoutManager(int orientation) {
        isSheLayoutManager = true;
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
        isSheLayoutManager = true;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), size);
        layoutManager.setOrientation(orientation);
        super.setLayoutManager(layoutManager);
    }
}
