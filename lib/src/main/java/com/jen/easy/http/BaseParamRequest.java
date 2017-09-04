package com.jen.easy.http;

import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public class BaseParamRequest extends HttpParam {
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

        /**
         * 返回实体
         */
        public Class resopseBaseClass;

        /**
         * 返回实体中resopseBaseClass的对象
         */
        public Class resopseBaseClassObject;

        /**
         * 用户停止
         */
        public boolean userCancel;

    }

    public HttpBaseListener getBseListener() {
        return bseListener;
    }

    public void setBseListener(HttpBaseListener bseListener) {
        this.bseListener = bseListener;
    }
}
