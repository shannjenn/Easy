package com.jen.easy;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：网络请求参数 相同参数时 提交类型
 */
public enum EasyRequestCommit {
    def,//默认(已经有参时 不替换)
    single,//单个提交(必须提交 必须替换)
    multiple//多个提交(用于对象 合并)
}
