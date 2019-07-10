package com.jen.easyui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easyui.R;

/**
 * 说明：
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */
public abstract class EasyWindow<T> extends EasyWindowFactory implements View.OnClickListener {
    private View mView;

    public static <T> Build<T> build(Context context) {
        return new Build<>(context);
    }

    public static TimePickerBuild buildTimerPicker(Context context) {
        return new TimePickerBuild(context);
    }

    EasyWindow(Build<T> build) {
        super(build);
        mView = bindView();
        updateTopBar();
        setContentView(mView);
//        if (build.height > 0) {//如果高度大于0，设置高度，小于等于0默认是ViewGroup.LayoutParams.WRAP_CONTENT
//            setHeight(build.height);
//        }
//        if (build.width > 0) {
//            setWidth(build.width);
//        }
        setHeight(build.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : build.height);
        setWidth(build.width == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : build.width);
    }

    abstract View bindView();

    public void showDropDown(View showView) {
        build.styleAnim = StyleAnim.DROP;
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
        build.styleAnim = StyleAnim.RIGHT;
        showAsDropDown(showView, x, y);
    }

    /**
     * 显示在底部
     */
    public void showCenter() {
        build.styleAnim = StyleAnim.BOTTOM;
        showAtLocation(getContentView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 显示在底部
     */
    public void showBottom() {
        build.styleAnim = StyleAnim.BOTTOM;
        showAtLocation(getContentView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void setFlagCode(int flagCode) {
        build.flagCode = flagCode;
    }

    @Override
    public void onClick(View v) {
        if (build.topBarListener == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.iv_left || i == R.id.tv_left) {
            build.topBarListener.windowLeft(build.flagCode);
        } else if (i == R.id.iv_right || i == R.id.tv_right) {
            build.topBarListener.windowRight(build.flagCode);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (build.dismissListener != null) {
            build.dismissListener.dismiss(build.flagCode);
        }
    }

    public void updateTopBar() {
        View rl_top_bar = mView.findViewById(R.id.layout_top_bar);
        if (!build.showTopBar) {
            rl_top_bar.setVisibility(View.GONE);
            return;
        }
        rl_top_bar.setVisibility(View.VISIBLE);
        ImageView iv_left = mView.findViewById(R.id.iv_left);
        TextView tv_left = mView.findViewById(R.id.tv_left);
        TextView tv_title = mView.findViewById(R.id.tv_title);
        ImageView iv_right = mView.findViewById(R.id.iv_right);
        TextView tv_right = mView.findViewById(R.id.tv_right);

        iv_left.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        if (build.styleTopBar == null) {
            build.styleTopBar = new StyleTopBar();
        }
        rl_top_bar.setBackgroundColor(build.styleTopBar.getBackgroundColor());
        tv_left.setTextSize(build.styleTopBar.getLeftTextSize());
        tv_title.setTextSize(build.styleTopBar.getTitleTextSize());
        tv_right.setTextSize(build.styleTopBar.getRightTextSize());

        tv_left.setTextColor(build.styleTopBar.getLeftTextColor());
        tv_title.setTextColor(build.styleTopBar.getTitleTextColor());
        tv_right.setTextColor(build.styleTopBar.getRightTextColor());

        switch (build.styleTopBar.getShowLeft()) {
            case IMAGE:
                iv_left.setVisibility(View.VISIBLE);
                tv_left.setVisibility(View.GONE);
                break;
            case TEXT:
                iv_left.setVisibility(View.GONE);
                tv_left.setVisibility(View.VISIBLE);
                tv_left.setText(build.styleTopBar.getLeftText());
                break;
            case NON:
                iv_left.setVisibility(View.GONE);
                tv_left.setVisibility(View.GONE);
                break;
        }
        switch (build.styleTopBar.getShowTitle()) {
            case TEXT:
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText(build.styleTopBar.getTitleText());
                break;
            case NON:
                tv_title.setVisibility(View.GONE);
                break;
        }
        switch (build.styleTopBar.getShowRight()) {
            case IMAGE:
                iv_right.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.GONE);
                break;
            case TEXT:
                iv_right.setVisibility(View.GONE);
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText(build.styleTopBar.getRightText());
                break;
            case NON:
                iv_right.setVisibility(View.GONE);
                tv_right.setVisibility(View.GONE);
                break;
        }
    }
}
