package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easy.http.HttpBasicRequest;

/**
 * Created by zs on 2018/4/26.
 */

public abstract class TaskHttpBaseRequest extends HttpBasicRequest {

    public TaskHttpBaseRequest() {
        empNo = "6407001308";
        token = "f7b84e310633bd1bb2c1927931c0f99e";
        contentType = "application/Json;charset=UTF-8";
    }

    @Easy.HTTP.RequestParam(value = "X-Emp-No", type = Easy.HTTP.TYPE.HEAD)
    private String empNo;
    @Easy.HTTP.RequestParam(value = "X-Auth-Value", type = Easy.HTTP.TYPE.HEAD)
    private String token;
    @Easy.HTTP.RequestParam(value = "X-Lang-Id", type = Easy.HTTP.TYPE.HEAD)
    private String language;
    @Easy.HTTP.RequestParam(value = "Content-Type", type = Easy.HTTP.TYPE.HEAD)
    private String contentType;

//    @Easy.HTTP.RequestParam(value = "X-Lang-Id", type = Easy.HTTP.TYPE.HEAD)
//    private String language;






}
