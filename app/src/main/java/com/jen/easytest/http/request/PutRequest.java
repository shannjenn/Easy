package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easy.http.HttpBaseRequest;
import com.jen.easytest.http.response.PutResponse;

@Easy.HTTP.PUT(URL = "http://47.92.134.67:92/portal/followExperts", Response = PutResponse.class)
public class PutRequest extends HttpBaseRequest {

    @Easy.HTTP.RequestParam(isHeadReq = true)
    private String token = "16643-bdf0737cafb1c1a32451a0d93692edba";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
