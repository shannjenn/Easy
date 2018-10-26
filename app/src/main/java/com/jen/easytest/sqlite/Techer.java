package com.jen.easytest.sqlite;


import com.jen.easy.EasyColumn;
import com.jen.easy.EasyTable;

@EasyTable("Techer")
public class Techer {

    private int id;

    private String name;

    @EasyColumn(invalid = true)
    private int age;

}
