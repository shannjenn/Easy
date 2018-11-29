package com.jen.easy.invalid;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2018/10/27.
 * 说明：失效
 */
@IntDef({InvalidType.Request, InvalidType.Response, InvalidType.Column})
@Retention(RetentionPolicy.SOURCE)
public @interface InvalidType {
    int Request = 0;//请求参数失效
    int Response = 1;//返回参数失效
    int Column = 2;//数据库列失效
}
