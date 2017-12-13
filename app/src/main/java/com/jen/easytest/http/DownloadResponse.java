package com.jen.easytest.http;


import com.jen.easy.EasyMouse;

public class DownloadResponse {

    @EasyMouse.HTTP.ResponseParam("message")
    private String message;

    @EasyMouse.HTTP.ResponseParam("token")
    private String token;

    @EasyMouse.HTTP.ResponseParam("code")
    private String code;

}
