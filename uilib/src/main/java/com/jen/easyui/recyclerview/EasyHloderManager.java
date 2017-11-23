package com.jen.easyui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jen.easy.log.EasyUILog;

/**
 * 作者：ShannJenn
 * 时间：2017/09/30.
 */

abstract class EasyHloderManager<T> extends RecyclerView.ViewHolder {
    public View itemView;
    private EasyAdapterClickEvent easyAdapterClickEvent;

    public EasyHloderManager(final View itemView) {
        super(itemView);
        this.itemView = itemView;
        if (easyAdapterClickEvent == null) {
            return;
        }
        int[] clicks = getOnClick();
        int[] longClicks = getOnLongClick();
        boolean itemClick = getItemClick();

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
            for (int i = 0; i < clicks.length; i++) {
                int id = clicks[i];
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
            for (int i = 0; i < longClicks.length; i++) {
                int id = longClicks[i];
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

    public abstract int[] getOnClick();

    public abstract int[] getOnLongClick();

    public abstract boolean getItemClick();

    void setAdapterClickEvent(EasyAdapterClickEvent easyAdapterClickEvent) {
        this.easyAdapterClickEvent = easyAdapterClickEvent;
    }

    void onBindViewHolder(T item, int pos) {
        itemView.setTag(pos);//pos作为tag
        onBindEasyHolder(item, pos);
    }

    /**
     * 初始化item数据
     *
     * @return
     */
    public abstract void onBindEasyHolder(T item, int pos);
}
