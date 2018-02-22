package com.jen.easytest.sqlite;


import com.jen.easy.EasyMouse;

import java.util.List;

@EasyMouse.DB.Table("Student")
public class Student {
    @EasyMouse.DB.Column(primaryKey = true)
    private int id;

    private String name;

    @EasyMouse.DB.Column("name2")
    private String name2;

    private int age;

    @EasyMouse.DB.Column("age2")
    private int age2;


    @EasyMouse.DB.Column(noColumn = true)
    List<String> list;

    @EasyMouse.DB.Column(noColumn = true)
    List<Student> students;

    @EasyMouse.DB.Column(noColumn = true)
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
