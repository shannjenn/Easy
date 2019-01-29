package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpGet;
import com.jen.easy.http.HttpBaseRequest;
import com.jen.easytest.http.response.DownloadResponse;

@EasyHttpGet(UrlBase = "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg", Response = DownloadResponse.class)
public class DownloadRequest extends HttpBaseRequest {


}
