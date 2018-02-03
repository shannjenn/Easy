package com.jen.easyui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
        onBindView(holder.itemView, holder.getItemViewType(), t, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        boolean isSet = setSpanSize(0) <= 0;//如果没设置直接返回
        if (isSet) {
            return;
        }

        //为GridLayoutManager 合并头布局的跨度
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int total = gridLayoutManager.getSpanCount();
                    int spanSize = setSpanSize(position);
                    if (spanSize > total || spanSize <= 0) {
                        return total;
                    } else {
                        return spanSize;
                    }
                }
            });
        }
    }

    /**
     * ViewHolder
     */
    class EasyHolder extends RecyclerView.ViewHolder {
        EasyHolder(View itemView) {
            super(itemView);
            initListener();
        }

        private void initListener() {
            if (easyAdapterClickEvent == null) {
                return;
            }
            int[] clicks = bindClick();
            int[] longClicks = bindLongClick();
            boolean itemClick = bindItemClick();

            if (itemClick) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        easyAdapterClickEvent.onItemClick(v, getAdapterPosition());
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
                            easyAdapterClickEvent.onClick(v, getAdapterPosition());
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
                            return easyAdapterClickEvent.onLongClick(v, getAdapterPosition());
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
     * 设置合并头布局的跨度，只对GridLayoutManager有效
     *
     * @param position 下标
     * @return 跨行
     */
    protected abstract int setSpanSize(int position);

    /**
     * 初始化item数据
     *
     * @return
     */
    protected abstract void onBindView(View view, int viewType, T data, int pos);

    public void setEasyAdapterClickEvent(EasyAdapterClickEvent easyAdapterClickEvent) {
        this.easyAdapterClickEvent = easyAdapterClickEvent;
    }
}
