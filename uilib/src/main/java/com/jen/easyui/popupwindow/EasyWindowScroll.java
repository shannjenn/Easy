package com.jen.easyui.popupwindow;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

class EasyWindowScroll extends EasyWindow implements View.OnClickListener {
    private StringScrollPicker pick_string;

    EasyWindowScroll(Build build) {
        super(build);
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_scroll, null);
        View rl_top_bar = popView.findViewById(R.id.rl_top_bar);
        pick_string = popView.findViewById(R.id.pick_string);
        ImageView iv_left = popView.findViewById(R.id.iv_left);
        TextView tv_title = popView.findViewById(R.id.tv_title);
        TextView tv_right = popView.findViewById(R.id.tv_right);

        rl_top_bar.setVisibility(build.showTopBar ? View.VISIBLE : View.GONE);
        tv_title.setText(build.topBarTitleText);
        tv_right.setText(build.topBarRightText);

        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

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
        }
    }

    @Override
    public void onClick(View v) {
        WindowOkListener okListener;
        if (!(build.listener instanceof WindowOkListener) || build.data.size() == 0) {
            return;
        }
        okListener = (WindowOkListener) build.listener;
        int i = v.getId();
        if (i == R.id.tv_right) {
            okListener.sure(build.flagCode, showView, pick_string.getSelectedPosition(), pick_string.getSelectedItem().toString());
        } else if (i == R.id.iv_left) {
            okListener.cancel(build.flagCode, showView);
        }
    }

}
