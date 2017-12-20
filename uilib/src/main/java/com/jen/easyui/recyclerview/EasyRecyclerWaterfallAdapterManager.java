package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyUILog;

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
    protected EasyRecyclerWaterfallAdapterManager(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
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
        int[] layouts = onBindLayout();
        if (layouts == null) {
            EasyUILog.e("布局为空");
            return null;
        }
        if (viewType < 0 || layouts.length > viewType) {
            EasyUILog.e("viewType：" + viewType + "错误");
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            EasyUILog.e("找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return null;
        }
        return new EasyHolder(view);
    }

    protected abstract int[] onBindLayout();

}
