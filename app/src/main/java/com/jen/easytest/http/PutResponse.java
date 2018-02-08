package com.jen.easytest.http;


import com.jen.easy.http.HttpHeadResponse;

public class PutResponse extends HttpHeadResponse {

    private String time;
    private int status;
    private String msg;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
