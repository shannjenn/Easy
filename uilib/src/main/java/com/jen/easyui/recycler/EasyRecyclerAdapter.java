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

public abstract class EasyRecyclerAdapter<T> extends EasyRecyclerBaseAdapter<T> {
    private final String TAG = EasyRecyclerAdapter.class.getSimpleName();
    /**
     * @param data 数据
     */
    protected EasyRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EasyItemType.HEAD.getType() || viewType == EasyItemType.FOOT.getType()) {
            return super.onCreateViewHolder(parent, viewType);
        }
        int layout = onBindLayout();
        if (layout == 0) {
            Log.w(TAG,"找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        if (view == null) {
            Log.w(TAG,"找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        return bindHolder(view, EasyItemType.BODY);
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    protected abstract int onBindLayout();

}