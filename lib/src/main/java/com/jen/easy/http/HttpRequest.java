package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.invalid.EasyInvalid;

import java.util.LinkedHashMap;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http请求参数
 */
@EasyInvalid
public abstract class HttpRequest {

//    /**
//     * 请求标识
//     */
//    public int flagCode;
//
//    /**
//     * 请求标识
//     */
//    public String flagStr;

    /**
     * url前端部分
     */
    public String urlBase;

    /**
     * url后端部分，可以动态设置
     */
    public String urlAppend;

    /**
     * 请求超时
     */
    public int timeout = 30000;

    /**
     * 读取超时
     */
    public int readTimeout = 30000;

    /**
     * 默认编码(默认ut-8)
     */
    public String charset = Unicode.DEFAULT;

    /**
     * 是否使用caches(默认false)
     */
    public boolean useCaches = false;

    /**
     * 返回对象
     */
    public Class response;

    /**
     * 网络请求运行状态
     */
    RequestStatus requestStatus = RequestStatus.ready;

    /**
     * 替换请求结果特殊符号(解析返回数据前替换特殊字符如：斜杠/)
     */
    LinkedHashMap<String, String> replaceHttpResultMap;

    /**
     * 替换请求结果特殊符号,如：\\\"code\\\": \\\"HKD\\\"
     *
     * @param oldChar 替换前
     * @param newChar 替换后
     */
    public void addResponseReplaceString(String oldChar, String newChar) {
        if (oldChar == null || newChar == null) {
            HttpLog.exception(ExceptionType.NullPointerException, "格式化前后字符串不能为空");
            return;
        }
        if (replaceHttpResultMap == null) {
            replaceHttpResultMap = new LinkedHashMap<>();
        }
        replaceHttpResultMap.put(oldChar, newChar);
    }

    /**
     * 清空返回替换
     */
    public void clearResponseReplaceString() {
        if (replaceHttpResultMap != null) {
            replaceHttpResultMap.clear();
            replaceHttpResultMap = null;
        }
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void stop() {
        this.requestStatus = RequestStatus.interrupt;
    }
}
