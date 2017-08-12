package com.jen.easy.http.param.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jen on 2017/7/26.
 */

public final class FinalBaseParam {
    FinalBaseParam() {
    }

    /**
     * 请求标识
     */
    public int flagCode;

    /**
     * 请求标识
     */
    public String flag;

    /**
     * 是否要解析返回结果(Json格式)
     */
    public boolean parseJson;

    /**
     * 用户停止
     */
    public boolean userCancel;

    /**
     * 请求参数集合
     */
    public final Map<String, String> requestParam = new HashMap<>();

    public String url;
    public String method;
    public String charset;
    public String contentType;
    public String connection;

    public int timeout = -1;
    public int readTimeout = -1;

    public boolean doOutput = false;
    public boolean doInput = true;
    public boolean useCaches = false;
}
