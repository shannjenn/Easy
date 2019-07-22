package com.jen.easy.imageLoader;

import com.jen.easy.EasyHttpGet;
import com.jen.easy.http.request.EasyHttpDownloadRequest;

@EasyHttpGet(Response = ImagLoaderResponse.class)
public class ImagLoaderRequest extends EasyHttpDownloadRequest {

}
