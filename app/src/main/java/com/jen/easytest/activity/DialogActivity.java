package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easytest.R;
import easybase.EasyActivity;
import com.jen.easyui.dialog.EasyDialog;
import com.jen.easyui.dialog.EasyLoading;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class DialogActivity extends EasyActivity {

    
    EasyLoading loading;
    EasyDialog dialog;

    @Override
    public int bindView() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loading = new EasyLoading(this);
        dialog = EasyDialog.build(this)
//                .setIconLeft(getResources().getDrawable(R.mipmap.ic_launcher))
                .setTitle("提示哟哟")
                .setContent("这是一个Dialog测试")
                .setLeftButton("左边")
                .setRightButton("右边")
                .create();
    }

    @EasyBindClick({R.id.loading, R.id.dialog})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.loading: {
                loading.show();
                break;
            }
            case R.id.dialog: {
                dialog.show();
                break;
            }
            default: {

                break;
            }
        }
    }

}
