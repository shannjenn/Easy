package com.jen.java;

import java.util.List;

public class javaClass {
    public static void main(String[] args){
        String str = Object.class.toString();
        System.out.println(str);

        boolean type = "java.util.List<com.zte.icenter.entity.netentity.TaskUserInfo>".contains(List.class.toString().replace("interface ",""));
        System.out.print(type);
    }

    public static class Student{

    }
}
