package com.jen.java;

import java.util.ArrayList;
import java.util.List;

public class javaClass {
    public static void main(String[] args){

        A a = new A();
        a.state = HttpState.STOP;
        B b = new B();
        System.out.println(b.state);
        b.state = a.state;
        System.out.println(b.state);


        Class aa = A.class;
        if(aa instanceof Class){
            System.out.println("aa is Class");
        }

        Object bb = a;
        if(bb instanceof Class){
            System.out.println("aa is Class");
        }
    }

    public static class Student{
//        private String aa;
        private int bb;
        private Integer b;
        private float cc;
        private Float c;
        private List<Student> dd;
        private ArrayList<Student> d;
//        private List<String> ee;
//        private Student e;
//        private Object eee;
    }

    public static class A{
        public HttpState state = HttpState.RUN;
    }
    public static class B{
        public HttpState state = HttpState.RUN;
    }
}
