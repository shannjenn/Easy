package com.jen.easy.http;

import com.jen.easy.EasyRequestInvalid;
import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.Throw;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：http请求参数
 */
@EasyRequestInvalid
public abstract class HttpRequest {

    /**
     * 请求标识
     */
    public int flagCode;

    /**
     * 请求标识
     */
    public String flagStr;

    /**
     * 如果url没设置值则url = urlBase + urlAppend
     */
    public String url;

    /**
     * 可以动态设置
     */
    public String urlBase;

    /**
     * 可以动态设置
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
     * 网络请求运行状态
     */
    HttpState state = HttpState.RUN;

    /**
     * 替换请求结果特殊符号
     */
    Map<String, String> responseFormatMap;

    /**
     * 替换请求结果特殊符号,如：\\\"code\\\": \\\"HKD\\\"
     *
     * @param regex       替换前
     * @param replacement 替换后
     */
    public void addResponseFormat(String regex, String replacement) {
        if (regex == null || replacement == null) {
            Throw.exception(ExceptionType.NullPointerException, "格式化前后字符串不能为空");
            return;
        }
        if (responseFormatMap == null) {
            responseFormatMap = new HashMap<>();
        }
        responseFormatMap.put(regex, replacement);
    }

    /**
     * 转Json
     *
     * @param obj 对象数据
     */
    public JSONObject toJson(Object obj) {
        return HttpReflectManager.requestToJson(obj);
    }
}
