package com.jen.easytest.sqlite;


import com.jen.easy.EasyColumn;
import com.jen.easy.EasyTable;

import java.util.List;

@EasyTable("student")
public class Student {
    @EasyColumn(value = "_id",primaryKey = true)
    private int id;

    private String name;

    private String name2;

    private int age = -1;

    @EasyColumn("age2")
    private Integer age2;


    List<String> list;

    List<Student> students;

    List<Techer> techers;


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

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Techer> getTechers() {
        return techers;
    }

    public void setTechers(List<Techer> techers) {
        this.techers = techers;
    }
}
