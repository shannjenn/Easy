package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyResponse;
import com.jen.easy.EasyResponse;
import com.jen.easytest.http.TaskUserInfo;
import com.jen.easytest.http.response.AirResponse;
import com.jen.easytest.model.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EasyHttpPost(UrlBase = "http://apics.baoku.com/api/air/query", Response = AirResponse.class)
public class AirRequest2 extends ExampleBaseRequest {

    public AirRequest2() {
    }

    @EasyResponse("cid")
    private String cid;// 商户公司编号

    @EasyResponse("fromCity")
    private String fromCity;// 起飞城市代码

    @EasyResponse("book")
    JSONObject book;

    @EasyResponse("taskUserInfo")
    TaskUserInfo taskUserInfo;


    @EasyResponse("books")
    JSONArray books;

    @EasyResponse("userInfos")
    List<TaskUserInfo> userInfos;

    @EasyResponse("userInfos")
    List<JSONObject> userInfos2;
}
