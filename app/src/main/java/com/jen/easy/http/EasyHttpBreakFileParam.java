package com.jen.easy.http;

/**
 * Created by Jen on 2017/7/21.
 */

public abstract class EasyHttpBreakFileParam extends EasyHttpParam {
    /**
     * 断点开始位置
     */
    long startPoit;
    /**
     * 断点结束位置
     */
    long endPoit;

    public long getStartPoit() {
        return startPoit;
    }

    public void setStartPoit(long startPoit) {
        this.startPoit = startPoit;
    }

    public long getEndPoit() {
        return endPoit;
    }

    public void setEndPoit(long endPoit) {
        this.endPoit = endPoit;
    }
}
