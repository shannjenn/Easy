package com.jen.easy.log;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2019/01/08.
 * 说明：日志级别
 */
public enum LogcatLevel {
    E,//错误级别
    W,//告警级别
    I,//信息级别
    D//调试级别
}
