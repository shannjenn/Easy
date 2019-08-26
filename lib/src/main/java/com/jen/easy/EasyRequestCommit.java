package com.jen.easy;

/**
 * 作者：ShannJenn
 * 时间：2018/10/26.
 * 说明：网络请求参数 相同参数时 提交类型
 */
public enum EasyRequestCommit {
    def,//默认(已经有参时 不替换)
    single,//单个提交(必须提交 必须替换)
    multiple,//多个提交(用于对象 合并)
    /*
     * 只有变量作请求参，实体class不做实体参请求(注意：只有实体才合适)
     * 如：
     *
     * @EasyHttpPost(UrlAppend = HttpConstant.Method.DUTY_WORK, Response = ShiftWorkResponse.class)
     * public class ShiftWorkRequest extends BaseRequest {
     *
     * @EasyRequest(commit = EasyRequestCommit.onlyField)
     * private Data data
     *
     * public static class Data{
     * private String id = "001";
     * private String name = "张三";
     * }
     * }
     *
     * 请求结果为：
     *
     * {”id“:"001","name":"张三"}(忽略data参)
     *
     *
     * */
    onlyField
}
