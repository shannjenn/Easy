package com.jen.easy.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
@IntDef({HttpState.RUN, HttpState.STOP})
@Retention(RetentionPolicy.SOURCE)
@interface HttpState {
    int RUN = 0;
    int STOP = 1;
}
