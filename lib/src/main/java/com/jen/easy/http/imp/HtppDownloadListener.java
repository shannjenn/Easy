package com.jen.easy.http.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：下载监听
 */
public interface HtppDownloadListener {

    void success(int flagCode, String flag);

    void fail(int flagCode, String flag, String msg);

    void progress(int flagCode, String flag, long currentPoint, long endPoint);
}