package com.jen.easyui.popupwindow;

import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowOkListener;
import com.jen.easyui.view.baseview.EasyTopBar;
import com.jen.easyui.view.loopview.StringScrollPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Scroll类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowScroll extends EasyWindow implements View.OnClickListener {
    StringScrollPicker pick_string;

    EasyWindowScroll(Build build) {
        super(build);
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_scroll, null);
        EasyTopBar topBar = popView.findViewById(R.id.topBar);
        StringScrollPicker pick_string = popView.findViewById(R.id.pick_string);
        topBar.setVisibility(build.showTopBar ? View.VISIBLE : View.GONE);
        topBar.getRightText().setOnClickListener(this);
        topBar.getLeftImageView().setOnClickListener(this);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < build.data.size(); i++) {
            if (build.data.get(0) instanceof String) {
                list.add((String) build.data.get(0));
            }
        }
        pick_string.setData(list);

        return popView;
    }

    @Override
    void setData(List data) {
        build.data.clear();
        if (data != null && data.size() > 0) {
            build.data.addAll(data);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < build.data.size(); i++) {
                if (build.data.get(0) instanceof String) {
                    list.add((String) build.data.get(0));
                }
            }
            pick_string.setData(list);
        }
    }

    @Override
    public void onClick(View v) {
        WindowOkListener okListener;
        if (!(build.listener instanceof WindowOkListener)) {
            return;
        }
        okListener = (WindowOkListener) build.listener;
        int i = v.getId();
        if (i == R.id.top_bar_tv_right) {
            okListener.ok(build.flagCode, showView, pick_string.getSelectedPosition(), pick_string.getSelectedItem().toString());
        } else if (i == R.id.top_bar_iv_close) {
            okListener.cancel(build.flagCode, showView);
        }
    }

}
