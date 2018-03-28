package com.jen.easy.http.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：下载监听
 */
public interface HttpDownloadListener {

    /**
     * 成功
     *
     * @param flagCode flagCode标记
     * @param flag     flag标记
     * @param filePath 文件保存的地址
     */
    void success(int flagCode, String flag, String filePath);

    /**
     * 失败
     *
     * @param flagCode flagCode标记
     * @param flag     flag标记
     * @param msg      错误码
     */
    void fail(int flagCode, String flag, String msg);

    /**
     * 进度
     *
     * @param flagCode     flagCode标记
     * @param flag         flag标记
     * @param currentPoint 当前下载大小
     * @param endPoint     最大大小
     */
    void progress(int flagCode, String flag, long currentPoint, long endPoint);
}
