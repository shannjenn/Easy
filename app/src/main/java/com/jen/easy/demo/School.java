package com.jen.easy.demo;

import com.jen.easy.http.imp.EasyHttpModelName;
import com.jen.easy.http.imp.EasyHttpParamName;
import com.jen.easy.sqlite.imp.EasyColumn;
import com.jen.easy.sqlite.imp.EasyTable;

import java.util.List;

/**
 * Created by Jen on 2017/7/24.
 */

@EasyHttpModelName(modelName = "school")
@EasyTable(tableName = "school")
public class School {

    @EasyHttpParamName(paramName = "id")
    @EasyColumn(columnName = "id", primaryKey = true)
    private String id;

    @EasyHttpParamName(paramName = "name")
    @EasyColumn(columnName = "name")
    private String name;

    @EasyHttpParamName(paramName = "student")
    @EasyColumn(columnName = "student")
    private Student student;

    @EasyHttpParamName(paramName = "students")
    @EasyColumn(columnName = "students")
    private List<Student> students;


    @EasyHttpParamName(paramName = "char")
    private char aChar;
    @EasyHttpParamName(paramName = "byte")
    private Byte aByte;
    @EasyHttpParamName(paramName = "Short")
    private Short aShort;
    @EasyHttpParamName(paramName = "aLong")
    private Long aLong;
    @EasyHttpParamName(paramName = "aFloat")
    private float aFloat;
    @EasyHttpParamName(paramName = "aDouble")
    private double aDouble;


    @EasyHttpModelName(modelName = "teacher")
    @EasyTable(tableName = "teacher")
    public class Teacher {
        @EasyHttpParamName(paramName = "id")
        @EasyColumn(columnName = "id", primaryKey = true)
        private String id;

        @EasyHttpParamName(paramName = "name")
        @EasyColumn(columnName = "name")
        private String name;
    }


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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
