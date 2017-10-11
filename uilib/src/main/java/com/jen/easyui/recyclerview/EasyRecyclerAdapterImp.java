package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.EasyUILog;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyRecyclerAdapterImp<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;
    protected List<T> data;
    private EasyItemOnClickEvent itemOnClickEvent;

    /**
     * @param data 数据
     */
    protected EasyRecyclerAdapterImp(Context context, List<T> data) {
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
    public EasyHloderImp onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = onBindLayout();
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        if (view == null) {
            EasyUILog.e("找不到该值对应item布局R.layout.id：" + layout);
            return null;
        }
        EasyHloderImp hloderImp = onCreateEasyHolder(view);
        hloderImp.setItemOnClickEvent(itemOnClickEvent);
        return hloderImp;
    }

    protected abstract int onBindLayout();

    /**
     * Holder
     *
     * @return
     */
    protected abstract EasyHloderImp onCreateEasyHolder(View view);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        T t = data.get(position);
        ((EasyHloderImp) holder).onBindViewHolder(t, position);
    }

    public void setItemOnClickEvent(EasyItemOnClickEvent itemOnClickEvent) {
        this.itemOnClickEvent = itemOnClickEvent;
    }

}
