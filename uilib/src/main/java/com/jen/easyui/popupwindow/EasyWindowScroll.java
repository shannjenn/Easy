package com.jen.easyui.popupwindow;

import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowCancelSureListener;
import com.jen.easyui.popupwindow.listener.WindowLeftRightListener;
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
    private List<String> data;

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
        } else if (!(data.get(0) instanceof String)) {
            EasyLog.e("setData错误,请设置String集合");
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        pick_string.setData(this.data);
        pick_string.setSelectedPosition(selectPosition);
    }

    @Override
    void clickLeftCallBack() {
        if (build.listener instanceof WindowLeftRightListener) {
            ((WindowLeftRightListener) build.listener).windowLeft(build.flagCode, showView, pick_string.getSelectedPosition());
        } else if (build.listener instanceof WindowCancelSureListener) {
            ((WindowCancelSureListener) build.listener).windowCancel(build.flagCode, showView);
        }
    }

    @Override
    void clickRightCallBack() {
        if (build.listener instanceof WindowLeftRightListener && data.size() > 0) {
            ((WindowLeftRightListener) build.listener).windowRight(build.flagCode, showView, pick_string.getSelectedPosition());
        } else if (build.listener instanceof WindowCancelSureListener) {
            if (selectPosition >= 0 && selectPosition < data.size()) {
                ((WindowCancelSureListener) build.listener).windowSure(build.flagCode, showView, selectPosition, data.get(selectPosition));
            }
        }
    }
}
