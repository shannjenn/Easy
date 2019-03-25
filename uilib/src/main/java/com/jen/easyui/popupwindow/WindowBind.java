package com.jen.easyui.popupwindow;

import android.view.View;

import com.jen.easyui.recycler.EasyHolder;

import java.util.List;

public interface WindowBind {

    int onBindItemLayout();

    void onBindItemData(EasyHolder easyHolder, View view, List data, int position);
}
