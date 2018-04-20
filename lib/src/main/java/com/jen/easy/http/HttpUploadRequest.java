package com.jen.easy.http;

import com.jen.easy.Easy;
import com.jen.easy.http.imp.HttpUploadListener;

/**
 * 上传文件请求参数
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
@Easy.HTTP.NoRequestParam
public class HttpUploadRequest extends HttpRequest {
    private HttpUploadListener uploadListener;

    /**
     * 上传/下载文件位置
     */
    public String filePath;
    /**
     * 开始位置
     */
    public long startPoint;
    /**
     * 结束位置
     */
    public long endPoint;
    /**
     * 是否断点下载
     */
    public boolean isBreak;
    /**
     * 用户停止
     */
    public boolean userCancel;

    public HttpUploadListener getUploadListener() {
        return uploadListener;
    }

    public void setEasyHttpUploadFileListener(HttpUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }
}
