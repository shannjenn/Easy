package com.jen.easyui.popupwindow;

import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowOkListener;
import com.jen.easyui.view.loopview.StringScrollPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Scroll类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowScroll extends EasyWindow {
    private StringScrollPicker pick_string;

    EasyWindowScroll(Build build) {
        super(build);
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_scroll, null);
        pick_string = popView.findViewById(R.id.pick_string);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < build.data.size(); i++) {
            if (build.data.get(i) instanceof String) {
                list.add((String) build.data.get(i));
            }
        }
        pick_string.setData(list);
        return popView;
    }

    @Override
    public void setData(List data) {
        build.data.clear();
        if (data != null && data.size() > 0) {
            build.data.addAll(data);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < build.data.size(); i++) {
                if (build.data.get(i) instanceof String) {
                    list.add((String) build.data.get(i));
                }
            }
            pick_string.setData(list);
            pick_string.setSelectedPosition(selectPosition);
        }
    }

    @Override
    void clickLeftCallBack() {
        if (build.listener instanceof WindowOkListener && build.data.size() > 0) {
            ((WindowOkListener) build.listener).windowLeft(build.flagCode, showView, pick_string.getSelectedPosition(), pick_string.getSelectedItem().toString());
        }
    }

    @Override
    void clickRightCallBack() {
        if (build.listener instanceof WindowOkListener && build.data.size() > 0) {
            ((WindowOkListener) build.listener).windowRight(build.flagCode, showView, pick_string.getSelectedPosition(), pick_string.getSelectedItem().toString());
        }
    }
}
