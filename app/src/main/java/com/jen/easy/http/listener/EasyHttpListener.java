package com.jen.easy.http.listener;

/**
 * Created by Jen on 2017/7/21.
 */

public interface EasyHttpListener {

    public void success(int flagCode, String flag, Object msg);

    public void fail(int flagCode, String flag, int easyHttpCode, String msg);
}
