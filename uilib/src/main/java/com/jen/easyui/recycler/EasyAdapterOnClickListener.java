package com.jen.easyui.recycler;

import android.view.View;

/**
 * Adapter点击事件
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public interface EasyAdapterOnClickListener {

    void onClick(View view, int position);

    boolean onLongClick(View view, int position);

}
