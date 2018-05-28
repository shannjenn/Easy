package com.jen.easyui.recyclerview;

import android.content.Context;

import java.util.List;

/**
 * list或者grid模式
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerAdapter<T> extends EasyRecyclerAdapterManager<T> {
    /**
     * @param context
     * @param data    数据
     */
    public EasyRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected int onBindLayout() {
        return 0;
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

}
