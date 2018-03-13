package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;

import java.util.List;

/**
 * 瀑布布局（多种Item）
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyRecyclerWaterfallAdapterManager<T> extends EasyRecyclerBaseAdapterManager<T> {
    /**
     * @param data 数据
     */
    EasyRecyclerWaterfallAdapterManager(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_HEAD:
            case VIEW_TYPE_FOOT:
                return viewType;
        }
        return getViewType(position - mHeadItems);
    }

    /**
     * 获取布局类型
     *
     * @param position 下标
     * @return
     */
    protected abstract int getViewType(int position);

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEAD:
            case VIEW_TYPE_FOOT:
                return super.onCreateViewHolder(parent, viewType);
        }
        int[] layouts = onBindLayout();
        if (layouts == null) {
            EasyLog.w("布局为空");
            return null;
        }
        if (viewType < 0 || layouts.length < viewType) {
            EasyLog.w("viewType：" + viewType + "错误");
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            EasyLog.w("找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return null;
        }
        return new EasyHolder(view);
    }

    /**
     * 返回值对应viewType
     *
     * @return viewType
     */
    protected abstract int[] onBindLayout();

    @Override
    protected void onBindHeaderView(View view) {

    }

    @Override
    protected void onBindFooterView(View view) {

    }
}
