package com.jen.easytest.logcatch;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.exception.HttpLog;
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
    protected void initViews() {
        HttpLog.d("打印测试=======================================================" + " 请求中断。\n \t");//\n\t打印才出现空行
        HttpLog.d("打印测试=======================================================" + " 请求中断。\n          ");//\n\t打印才出现空行
        HttpLog.d("打印测试=======================================================" + " 请求中断。\n           ");//\n\t打印才出现空行
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
