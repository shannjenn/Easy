package com.jen.easytest.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.HttpBaseRequest;

@EasyMouse.HTTP.GET(URL = "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg", Response = DownloadResponse.class)
public class DownloadRequest extends HttpBaseRequest {


}
