package com.jen.easy.http.param.factory;

/**
 * Created by Jen on 2017/7/26.
 */

public final class FinalUploadParam {
    public FinalUploadParam() {
    }

    /**
     * 上传/下载文件位置
     */
    public String filePath;
    /**
     * 开始位置
     */
    public long startPoit;
    /**
     * 结束位置
     */
    public long endPoit;

    /**
     * 是否断点下载
     */
    public boolean isBreak;
}
