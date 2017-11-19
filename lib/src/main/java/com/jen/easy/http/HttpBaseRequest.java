package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public class HttpBaseRequest<T extends HttpResponse/*返回的参数*/> extends HttpRequest {
    private HttpBaseListener<T> bseListener;
    public Flag flag = new Flag();

    /**
     * 通用数据返回
     * 设置返回Object变量实体：List集合实体、单实体
     * 如：
     * （@EasyMouse.mHttp.ResponseParam("data") 注释返回参数）
     * （@private Object data; 实体变量）
     */
    public Class ResponseObjClass;


    public final class Flag {
        /**
         * 请求标识
         */
        public int code;

        /**
         * 请求标识
         */
        public String str;

    }

    public HttpBaseListener<T> getBseListener() {
        return bseListener;
    }

    public void setBseListener(HttpBaseListener<T> bseListener) {
        this.bseListener = bseListener;
    }
}
