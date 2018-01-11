package com.jen.easy.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.imp.HttpBaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：基本数据请求参数
 */
public abstract class HttpBaseRequest extends HttpRequest {
    /**
     * 通用数据返回
     * 设置Response类中Object变量的值：List集合实体、单实体
     * 如：
     * （@EasyMouse.mHttp.ResponseParam("data") 注释返回参数）
     * （@private Object data; 实体变量，必须为Object）
     */
    @EasyMouse.HTTP.RequestParam(noReq = true)
    public Class responseObjectType;

    @EasyMouse.HTTP.RequestParam(noReq = true)
    private HttpBaseListener bseListener;

    @EasyMouse.HTTP.RequestParam(noReq = true)
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
