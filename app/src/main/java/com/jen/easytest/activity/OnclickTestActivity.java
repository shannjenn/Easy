package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import easybase.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class OnclickTestActivity extends EasyActivity {

    @EasyBindId(R.id.tv_onclickTest)
    TextView tv_onclickTest;

    @Override
    public int bindView() {
        return R.layout.activity_onclick_test;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @EasyBindClick({R.id.tv_onclickTest})
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.tv_onclickTest: {
                EasyLog.d("tv_onclickTest");
                break;
            }
            default: {

                break;
            }
        }
    }

}
