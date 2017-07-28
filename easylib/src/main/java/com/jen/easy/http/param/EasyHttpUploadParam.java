package com.jen.easy.http.param;

import com.jen.easy.http.listener.EasyHttpUploadListener;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class EasyHttpUploadParam extends EasyHttpParamFather {

    private EasyHttpUploadListener easyHttpUploadFileListener;
    public FinalUploadParam fileParam;

    public EasyHttpUploadParam() {
        fileParam = new FinalUploadParam();
    }

    public EasyHttpUploadListener getEasyHttpUploadFileListener() {
        return easyHttpUploadFileListener;
    }

    public void setEasyHttpUploadFileListener(EasyHttpUploadListener easyHttpUploadFileListener) {
        this.easyHttpUploadFileListener = easyHttpUploadFileListener;
    }
}
