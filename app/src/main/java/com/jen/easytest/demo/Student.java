package com.jen.easytest.demo;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.BaseParamRequest;

import java.util.List;
import java.util.Map;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/20.
 */

public class Student extends BaseParamRequest {


    @EasyMouse.HTTP.ResponseParam("name")
    private String id;

    School school;

    List<School> list;

    School[] schools;

    Map<String, School> map;


    public Object obj;
}
