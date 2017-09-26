package com.jen.easyui.listview;

import android.view.MotionEvent;
import android.view.View;

/**
 * Adapter点击事件
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public interface AdapterClickEvent {

    void onClick(View view);

    boolean onLongClick(View view);

    boolean onTouch(View view, MotionEvent motionEvent);

    void onItemClick(View view, int pos);
}
