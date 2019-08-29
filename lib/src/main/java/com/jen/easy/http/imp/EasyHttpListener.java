package com.jen.easy.http.imp;

import com.jen.easy.http.request.EasyHttpRequest;

import java.util.List;
import java.util.Map;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：基本数据网络请求监听
 */
public abstract class EasyHttpListener {

    /**
     * 成功
     *
     * @param flagCode 标记
     * @param flagStr  标记
     * @param request  请求实体对象
     * @param response 返回实体对象
     * @param headMap  返回头部信息
     */
    public abstract void success(int flagCode, String flagStr, EasyHttpRequest request, Object response, Map<String, List<String>> headMap);

    /**
     * 失败
     *
     * @param flagCode 标记
     * @param flagStr  标记
     * @param request  请求实体对象
     * @param response 返回实体对象
     */
    public abstract void fail(int flagCode, String flagStr, EasyHttpRequest request, Object response);

    /**
     * 进度
     *
     * @param flagCode     标记
     * @param flagStr      标记
     * @param request      请求实体对象
     * @param response     返回实体对象
     * @param currentPoint 当前下载大小
     * @param endPoint     最大大小
     */
    public void progress(int flagCode, String flagStr, EasyHttpRequest request, Object response, long currentPoint, long endPoint) {

    }
}