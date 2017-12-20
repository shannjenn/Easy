package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * 树形模式
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeRecyclerAdapter<T extends EasyTreeItem> extends EasyTreeRecyclerAdapterManager<T> {

    /**
     * @param context
     * @param data
     */
    protected EasyTreeRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    @Override
    protected int[] onBindLayout() {
        return new int[0];
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
    protected void onBindView(View view, T data, int pos) {

    }

    @Override
    public void setEasyAdapterClickEvent(EasyAdapterClickEvent easyAdapterClickEvent) {
        super.setEasyAdapterClickEvent(easyAdapterClickEvent);
    }

    @Override
    protected float itemSpaceSize() {
        return 0;
    }
}