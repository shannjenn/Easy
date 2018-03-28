package com.jen.easy.http.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：上传监听
 */
public interface HttpUploadListener<T> {

    /**
     * 成功
     *
     * @param flagCode flagCode标记
     * @param flag     flag标记
     * @param response 返回对象
     */
    void success(int flagCode, String flag, T response);

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