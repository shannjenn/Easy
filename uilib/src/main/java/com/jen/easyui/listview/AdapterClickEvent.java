package com.jen.easyui.listview;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2017/8/25.
 */

public class AdapterClickEvent implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, AdapterView.OnItemClickListener {

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
