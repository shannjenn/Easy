package com.jen.easyui.popupwindow.listener;

import android.view.View;

public interface WindowOkListener extends WindowListener {

    void ok(int flag, View view, int position, Object object);

    void cancel(int flag, View view);

}
