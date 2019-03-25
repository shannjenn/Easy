package com.jen.easyui.popupwindow.listener;

import android.view.View;

import java.util.List;

public interface WindowItemListener extends WindowListener{
    void onClickItem(int flag, View view, Object item, int position);
}
