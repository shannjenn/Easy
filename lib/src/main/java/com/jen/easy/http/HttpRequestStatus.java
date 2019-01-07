package com.jen.easy.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
@IntDef({HttpRequestStatus.RUN, HttpRequestStatus.STOP, HttpRequestStatus.FINISH})
@Retention(RetentionPolicy.SOURCE)
public @interface HttpRequestStatus {
    int RUN = 0;//正在运行
    int STOP = 1;//被停止
    int FINISH = 2;//完成
}
