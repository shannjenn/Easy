package com.jen.easytest.base;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public interface HttpListenerImp<T> {

    void httpSuccess(int flagCode, String flag, T response);

    void httpFail(int flagCode, String flag, String msg);
}
