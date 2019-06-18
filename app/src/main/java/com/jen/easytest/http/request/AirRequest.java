package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.EasyRequest;
import com.jen.easytest.http.TaskUserInfo;
import com.jen.easytest.http.response.AirResponse;
import com.jen.easytest.model.Book;

import java.util.ArrayList;
import java.util.List;

@EasyHttpPost(UrlBase = "http://apics.baoku.com/api/air/query", Response = AirResponse.class)
public class AirRequest extends BaseRequest {

      }
