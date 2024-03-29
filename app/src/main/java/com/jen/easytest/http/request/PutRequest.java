package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpPut;
import com.jen.easy.EasyRequest;
import com.jen.easy.EasyRequestType;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easytest.http.response.PutResponse;

@EasyHttpPut(UrlBase = "http://47.92.134.67:92/portal/followExperts/{id}", Response = PutResponse.class)
public class PutRequest extends EasyHttpDataRequest {

    @EasyRequest(value = "id", type = EasyRequestType.Url)
    private String id;

    @EasyRequest(type = EasyRequestType.Head)
    private String token = "16643-bdf0737cafb1c1a32451a0d93692edba";

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
