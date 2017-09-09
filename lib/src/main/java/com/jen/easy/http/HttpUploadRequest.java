package com.jen.easy.http;

import com.jen.easy.http.imp.HttpUploadListener;

/**
 * 上传文件请求参数
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public abstract class HttpUploadRequest extends HttpRequest {
    private HttpUploadListener uploadListener;
    public Request request = new Request();

    public final class Request {
        /**
         * 请求标识
         */
        public int flagCode;

        /**
         * 请求标识
         */
        public String flag;

        /**
         * 用户停止
         */
        public boolean userCancel;

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

    public HttpUploadListener getUploadListener() {
        return uploadListener;
    }

    public void setEasyHttpUploadFileListener(HttpUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }
}
