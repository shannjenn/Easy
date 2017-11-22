package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public class HttpBaseRequest extends HttpRequest {
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
