package com.jen.easytest.http.response;


import com.jen.easy.Easy;

public class DownloadResponse {

    @Easy.HTTP.ResponseParam("message")
    private String message;

    @Easy.HTTP.ResponseParam("token")
    private String token;

    @Easy.HTTP.ResponseParam("code")
    private String code;

}
