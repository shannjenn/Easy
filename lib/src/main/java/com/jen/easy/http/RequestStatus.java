package com.jen.easy.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
public enum RequestStatus {
    ready,//准备状态,未执行
    running,//正在运行
    finish,//完成
    interrupt//被中断/停止
}
