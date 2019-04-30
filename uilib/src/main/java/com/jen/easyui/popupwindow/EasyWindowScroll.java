package com.jen.easyui.popupwindow;

import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.view.loopview.StringScrollPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Scroll类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowScroll extends EasyWindow {
    private StringScrollPicker pick_string;
    private List<String> data;
    private int selectPosition;

    EasyWindowScroll(Build build) {
        super(build);
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_scroll, null);
        pick_string = popView.findViewById(R.id.pick_string);
        data = new ArrayList<>();
        data.add("");//默认有一个
        return popView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List data) {
        if (data == null || data.size() == 0) {
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        pick_string.setData(this.data);
        pick_string.setSelectedPosition(selectPosition);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public String getSelectString() {
        return pick_string.getSelectedItem().toString();
    }
}
