package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public abstract class HttpBaseRequest extends HttpRequest {
    private HttpBaseListener bseListener;
    public Request request = new Request();

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

    public HttpBaseListener getBseListener() {
        return bseListener;
    }

    public void setBseListener(HttpBaseListener bseListener) {
        this.bseListener = bseListener;
    }
}
