package com.jen.easy.http.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：基本数据网络请求监听
 */
public interface HttpBasicListener {

    /**
     * 成功
     *
     * @param flagCode flagCode标记
     * @param flag     flag标记
     * @param response 返回对象
     */
    void success(int flagCode, String flag, Object response);

    /**
     * 失败
     *
     * @param flagCode flagCode标记
     * @param flag     flag标记
     * @param msg      错误码
     */
    void fail(int flagCode, String flag, String msg);
}