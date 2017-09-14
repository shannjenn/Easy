package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public abstract class HttpBaseRequest<T extends HttpResponse> extends HttpRequest {
    private HttpBaseListener<T> bseListener;
    public Request request = new Request();

    /**
     * 通用数据返回
     * 设置返回Object变量实体：List集合实体、单实体
     * 如：
     * （@EasyMouse.HTTP.ResponseParam("data") 注释返回参数）
     * （@private Object data; 实体变量）
     */
    public Class ResponseObjClass;


    public final class Request {
        /**
         * 请求标识
         */
        public int flagCode;

        /**
         * 请求标识
         */
        public String flag;

    }

    public HttpBaseListener<T> getBseListener() {
        return bseListener;
    }

    public void setBseListener(HttpBaseListener<T> bseListener) {
        this.bseListener = bseListener;
    }
}
