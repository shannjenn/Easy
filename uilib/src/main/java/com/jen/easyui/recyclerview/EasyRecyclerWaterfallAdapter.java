package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.View;

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
    protected EasyRecyclerWaterfallAdapter(Context context, List<T> data) {
        super(context, data);
    }

    /**
     * @param position 下标(对应onBindLayout返回数组下标：0、1、2...)
     * @return
     */
    @Override
    protected abstract int getViewType(int position);

    @Override
    protected abstract int[] onBindLayout();

    @Override
    protected abstract EasyHloder onCreateEasyHolder(View view);
}
