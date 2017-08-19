package com.jen.easy.http.param.factory;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class ParamFather {
    public FinalHttpPram httpParam;
    public FinalBasePram baseParam;

    public ParamFather() {
        httpParam = new FinalHttpPram();
        baseParam = new FinalBasePram();
    }
}
