package com.jen.easy.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
@IntDef({RequestStatus.running, RequestStatus.stop, RequestStatus.finish})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestStatus {
    int running = 0;//正在运行
    int stop = 1;//被停止
    int finish = 2;//完成
}
