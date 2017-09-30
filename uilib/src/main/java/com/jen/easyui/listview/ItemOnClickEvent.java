package com.jen.easyui.listview;

import android.view.View;

/**
 * Adapter点击事件
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public interface ItemOnClickEvent {

    void onClick(View view, int pos);

    boolean onLongClick(View view, int pos);

    void onItemClick(View view, int pos);
}
