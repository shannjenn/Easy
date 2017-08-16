package com.jen.easytest.demo;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/9.
 */

import com.jen.easy.EasyFactory;
import com.jen.easy.EasyMouse;

import java.util.Date;

/**
 * Created by Jen on 2017/7/19.
 */

@EasyMouse.DB.Table(tableName = "student")
public class Student extends EasyFactory.HTTP.BaseParam{

    @EasyMouse.HTTP.Param(paramName = "id")
    @EasyMouse.DB.Column(columnName = "id", primaryKey = true)
    private String id;

    @EasyMouse.HTTP.Param(paramName = "name")
    @EasyMouse.DB.Column(columnName = "name")
    private String name;

    @EasyMouse.HTTP.Param(paramName = "name")
    @EasyMouse.DB.Column(columnName = "age")
    private int age;

    @EasyMouse.DB.Column(columnName = "yes")
    private boolean yes;

    @EasyMouse.DB.Column(columnName = "date")
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
