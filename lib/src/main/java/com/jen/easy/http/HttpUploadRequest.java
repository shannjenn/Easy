package com.jen.easy.http;

import com.jen.easy.http.imp.HttpUploadListener;

/**
 * 上传文件请求参数
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class HttpUploadRequest<T extends HttpResponse> extends HttpRequest {
    private HttpUploadListener<T> uploadListener;
    public Flag flag = new Flag();

    /**
     * 公共数据返回，以免创建多个HttpResponse对象
     * 与HttpResponse中objClass对应，设置返回的实体对象的class类
     * 如：ResponseObjClass = Student.class,则在HttpResponse返回Student或List<Student>（取决于服务器数据）
     */
    public Class ResponseObjClass;

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

    public HttpUploadListener<T> getUploadListener() {
        return uploadListener;
    }

    public void setEasyHttpUploadFileListener(HttpUploadListener<T> uploadListener) {
        this.uploadListener = uploadListener;
    }
}
