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

public class EasyWindowScroll<T> extends EasyWindow<T> {
    private StringScrollPicker pick_string;
    private List<String> data;

    EasyWindowScroll(Build<T> build) {
        super(build);
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_scroll, null);
        pick_string = popView.findViewById(R.id.pick_string);
        data = new ArrayList<>();
        data.add("");//默认有一个
        return popView;
    }

    public void setData(List<String> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        pick_string.setData(this.data);
        pick_string.setSelectedPosition(0);//默认选中第一个
    }

    public List<String> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public int getSelectPosition() {
        return pick_string.getSelectedPosition();
    }

    public void setSelectPosition(int selectPosition) {
        pick_string.setSelectedPosition(selectPosition);
    }

    public String getSelectString() {
        return pick_string.getSelectedItem().toString();
    }
}
