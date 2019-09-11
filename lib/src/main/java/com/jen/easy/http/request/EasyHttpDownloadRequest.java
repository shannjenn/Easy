package com.jen.easy.http.request;

import com.jen.easy.invalid.EasyInvalid;

/**
 * 注意请求方式如：post/get
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：下载文件请求参数
 */
@EasyInvalid
public class EasyHttpDownloadRequest extends EasyHttpRequest {
    /**
     * 下载文件位置
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
     * 是否在下载删除旧文件，默认删除
     */
    public boolean deleteOldFile = true;

}
