package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easy.http.HttpBaseRequest;
import com.jen.easytest.http.response.DownloadResponse;

@Easy.HTTP.GET(URL = "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg", Response = DownloadResponse.class)
public class DownloadRequest extends HttpBaseRequest {


}
