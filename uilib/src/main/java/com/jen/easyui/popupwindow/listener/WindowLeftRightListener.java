package com.jen.easyui.popupwindow.listener;

import android.view.View;

public interface WindowLeftRightListener extends WindowListener {

    void windowRight(int flag, View view, int position);

    void windowLeft(int flag, View view, int position);

}
