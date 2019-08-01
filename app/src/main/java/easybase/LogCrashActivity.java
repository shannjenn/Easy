package easybase;

import android.os.Bundle;
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
    public int bindView() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onBindClick(View view) {

    }

}
