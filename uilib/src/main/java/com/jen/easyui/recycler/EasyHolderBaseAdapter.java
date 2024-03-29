package com.jen.easyui.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public abstract class EasyHolderBaseAdapter<T> extends EasyAdapterFactory<T> {
    private final String TAG = EasyHolderBaseAdapter.class.getSimpleName();

    public EasyHolderBaseAdapter(Context context) {
        super(context);
    }

    public EasyHolderBaseAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = onBindLayout();
        if (layout == 0) {
            Log.e(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return super.createViewHolder(parent, viewType);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        if (view == null) {
            Log.e(TAG, "找不到该值对应item布局R.layout.id：" + layout);
            return super.createViewHolder(parent, viewType);
        }
        return bindHolder(view);
    }

    @Override
    public void onBindViewHolder(EasyHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected int gridLayoutItemRows(int position) {
        return 0;
    }

    @Override
    protected EasyHolder bindHolder(View view) {
        return new MyHolder(this, view);
    }

    class MyHolder extends EasyHolder {
        MyHolder(EasyAdapterFactory adapter, View itemView) {
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