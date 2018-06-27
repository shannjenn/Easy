package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.Easy;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.baseview.EasyTagEditText;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class DrawableActivity extends EasyActivity {

    @Easy.BIND.ID(R.id.easyTagEditText)
    EasyTagEditText easyTagEditText;

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
//        easyButon.setBackgroundColor(0xffff0000);
//        easyTagEditText.setTagTextSize(18);
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
                easyTagEditText.insertTag(easyTagEditText.getInputText());
                break;
            }
            default: {

                break;
            }
        }
    }
}
