package com.jen.easyui.popupwindow.listener;

import android.view.View;

public interface WindowItemListener extends WindowListener {

    void itemClick(int flag, View view, int position, Object object);

}
