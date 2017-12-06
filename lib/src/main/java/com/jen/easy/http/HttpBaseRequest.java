package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public class HttpBaseRequest extends HttpRequest {
    /**
     * 通用数据返回
     * 设置返回Object变量实体：List集合实体、单实体
     * 如：
     * （@EasyMouse.mHttp.ResponseParam("data") 注释返回参数）
     * （@private Object data; 实体变量，必须为Object）
     */
    public Class responseObjectType;

    private HttpBaseListener bseListener;
    public Flag flag = new Flag();

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

    public HttpBaseListener getBseListener() {
        return bseListener;
    }

    public void setBseListener(HttpBaseListener bseListener) {
        this.bseListener = bseListener;
    }
}
