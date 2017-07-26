package com.jen.easy.demo;

import com.jen.easy.http.imp.EasyHttpModelName;
import com.jen.easy.http.imp.EasyHttpParamName;
import com.jen.easy.http.EasyHttpDownloadParam;
import com.jen.easy.sqlite.imp.EasyColumn;
import com.jen.easy.sqlite.imp.EasyTable;

import java.util.Date;

/**
 * Created by Jen on 2017/7/19.
 */

@EasyHttpModelName(modelName = "student")
@EasyTable(tableName = "student")
public class Student extends EasyHttpDownloadParam {

    @EasyHttpParamName(paramName = "id")
    @EasyColumn(columnName = "id", primaryKey = true)
    private String id;

    @EasyHttpParamName(paramName = "name")
    @EasyColumn(columnName = "name")
    private String name;

    @EasyHttpParamName(paramName = "age")
    @EasyColumn(columnName = "age")
    private int age;

    @EasyHttpParamName(paramName = "yes")
    @EasyColumn(columnName = "yes")
    private boolean yes;

    @EasyHttpParamName(paramName = "date")
    @EasyColumn(columnName = "date")
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
