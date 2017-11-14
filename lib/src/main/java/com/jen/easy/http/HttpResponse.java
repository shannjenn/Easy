package com.jen.easy.http;

/**
 * 作者：ShannJenn
 * 时间：2017/9/9.
 * 说明：网络请求返回
 */
public class HttpResponse {

    /**
     * 公共数据返回，以免创建多个HttpResponse对象
     * 继承HttpResponse后，定义Object变量，可以返回List集合实体、单实体对象
     * 如：
     * （@EasyMouse.mHttp.ResponseParam("data") 注释返回参数）
     * （@private Object data;）
     */
    public Class objClass;
}
