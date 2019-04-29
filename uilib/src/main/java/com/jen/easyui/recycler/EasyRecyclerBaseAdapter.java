package com.jen.easyui.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * list或者grid模式
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerBaseAdapter<T> extends EasyRecyclerAdapterFactory<T> {
    private final String TAG = EasyRecyclerBaseAdapter.class.getSimpleName();

    /**
     * @param data 数据
     */
    protected EasyRecyclerBaseAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = onBindLayout();
        if (layout == 0) {
            Log.e(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return super.onCreateViewHolder(parent, viewType);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        if (view == null) {
            Log.e(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return super.onCreateViewHolder(parent, viewType);
        }
        return bindHolder(view);
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    protected abstract int onBindLayout();

}