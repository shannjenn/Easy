package com.jen.easy.imageLoader;

import com.jen.easy.EasyHttpGet;
import com.jen.easy.http.request.EasyHttpDownloadRequest;

@EasyHttpGet(Response = ImageLoaderResponse.class)
public class ImageLoaderRequest extends EasyHttpDownloadRequest {

}
