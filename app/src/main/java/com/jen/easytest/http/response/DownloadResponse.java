package com.jen.easytest.http.response;


import com.jen.easy.EasyResponse;

public class DownloadResponse {

    @EasyResponse("message")
    private String message;

    @EasyResponse("token")
    private String token;

    @EasyResponse("code")
    private String code;

}
