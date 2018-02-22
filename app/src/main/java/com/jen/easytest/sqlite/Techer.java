package com.jen.easytest.sqlite;


import com.jen.easy.EasyMouse;

@EasyMouse.DB.Table("Techer")
public class Techer {

    private int id;

    private String name;

    @EasyMouse.DB.Column(noColumn = true)
    private int age;

}
