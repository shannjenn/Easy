package com.jen.easy.http.param;

import com.jen.easy.http.listener.EasyHttpDownloadListener;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class EasyHttpDownloadParam extends EasyHttpParamFather {

    private EasyHttpDownloadListener easyHttpDownloadFileListener;
    public FinalDownloadParam fileParam;

    public EasyHttpDownloadParam() {
        fileParam = new FinalDownloadParam();
    }

    public EasyHttpDownloadListener getEasyHttpDownloadFileListener() {
        return easyHttpDownloadFileListener;
    }

    public void setEasyHttpDownloadFileListener(EasyHttpDownloadListener easyHttpDownloadFileListener) {
        this.easyHttpDownloadFileListener = easyHttpDownloadFileListener;
    }
}
