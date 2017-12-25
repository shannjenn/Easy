package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyUILog;

import java.util.List;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

abstract class EasyRecyclerBaseAdapterManager<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected List<T> mData;
    private EasyAdapterClickEvent easyAdapterClickEvent;

    /**
     * @param data 数据
     */
    EasyRecyclerBaseAdapterManager(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData == null) {
            return count;
        }
        count = mData.size();
        return count;
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Holder
     *
     * @return
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder == null) {
            return;
        }
        T t = mData.get(position);
        onBindView(holder.itemView, t, position);
    }

    /**
     * ViewHolder
     */
    class EasyHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public EasyHolder(final View itemView) {
            super(itemView);
            this.itemView = itemView;
            if (easyAdapterClickEvent == null) {
                return;
            }
            int[] clicks = bindClick();
            int[] longClicks = bindLongClick();
            boolean itemClick = bindItemClick();

            Object obj = itemView.getTag();
            if (obj == null || !(obj instanceof Integer)) {
                EasyUILog.e("itemView=" + itemView + " 点击事件失败，请检查是否重复设置该控件Tag");
            }
            final int pos = (int) obj;

            if (itemClick) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        easyAdapterClickEvent.onItemClick(v, pos);
                    }
                });
            }

            if (clicks != null) {
                for (int id : clicks) {
                    View view = itemView.findViewById(id);
                    if (view == null) {
                        EasyUILog.e("点击设置事件失败，请检查ID是否正确id=" + id);
                        continue;
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            easyAdapterClickEvent.onClick(v, pos);
                        }
                    });
                }
            }

            if (longClicks != null) {
                for (int id : longClicks) {
                    View view = itemView.findViewById(id);
                    if (view == null) {
                        EasyUILog.e("点击设置事件失败，请检查ID是否正确id=" + id);
                        continue;
                    }
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return easyAdapterClickEvent.onLongClick(v, pos);
                        }
                    });
                }
            }
        }
    }

    protected abstract int[] bindClick();

    protected abstract int[] bindLongClick();

    protected abstract boolean bindItemClick();

    /**
     * 初始化item数据
     *
     * @return
     */
    protected abstract void onBindView(View view, T data, int pos);

    public void setEasyAdapterClickEvent(EasyAdapterClickEvent easyAdapterClickEvent) {
        this.easyAdapterClickEvent = easyAdapterClickEvent;
    }
}
