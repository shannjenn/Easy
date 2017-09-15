package com.jen.easyui.listview;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/8/25.
 */

public interface AdapterClickEvent {

    void onClick(View view);

    boolean onLongClick(View view);

    boolean onTouch(View view, MotionEvent motionEvent);

    void onItemClick(View view, int pos);
}
