package com.jen.easytest.http;

import com.jen.easy.Easy;

import java.io.Serializable;

public class TaskUserInfo implements Serializable {
    /**
     * 姓名+工号
     */
    private String name;
    /**
     * 姓名+工号
     */
    @Easy.HTTP.ResponseParam("name_en")
    private String nameEn;
    /**
     * 工号
     */
    private String no;
    /**
     * 头像Url
     */
    private String portrait;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
