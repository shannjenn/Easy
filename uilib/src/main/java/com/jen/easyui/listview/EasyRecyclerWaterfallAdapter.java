package com.jen.easyui.listview;

import android.view.View;

import java.util.List;

/**
 * 瀑布布局（多种Item）
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerWaterfallAdapter<T> extends EasyRecyclerWaterfallAdapterImp<T> {
    /**
     * @param data 数据
     */
    protected EasyRecyclerWaterfallAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected abstract int getViewType(int position);

    @Override
    protected abstract int[] onBindLayout();

    @Override
    protected abstract EasyHloder onCreateEasyHolder(View view);
}
