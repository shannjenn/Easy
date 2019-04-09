package com.jen.easy.http.request;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.invalid.EasyInvalid;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http请求参数
 */
@EasyInvalid
public abstract class EasyHttpRequest implements Serializable {

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
    private EasyRequestStatus requestStatus = EasyRequestStatus.ready;

    /**
     * 在解析数据前替换请求结果的特殊符号(返回数据前替换特殊字符如：斜杠/)
     */
    private LinkedHashMap<String, String> replaceResult;

    /**
     * 替换请求结果特殊符号,如：\\\"code\\\": \\\"HKD\\\"
     *
     * @param oldChar 替换前
     * @param newChar 替换后
     */
    public void addReplaceResult(String oldChar, String newChar) {
        if (oldChar == null || newChar == null) {
            HttpLog.exception(ExceptionType.NullPointerException, "格式化前后字符串不能为空");
            return;
        }
        if (replaceResult == null) {
            replaceResult = new LinkedHashMap<>();
        }
        replaceResult.put(oldChar, newChar);
    }

    /**
     * @return 获取特殊符号集合
     */
    public LinkedHashMap<String, String> getReplaceResult() {
        if (replaceResult == null) {
            return new LinkedHashMap<>();
        }
        return replaceResult;
    }

    /**
     * 清空返回替换
     */
    public void clearReplaceResult() {
        if (replaceResult != null) {
            replaceResult.clear();
            replaceResult = null;
        }
    }

    /**
     * @return 获取请求状态
     */
    public EasyRequestStatus getRequestStatus() {
        return requestStatus;
    }

    /**
     * @param requestStatus 设置请求状态
     */
    public void setRequestStatus(EasyRequestStatus requestStatus) {
        if (requestStatus == null) {//防止空指针
            return;
        }
        this.requestStatus = requestStatus;
    }

    /**
     * 中断/停止请求请求
     */
    public void interrupt() {
        this.requestStatus = EasyRequestStatus.interrupt;
    }
}
