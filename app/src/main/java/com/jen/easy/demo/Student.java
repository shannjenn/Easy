package com.jen.easy.demo;

import com.jen.easy.sqlite.imp.EasyColumn;
import com.jen.easy.sqlite.imp.EasyTable;

/**
 * Created by Jen on 2017/7/19.
 */

@EasyTable(tableName = "student")
public class Student {

    @EasyColumn(columnName = "id", primaryKey = true)
    private String id;

    @EasyColumn(columnName = "name", columnType = EasyColumn.TYPE0)
    private String name;

    @EasyColumn(columnName = "age")
    private String age;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
