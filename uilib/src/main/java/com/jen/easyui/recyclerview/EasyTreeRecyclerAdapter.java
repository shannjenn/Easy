package com.jen.easyui.recyclerview;

import android.content.Context;

import java.util.List;

/**
 * 树形模式(数据平铺:如level0 position=0，level1 position=1)
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeRecyclerAdapter<T extends EasyTreeItem> extends EasyTreeRecyclerAdapterManager<T> {


    /**
     * @param context
     * @param data
     */
    public EasyTreeRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected int getViewType(int level) {
        return 0;
    }

    @Override
    protected int[] onBindLayout() {
        return new int[0];
    }

    @Override
    protected float itemLeftSpace() {
        return 0;
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

}