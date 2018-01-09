package com.jen.easytest.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.EasyMain;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easyui.dialog.EasyLoading;

/**
 * EditText
 * 作者：ShannJenn
 * 时间：2017/01/09.
 */

public abstract class BaseFragment<T> extends Fragment implements HttpListener<T> {
    private View rootView;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(inflateLayout(), container, false);
            EasyMain.mBindView.inject(this, rootView);
            mLoading = new EasyLoading(getContext());
//            httpListener.setListenerImp(this);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        loadDataAfterView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
        mHandler = null;
    }

    protected abstract int inflateLayout();

    protected abstract void initViews();

    protected abstract void loadDataAfterView();

}
