package com.jen.easy.http;

import com.jen.easy.http.imp.HtppDownloadListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：下载文件请求参数
 */
public class HttpDownloadPRequest extends HttpRequest {
    private HtppDownloadListener downloadListener;
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

        /**
         * 是否在下载删除旧文件
         */
        public boolean deleteOldFile;
    }

    public HtppDownloadListener getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(HtppDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }
}
