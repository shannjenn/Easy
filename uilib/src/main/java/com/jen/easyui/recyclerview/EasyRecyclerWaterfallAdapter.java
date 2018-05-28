package com.jen.easyui.recyclerview;

import android.content.Context;

import java.util.List;

/**
 * 瀑布布局（多种Item）
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerWaterfallAdapter<T> extends EasyRecyclerWaterfallAdapterManager<T> {
    /**
     * @param data 数据
     */
    public EasyRecyclerWaterfallAdapter(Context context, List<T> data) {
        super(context, data);
    }

    /**
     * @param position 对应BindLayout下标
     * @return
     */
    @Override
    protected int getViewType(int position) {
        return 0;
    }

    @Override
    protected int[] onBindLayout() {
        return new int[0];
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

}
