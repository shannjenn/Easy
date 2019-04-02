package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class OnclickTestActivity extends EasyActivity {

    @EasyBindId(R.id.tv_onclickTest)
    TextView tv_onclickTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_test);
    }


    @Override
    protected void initViews() {

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
