package com.jen.easyui.popupwindow.listener;

import android.view.View;

public interface WindowOkListener extends WindowListener {

    void windowRight(int flag, View view, int position, Object object);

    void windowLeft(int flag, View view, int position, Object object);

}
