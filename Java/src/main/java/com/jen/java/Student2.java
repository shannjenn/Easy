package com.jen.java;

public class Student2 {
    static {
        System.out.println("Student 静态代码块2");
    }

    {
        System.out.println("Student 构造代码块2");
    }

    public Student2() {
        System.out.println("Student 构造方法");
    }
}
