package com.jen.easyui.recycler.listener;

import android.view.View;

/**
 * Adapter点击事件
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public interface EasyAdapterListenerB  extends EasyAdapterListenerA {

//    void onItemClick(View view, int position);

//    void onViewClick(View view, int position);

    boolean onViewLongClick(View view, int position);

}
