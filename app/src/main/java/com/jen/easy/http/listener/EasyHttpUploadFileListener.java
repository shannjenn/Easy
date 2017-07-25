package com.jen.easy.http.listener;

/**
 * Created by Jen on 2017/7/21.
 */

public interface EasyHttpUploadFileListener {

    public void success(int flagCode, String flag, Object result);

    public void fail(int flagCode, String flag, int easyHttpCode, String msg);

    public void progress(int flagCode, String flag, long currentPoint, long endPoint);
}
