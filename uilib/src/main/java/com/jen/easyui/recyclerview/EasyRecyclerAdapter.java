package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.View;

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
    protected EasyRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected int onBindLayout() {
        return 0;
    }

    @Override
    protected int[] bindClick() {
        return new int[0];
    }

    @Override
    protected int[] bindLongClick() {
        return new int[0];
    }

    @Override
    protected boolean bindItemClick() {
        return false;
    }

    @Override
    protected int setSpanSize(int position) {
        return 0;
    }

    @Override
    protected void onBindView(View view, int viewType, T data, int pos) {

    }

    @Override
    public void setEasyAdapterClickEvent(EasyAdapterClickEvent easyAdapterClickEvent) {
        super.setEasyAdapterClickEvent(easyAdapterClickEvent);
    }
}
