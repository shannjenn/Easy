package com.jen.easyui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;

import java.util.List;

/**
 * 说明：
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public abstract class EasyWindow extends EasyFactoryWindow {
    protected Build build;
    int selectPosition;
    protected View showView;

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

    public void showDropDown(View showView) {
        this.showView = showView;
        build.animStyle = AnimStyle.DROP;
        showAsDropDown(showView);
    }

    /**
     * 显示在右边
     */
    public void showRight(View showView) {
        showRight(showView, 0, 0);
    }

    /**
     * 显示在右边
     */
    public void showRight(View showView, int x, int y) {
        this.showView = showView;
        build.animStyle = AnimStyle.RIGHT;
        showAsDropDown(showView, x, y);
    }

    /**
     * 显示在底部
     */
    public void showBottom(View showView) {
        this.showView = showView;
        build.animStyle = AnimStyle.BOTTOM;
        showAtLocation(showView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public void setFlagCode(int flagCode) {
        build.flagCode = flagCode;
    }

    @Override
    protected int animation() {
        int style;
        switch (build.animStyle) {
            case BOTTOM:
                style = R.style.easy_popup_window_show_bottom_anim_style;
                break;
            case DROP:
                style = R.style.easy_popup_window_drop_down_anim_style;
                break;
            case RIGHT:
                style = R.style.easy_popup_window_show_right_anim_style;
                break;
            default:
                style = R.style.easy_popup_window_show_bottom_anim_style;
                break;
        }
        return style;
    }
}
