package com.jen.easytest.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.Easy;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.baseview.EasyTag;
import com.jen.easyui.baseview.EasyTagEditText;
import com.jen.easyui.popupwindow.EasyPopupWindow;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class DrawableActivity extends EasyActivity {

    @Easy.BIND.ID(R.id.easyTagEditText)
    EasyTagEditText easyTagEditText;

    EasyPopupWindow easyPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
        easyPopupWindow = new EasyPopupWindow(mContext);
        View popView = LayoutInflater.from(this).inflate(R.layout._easy_dialog, null);
        easyPopupWindow.setContentView(popView);
        easyPopupWindow.setWidth(500);
        easyPopupWindow.setHeight(300);
        easyPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        easyPopupWindow.setOutsideTouchable(true);
        easyPopupWindow.setFocusable(true);
//        easyButon.setBackgroundColor(0xffff0000);
//        easyTagEditText.setTagTextSize(18);
        easyTagEditText.setTagBackgroundColor(0xffff0000);
        easyTagEditText.setTagStrokeColor(0xffffff00);
        easyTagEditText.setTagStrokeWidth(2);
        easyTagEditText.setEasyTagListener(new EasyTagEditText.EasyTagListener() {
            @Override
            public void inputTextChanged(int flag, String inputText) {

            }

            @Override
            public void removeTags(int flag, List<String> tags) {

            }

            @Override
            public void onLongClick(int flag, EasyTagEditText easyTagEditText, EasyTag easyTag, float x, float y) {
                EasyLog.d("onLongClick ===========================================");
//                easyTagEditText.removeTag(easyTag);
            }

        });
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

    @Override
    public void success(int flagCode, String flag, Object response) {

    }

    @Override
    public void fail(int flagCode, String flag, String msg) {

    }

    @Easy.BIND.Method({R.id.button})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                easyTagEditText.insertTag();
                break;
            }
            default: {

                break;
            }
        }
    }
}
