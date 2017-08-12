package com.jen.easy.http.param.factory;

/**
 * Created by Jen on 2017/7/26.
 */

public abstract class ParamFather {
    public FinalBaseParam httpBase;

    public ParamFather() {
        httpBase = new FinalBaseParam();
    }
}
