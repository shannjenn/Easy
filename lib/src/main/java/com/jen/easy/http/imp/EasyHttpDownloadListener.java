package com.jen.easy.http.imp;

import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：下载监听
 */
public interface EasyHttpDownloadListener {

    /**
     * 成功
     *
     * @param flagCode 标记
     * @param flagStr  标记
     * @param filePath 文件保存的地址
     * @param headMap  返回头部信息
     */
    void success(int flagCode, String flagStr, String filePath, Map<String, List<String>> headMap);

    /**
     * 失败
     *
     * @param flagCode 标记
     * @param flagStr  标记
     * @param msg      错误码
     */
    void fail(int flagCode, String flagStr, String msg);

    /**
     * 进度
     *
     * @param flagCode     标记
     * @param flagStr      标记
     * @param currentPoint 当前下载大小
     * @param endPoint     最大大小
     */
    void progress(int flagCode, String flagStr, long currentPoint, long endPoint);
}
