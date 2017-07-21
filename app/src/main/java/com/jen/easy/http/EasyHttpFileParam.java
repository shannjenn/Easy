package com.jen.easy.http;

/**
 * Created by Administrator on 2017/7/21.
 */

public abstract class EasyHttpFileParam extends EasyHttpParam{
    /**下载文件*/
    boolean isDownLoad = false;
    /**下载文件位置*/
    String downLoadFile;
    /**上传文件*/
    boolean isUpLoad = false;
    /**下载文件位置*/
    String upLoadFile;

    public boolean isDownLoad() {
        return isDownLoad;
    }

    public void setDownLoad(boolean downLoad) {
        isDownLoad = downLoad;
    }

    public String getDownLoadFile() {
        return downLoadFile;
    }

    public void setDownLoadFile(String downLoadFile) {
        this.downLoadFile = downLoadFile;
    }

    public boolean isUpLoad() {
        return isUpLoad;
    }

    public void setUpLoad(boolean upLoad) {
        isUpLoad = upLoad;
    }

    public String getUpLoadFile() {
        return upLoadFile;
    }

    public void setUpLoadFile(String upLoadFile) {
        this.upLoadFile = upLoadFile;
    }
}
