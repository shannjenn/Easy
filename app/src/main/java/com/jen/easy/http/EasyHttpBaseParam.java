package com.jen.easy.http;

import com.jen.easy.http.listener.EasyHttpListener;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class EasyHttpBaseParam extends EasyHttpParamFather {

    private EasyHttpListener easyHttpListener;

    public EasyHttpListener getEasyHttpListener() {
        return easyHttpListener;
    }

    public void setEasyHttpListener(EasyHttpListener easyHttpListener) {
        this.easyHttpListener = easyHttpListener;
    }
}
