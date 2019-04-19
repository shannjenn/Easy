package com.jen.easy.http.request;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 */
public enum EasyRequestState {
    ready,//准备状态,未执行
    running,//正在运行
    finish,//完成
    interrupt//被中断/停止
}
