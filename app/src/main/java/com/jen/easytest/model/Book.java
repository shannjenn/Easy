package com.jen.easytest.model;

import com.jen.easy.EasyResponse;

/**
 * Created by Administrator on 2018/3/28.
 */

public class Book {

    @EasyResponse(value = "_id")//返回参数名为_id
    private int id;

    private String name;//不注释默认作为参数返回,参数名与变量名一致，name

    private long date;

    private String des;

//    @EasyResponse(invalid = true)//noResp = true，则不作为返回参数
    private boolean isCheck;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
