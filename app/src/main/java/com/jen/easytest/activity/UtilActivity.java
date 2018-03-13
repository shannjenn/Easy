package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.util.DateFormat;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class UtilActivity extends EasyActivity {
    DateFormat format = new DateFormat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadDataAfterView() {

    }

    @EasyMouse.BIND.Method({R.id.dateFormat})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.dateFormat: {
                String dateStr = format.format(System.currentTimeMillis());
                String dateStr1 = format.format("1514554545415");

                EasyLog.d("DateFormat-------------");
                break;
            }
            default: {

                break;
            }
        }
    }

}
