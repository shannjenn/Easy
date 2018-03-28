package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easy.http.HttpBaseRequest;
import com.jen.easytest.http.response.ImageLoaderResponse;

/*
{{"00","359","35","230","116",
        "118","231","346","117",
        "243","240","343","321",
        "44","42","43","224",
        "268","269","271","270"},
        {"精选","财经原创","外汇视频","大宗商品","股灵经怪",
        "金融学院","慧眼识陷阱","理财叨叨叨","金融小百科",
        "财经百味","热点狙击","企业宣传","个人宣传",
        "汇誉团队","精英专访","机构风采","汇誉大电影",
        "港股指数直击","牛股火线快评","港股午盘直击","港股早盘直击"}};*/
@Easy.HTTP.GET(URL = "http://47.92.134.67:92/portal/articlecategorys/116", Response = ImageLoaderResponse.class)
public class ImageLoaderRequest extends HttpBaseRequest {

    private int page;
    private int pageSize = 1000;//默认查全部

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
