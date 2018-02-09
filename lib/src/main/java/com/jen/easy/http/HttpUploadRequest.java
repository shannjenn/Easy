package com.jen.easy.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.imp.HttpUploadListener;

/**
 * 上传文件请求参数
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class HttpUploadRequest extends HttpRequest {

    @EasyMouse.HTTP.RequestParam(noReq = true)
    private HttpUploadListener uploadListener;

    @EasyMouse.HTTP.RequestParam(noReq = true)
    public Flag flag = new Flag();

    public final class Flag {
        /**
         * 请求标识
         */
        public int code;

        /**
         * 请求标识
         */
        public String str;

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
