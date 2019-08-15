package com.jen.easy.http.request;

import com.jen.easy.EasyUploadType;
import com.jen.easy.invalid.EasyInvalid;

/**
 * 上传文件请求参数
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
@EasyInvalid
public class EasyHttpUploadRequest extends EasyHttpRequest {
    /**
     * 上传/下载文件位置
     */
    public String filePath;
    /**
     * 上传文件名称key值
     */
    public String fileNameKey;
    /**
     * 上传文件名称value值
     */
    public String fileNameValue;
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
     * 上传模式
     */
    public EasyUploadType uploadType = EasyUploadType.ParamFile;
}
