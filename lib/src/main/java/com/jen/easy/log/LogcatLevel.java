package com.jen.easy.log;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2019/01/08.
 * 说明：日志级别
 */
@IntDef({LogcatLevel.E, LogcatLevel.W, LogcatLevel.I, LogcatLevel.D})
@Retention(RetentionPolicy.SOURCE)
public @interface LogcatLevel {
    int E = 0;//错误级别
    int W = 1;//告警级别
    int I = 2;//
    int D = 3;//
}
