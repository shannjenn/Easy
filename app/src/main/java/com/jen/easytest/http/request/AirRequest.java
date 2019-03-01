package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyRequest;
import com.jen.easytest.http.TaskUserInfo;
import com.jen.easytest.http.response.AirResponse;
import com.jen.easytest.model.Book;

import java.util.ArrayList;
import java.util.List;

@EasyHttpPost(UrlBase = "http://apics.baoku.com/api/air/query", Response = AirResponse.class)
public class AirRequest extends ExampleBaseRequest {

    public AirRequest() {
        Book book = new Book();
        books.add(book);

        TaskUserInfo userInfo = new TaskUserInfo();
        userInfos.add(userInfo);
    }

    @EasyRequest("cid")
    private String cid = "cid";// 商户公司编号

    @EasyRequest("fromCity")
    private String fromCity = "ccccc";// 起飞城市代码

    @EasyRequest("book")
    Book book = new Book();

    @EasyRequest("book")
    TaskUserInfo taskUserInfo = new TaskUserInfo();


    @EasyRequest("list")
    List<Book> books = new ArrayList<>();

    @EasyRequest("list")
    List<TaskUserInfo> userInfos = new ArrayList<>();
}
