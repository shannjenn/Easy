package com.jen.easyui.popupwindow.listener;

import android.view.View;

import java.util.List;

public interface WindowOkListener extends WindowListener {
    void confirm(int flag, View view, List data, int position);
}
