package com.jen.easy.demo;

import com.jen.easy.http.EasyHttpBaseParam;
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
public class School extends EasyHttpBaseParam{

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
