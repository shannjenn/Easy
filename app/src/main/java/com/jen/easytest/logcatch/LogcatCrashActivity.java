package com.jen.easytest.logcatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jen.easy.EasyBindClick;
import com.jen.easy.http.EasyHttp;
import com.jen.easy.http.EasyHttpTool;
import com.jen.easy.http.TestHttp;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.http.request.AirRequest;
import com.jen.easytest.http.request.AirRequest2;
import com.jen.easytest.http.request.PutRequest;
import com.jen.easytest.http.request.QNRequest;
import com.jen.easytest.http.response.StockQuotationResponse;
import com.jen.easytest.request.SystemParamRequest;
import com.jen.easyui.base.EasyActivity;

import org.json.JSONObject;

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
