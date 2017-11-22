package com.jen.easy.http.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:44
 * 说明：上传监听
 */
public interface HttpUploadListener<T> {

    void success(int flagCode, String flag, T response);

    void fail(int flagCode, String flag, String msg);

    void progress(int flagCode, String flag, long currentPoint, long endPoint);
}