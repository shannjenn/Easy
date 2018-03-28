package com.jen.easytest.sqlite;


import com.jen.easy.Easy;

@Easy.DB.Table("Techer")
public class Techer {

    private int id;

    private String name;

    @Easy.DB.Column(noColumn = true)
    private int age;

}
