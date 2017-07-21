package com.jen.easy.http.listener;

/**
 * Created by Jen on 2017/7/21.
 */

public interface EasyHttpDownloadFileListener {

    public void success(int flagCode, String flag);

    public void fail(int flagCode, String flag, int easyHttpCode, String tag);

    public void progress(int flagCode, String flag, long currentPoint, long endPoint);
}
