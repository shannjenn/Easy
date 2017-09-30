package com.jen.easyui.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jen.easyui.EasyUILog;

/**
 * 作者：ShannJenn
 * 时间：2017/09/30.
 */

abstract class EasyHloderImp extends RecyclerView.ViewHolder {
    public View itemView;
    private ItemOnClickEvent itemOnClickEvent;

    public EasyHloderImp(final View itemView) {
        super(itemView);
        this.itemView = itemView;
        if (itemOnClickEvent == null) {
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
                    itemOnClickEvent.onItemClick(v, pos);
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
                        itemOnClickEvent.onClick(v, pos);
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
                        return itemOnClickEvent.onLongClick(v, pos);
                    }
                });
            }
        }
    }

    public abstract int[] getOnClick();

    public abstract int[] getOnLongClick();

    public abstract boolean getItemClick();

    void setItemOnClickEvent(ItemOnClickEvent itemOnClickEvent) {
        this.itemOnClickEvent = itemOnClickEvent;
    }

    void onBindViewHolder(int pos) {
        itemView.setTag(pos, null);//pos作为tag
        onBindEasyHolder(pos);
    }

    /**
     * 初始化item数据
     *
     * @return
     */
    public abstract boolean onBindEasyHolder(int pos);
}
