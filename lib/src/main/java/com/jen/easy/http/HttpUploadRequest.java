package com.jen.easy.http;

import com.jen.easy.http.imp.HttpUploadListener;

/**
 * 上传文件请求参数
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public abstract class HttpUploadRequest<T extends HttpResponse> extends HttpRequest {
    private HttpUploadListener<T> uploadListener;
    public Request request = new Request();

    /**
     * 设置返回Object变量实体：List集合实体、单实体
     * 如：
     * （@EasyMouse.HTTP.ResponseParam("data") 注释返回参数）
     * （@private Object data; 实体变量）
     */
    public Class ResponseObjClass;

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

    public HttpUploadListener<T> getUploadListener() {
        return uploadListener;
    }

    public void setEasyHttpUploadFileListener(HttpUploadListener<T> uploadListener) {
        this.uploadListener = uploadListener;
    }
}
