package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easy.http.HttpBasicRequest;
import com.jen.easytest.http.response.DownloadResponse;

@Easy.HTTP.GET(URLBASE = "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg", Response = DownloadResponse.class)
public class DownloadRequest extends HttpBasicRequest {


}
