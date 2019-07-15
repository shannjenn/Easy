package com.jen.easy.http.imp;

import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：基本数据网络请求监听
 */
public interface EasyHttpListener {

    /**
     * 成功
     *
     * @param flagCode     标记
     * @param flagStr      标记
     * @param responseBody 返回实体对象
     * @param headMap      返回头部信息
     */
    void success(int flagCode, String flagStr, Object responseBody, Map<String, List<String>> headMap);

    /**
     * 失败
     *
     * @param flagCode     标记
     * @param flagStr      标记
     * @param responseBody 返回实体对象
     */
    void fail(int flagCode, String flagStr, Object responseBody);
}