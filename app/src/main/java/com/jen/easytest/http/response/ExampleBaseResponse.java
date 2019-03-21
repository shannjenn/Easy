package com.jen.easytest.http.response;

import com.jen.easy.EasyResponse;

/**
 * 作者：ShannJenn
 * 时间：2018/1/17:19:39
 * 说明：
 */

public class ExampleBaseResponse   {

    @EasyResponse("code")
    protected String code;

    @EasyResponse("message")
    protected String message;

    private String head;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
