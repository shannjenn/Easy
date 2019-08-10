package com.jen.easytest.logcatch;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.http.EasyHttp;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;

import easybase.EasyActivity;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class LogcatCrashActivity extends EasyActivity {
    EasyHttp http = new EasyHttp(5);//设置请求最大线程数量值5
    String bt_crash_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_crash);
//        EasyMain.mHttp.setDefaultBaseListener(httpListener);
    }

    @Override
    public int bindView() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LogcatCrashManager.getIns().start();
//        bt_crash_error.length();
    }


    @EasyBindClick({R.id.bt_crash_error, R.id.bt_crash_log})
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.bt_crash_error:
                bt_crash_error.length();
                break;
            case R.id.bt_crash_log:
                EasyLog.e(".............");
                break;
        }
    }


}
