package com.jen.easy.http.param.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jen on 2017/7/26.
 */

public final class FinalBasePram {
    FinalBasePram() {
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
     * 是否要解析返回结果(默认解析)
     */
    public boolean parse = true;

    /**
     * 用户停止
     */
    public boolean userCancel;

    /**
     * 请求参数集合
     */
    public final Map<String, String> requestParam = new HashMap<>();

}
