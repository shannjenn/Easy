package com.jen.easyui.base;

import android.view.View;

import com.jen.easyui.R;

/**
 * 错误日志抓取
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */
public class LogCrashActivity<T> extends EasyActivity<T> {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout._easy_log_crash);
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

    @Override
    protected void onBindClick(View view) {

    }

    @Override
    public void httpSuccess(int flagCode, String flag, T response) {

    }

    @Override
    public void httpFail(int flagCode, String flag, String msg) {

    }
}