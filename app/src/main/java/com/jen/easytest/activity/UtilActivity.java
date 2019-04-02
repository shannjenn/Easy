package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.util.EasyDateFormatUtil;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class UtilActivity extends EasyActivity {
    EasyDateFormatUtil format = new EasyDateFormatUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
    }

    @Override
    protected void initViews() {

    }

    @EasyBindClick({R.id.dateFormat})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.dateFormat: {
                String dateStr = format.format(System.currentTimeMillis());
                String dateStr1 = format.format("1514554545415");

                EasyLog.d("EasyDateFormatUtil-------------");
                break;
            }
            default: {

                break;
            }
        }
    }

}
