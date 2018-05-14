package com.jen.easy.http;

import com.jen.easy.Easy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：下载文件请求参数
 */
@Easy.HTTP.NoRequestParam
public class HttpDownloadRequest extends HttpRequest {
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

    /**
     * 是否在下载删除旧文件，默认删除
     */
    public boolean deleteOldFile = true;

}
