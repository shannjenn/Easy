package com.jen.easytest.demo;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/9.
 */

import com.jen.easy.EasyA;
import com.jen.easy.EasyP;

import java.util.Date;

/**
 * Created by Jen on 2017/7/19.
 */

@EasyA.DB.Table(tableName = "student")
public class Student extends EasyP.HTTP.BaseParam{

    @EasyA.HTTP.Param(paramName = "id")
    @EasyA.DB.Column(columnName = "id", primaryKey = true)
    private String id;

    @EasyA.HTTP.Param(paramName = "name")
    @EasyA.DB.Column(columnName = "name")
    private String name;

    @EasyA.HTTP.Param(paramName = "name")
    @EasyA.DB.Column(columnName = "age")
    private int age;

    @EasyA.DB.Column(columnName = "yes")
    private boolean yes;

    @EasyA.DB.Column(columnName = "date")
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
