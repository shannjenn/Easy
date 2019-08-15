package com.jen.easytest.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.http.request.EasyHttpUploadRequest;

/**
 * 日志上传接口
 */
@EasyHttpPost(UrlAppend = "http://localdev.intel-yun.com/cloud-platform/restful/logTable/save/v1/", Response = LogcatResponse.class)
public class LogcatRequest extends EasyHttpUploadRequest {
    private String fileName;//文件流
    private String busType = "0";//0:一体机;1:POS收银机

    public String getFileName() {
        return fileName == null ? "" : fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBusType() {
        return busType == null ? "" : busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }
}
