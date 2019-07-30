package com.jen.easyui.recycler.listener;

import android.view.View;

/**
 * Adapter点击事件
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public abstract class EasyItemListener {

    public abstract void onItemClick(View view, int position);

    public void onViewClick(View view, int position) {
    }

    public boolean onViewLongClick(View view, int position) {
        return false;
    }
}
