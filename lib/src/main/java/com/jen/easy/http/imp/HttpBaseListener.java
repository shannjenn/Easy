package com.jen.easy.http.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：基本数据网络请求监听
 */
public interface HttpBaseListener {

    void success(int flagCode, String flag, Object msg);

    void fail(int flagCode, String flag, String msg);
}