package com.jen.easyui.base;

import com.jen.easy.http.EasyHttp;
import com.jen.easy.http.request.EasyHttpRequest;
import com.jen.easy.http.imp.EasyHttpDataListener;

import java.util.List;
import java.util.Map;

/**
 * @author Created by zs on 2018/10/22.
 */
public class HttpManager extends EasyHttp implements EasyHttpDataListener {
    private EasyActivity mActivity;
    /*网络请求同时最大请求数量*/
    private static final int MAX_THREAD = 5;
    private HttpListener httpListener;
    private boolean showToast = true;//默认显示
    private boolean showWaitDialog = true;//默认显示
    private boolean dialogCanDismiss = true;//不允许点击外面消失
    private int requestSizes;

//    public final static int CODE_ERROR_HINT_MINUS_2 = -2;
//    public final static int CODE_ERROR_HINT_903 = 903;

    public HttpManager(EasyActivity activity) {
        super(MAX_THREAD);
        this.mActivity = activity;
        setDataListener(this);
    }

    private HttpManager(int i) {
        super(i);
    }

    /*public static HttpManager getClone() {
        HttpManager manager = new HttpManager(MAX_THREAD);
        manager.setDefaultBaseListener(this);
        return manager;
    }*/

    @Override
    public void start(EasyHttpRequest httpRequest) {
        /*if (!EasyApplication.getAppContext().isNetworkOnline()) {//未连网
            toast(R.string.network_offline);
        } else {
            requestSizes++;
            showWaitDialog();
            super.start(httpRequest);
        }*/
    }

    @Override
    public void success(int flagCode, String flagStr, Object responseBody, Map<String, List<String>> headMap) {
        requestSizes--;
        if (responseBody instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) responseBody;
            int code = response.getCode();
            if (httpListener == null) {

            } else if (code == 0) {
//                HttpDataManager.getIns().updateCache(flagCode, flagStr, response);//调用数据逻辑处理层
                httpListener.HttpSuccess(flagCode, flagStr, response);
            } else {
                String msg = response.getMessage();
                toast(msg);
            /*switch (code) {
                case CODE_ERROR_HINT_MINUS_2:
                case CODE_ERROR_HINT_903: {
                    toast(msg);
                    break;
                }
                default:
                    break;
            }*/
                httpListener.httpFail(flagCode, flagStr, response.getMessage());
            }
        } else {
            httpListener.httpFail(flagCode, flagStr, "");
        }
        dismissWaitDialog();
    }

    @Override
    public void fail(int flagCode, String flagStr, String msg) {
        requestSizes--;
        if (httpListener != null) {
            httpListener.httpFail(flagCode, flagStr, msg);
        }
        dismissWaitDialog();
    }

    public interface HttpListener {
        void HttpSuccess(int flagCode, String flagStr, BaseResponse response);

        void httpFail(int flagCode, String flagStr, String msg);
    }

    public void setHttpListener(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    /*if (!XGApplication.getApplication().isNetworkOnline()) {
        if (listener != null) {
            listener.onErrorCode(Constant.EVENT_NETWORK_OFFLINE, UIUtil.getString(R.string.network_offline), null);
        }
        return null;
    }*/

    @Override
    public void shutdown() {
        super.shutdown();
        mActivity = null;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public void setShowWaitDialog(boolean showWaitDialog) {
        this.showWaitDialog = showWaitDialog;
    }

    public void setDialogCanDismiss(boolean dialogCanDismiss) {
        this.dialogCanDismiss = dialogCanDismiss;
    }

    private void showWaitDialog() {
        if (mActivity != null && mActivity.mHandler != null && showWaitDialog) {
            mActivity.mHandler.post(new Runnable() {
                @Override
                public void run() {
//                    if (mActivity != null)
//                        mActivity.showWaitDialog(dialogCanDismiss);
                }
            });
        }
    }

    private void dismissWaitDialog() {
        if (mActivity != null && mActivity.mHandler != null && requestSizes <= 0)
            mActivity.mHandler.post(new Runnable() {
                @Override
                public void run() {
//                    if (mActivity != null)
//                        mActivity.dismissWaitDialog();
                }
            });
    }

    private void toast(final int strId) {
        if (mActivity != null) {
            toast(mActivity.getResources().getString(strId));
        }
    }

    private void toast(final String str) {
        if (!showToast) {
            return;
        }
        mActivity.mHandler.post(new Runnable() {
            @Override
            public void run() {
//                if (mActivity != null)
//                    ToastUtil.showLong(str);
            }
        });
    }
}
