package com.jen.easytest.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.EasyViewID;
import com.jen.easy.EasyViewMethod;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.popupwindow.EasyPopupWindow;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class PopupWindowActivity extends EasyActivity {

    EasyPopupWindow mEasyPopupWindow;

    @EasyViewID(R.id.popup_line)
    View popup_line;
    @EasyViewID(R.id.popup_window)
    View popup_window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
        mEasyPopupWindow = new EasyPopupWindow(this);

        View popView = LayoutInflater.from(this).inflate(R.layout._easy_dialog, null);
        mEasyPopupWindow.setContentView(popView);
        mEasyPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mEasyPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mEasyPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        mEasyPopupWindow.setOutsideTouchable(true);
        mEasyPopupWindow.setFocusable(true);
    }

    @Override
    protected void loadDataAfterView() {

    }

    @EasyViewMethod({R.id.popup_window})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.popup_window: {
//                mEasyPopupWindow.showAsDropDown(popup_line);
                mEasyPopupWindow.showAtLocation(popup_window, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            }
            default: {

                break;
            }
        }
    }

    @Override
    public void success(int flagCode, String flag, Object response) {

    }

    @Override
    public void fail(int flagCode, String flag, String msg) {

    }
}
