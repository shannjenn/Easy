package com.jen.easyui.popupwindow.listener;

import android.view.View;

public interface WindowCancelSureListener extends WindowListener {

    void windowSure(int flag, View view, int position, Object object);

    void windowCancel(int flag, View view);

}
