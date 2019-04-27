package com.jen.easyui.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * list或者grid模式 带holder
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyHolderRecyclerBaseAdapter<T> extends EasyRecyclerAdapterFactory<T> {
    private final String TAG = EasyHolderRecyclerBaseAdapter.class.getSimpleName();

    /**
     * @param data 数据
     */
    public EasyHolderRecyclerBaseAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = onBindLayout();
        if (layout == 0) {
            Log.w(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        if (view == null) {
            Log.w(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        return bindHolder(view);
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    @Override
    protected EasyHolder bindHolder(View view) {
        return new MyHolder(this, view);
    }

    @Override
    public void onBindViewHolder(EasyHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    class MyHolder extends EasyHolder {
        public MyHolder(EasyRecyclerAdapterFactory adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {
            onBindHolderData(this, view, viewType, position);
        }
    }

    protected abstract int onBindLayout();

    protected abstract void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position);


}