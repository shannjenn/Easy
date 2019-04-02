package com.jen.easyui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 说明：
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public abstract class EasyWindow extends EasyFactoryWindow {
    protected Build build;
    int selectPosition;

    public static Build build(Context context) {
        return new Build(context);
    }

    EasyWindow(Build build) {
        super(build.context);
        this.build = build;
        setContentView(bindContentView());
        setHeight(build.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : build.height);
        setWidth(build.width == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : build.width);
    }

    abstract View bindContentView();
    public abstract void setData(List data);

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    /**
     * 显示在底部
     */
    public void showAsBottom(View showView) {
        showAtLocation(showView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}
