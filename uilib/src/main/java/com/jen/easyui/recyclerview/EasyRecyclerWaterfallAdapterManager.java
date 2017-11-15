package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

abstract class EasyRecyclerWaterfallAdapterManager<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;
    protected List<T> data;
    private EasyAdapterClickEvent easyAdapterClickEvent;

    /**
     * @param data 数据
     */
    protected EasyRecyclerWaterfallAdapterManager(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (data == null) {
            return count;
        }
        count = data.size();
        return count;
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
    public EasyHloderManager onCreateViewHolder(ViewGroup parent, int viewType) {
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
        EasyHloderManager hloderImp = onCreateEasyHolder(view);
        hloderImp.setAdapterClickEvent(easyAdapterClickEvent);
        return hloderImp;
    }

    protected abstract int[] onBindLayout();

    /**
     * Holder
     *
     * @return
     */
    protected abstract EasyHloderManager onCreateEasyHolder(View view);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        T t = data.get(position);
        ((EasyHloderManager) holder).onBindViewHolder(t, position);
    }

    public void setEasyAdapterClickEvent(EasyAdapterClickEvent easyAdapterClickEvent) {
        this.easyAdapterClickEvent = easyAdapterClickEvent;
    }
}