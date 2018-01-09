package com.jen.easytest.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jen.easy.EasyMain;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easyui.dialog.EasyLoading;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements HttpListener<T> {
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private EasyLoading mLoading;

    /**
     * 网络请求监听(网络基本数据请求用此监听)
     */
    private HttpBaseListener<T> mHttpListener = new HttpBaseListener<T>() {
        @Override
        public void success(final int flagCode, final String flag, final T response) {
            if (mHandler == null) {
                return;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    httpSuccess(flagCode, flag, response);
                }
            });
        }

        @Override
        public void fail(final int flagCode, final String flag, final String msg) {
            if (mHandler == null) {
                return;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    httpFail(flagCode, flag, msg);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
//        setContentView(setLayout());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        EasyMain.mBindView.bind(this);
        mLoading = new EasyLoading(mContext);

        intDataBeforeView();
        initViews();
        loadDataAfterView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.mBindView.unbind(this);
        mHandler.removeMessages(0);
        mHandler = null;
    }

    /**
     * @return 布局，如：R.layout.main
     */
//    protected abstract int setLayout();
    protected abstract void intDataBeforeView();

    protected abstract void initViews();

    protected abstract void loadDataAfterView();

    protected abstract void onBindClick(View view);
}
