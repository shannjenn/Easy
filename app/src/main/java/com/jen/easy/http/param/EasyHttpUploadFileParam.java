package com.jen.easy.http.param;

import com.jen.easy.http.listener.EasyHttpUploadFileListener;

/**
 * Created by Jen on 2017/7/21.
 */

public abstract class EasyHttpUploadFileParam extends EasyHttpParam {
    private EasyHttpUploadFileListener easyHttpUploadFileListener;

    private String fileName;
    /**
     * 下载文件位置
     */
    private String filePath;
    /**
     * 开始位置
     */
    private long startPoit;
    /**
     * 结束位置
     */
    private long endPoit;

    /**
     * 是否断点下载
     */
    private boolean isBreak;

    /**
     * 用户停止
     */
    private boolean userCancel;

    public EasyHttpUploadFileParam() {
        doOutput = true;
        doInput = true;
        useCaches = false;
    }

    public EasyHttpUploadFileListener getEasyHttpUploadFileListener() {
        return easyHttpUploadFileListener;
    }

    public void setEasyHttpUploadFileListener(EasyHttpUploadFileListener easyHttpUploadFileListener) {
        this.easyHttpUploadFileListener = easyHttpUploadFileListener;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getStartPoit() {
        return startPoit;
    }

    public void setStartPoit(long startPoit) {
        this.startPoit = startPoit;
    }

    public long getEndPoit() {
        return endPoit;
    }

    public void setEndPoit(long endPoit) {
        this.endPoit = endPoit;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak(boolean aBreak) {
        isBreak = aBreak;
    }

    public boolean isUserCancel() {
        return userCancel;
    }

    public void setUserCancel(boolean userCancel) {
        this.userCancel = userCancel;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}