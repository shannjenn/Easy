package com.jen.easytest.http;

import com.jen.easy.EasyResponse;

import java.io.Serializable;

public class TaskUserInfo implements Serializable {
    /**
     * 姓名+工号
     */
    private String name = "2313131";
    /**
     * 姓名+工号
     */
    @EasyResponse("name_en")
    private String nameEn = "44444444";
    /**
     * 工号
     */
    private String no = "55555555";
}
