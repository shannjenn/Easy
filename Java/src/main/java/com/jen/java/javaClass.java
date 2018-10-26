package com.jen.java;

import java.util.ArrayList;
import java.util.List;

public class javaClass {
    public static void main(String[] args) {
        HttpState httpState = HttpState.RUN;
       long t1= System.currentTimeMillis();
        if (httpState == HttpState.STOP) {

        }
        long t2= System.currentTimeMillis();
        System.out.println(t2-t1);

        String a =null;
        long t3= System.currentTimeMillis();
        if(a == null){

        }
        long t4= System.currentTimeMillis();
        System.out.println(t4-t3);


        String b = "bbbbbbb";
        b = test();
        System.out.println(b);
    }

    private static String test(){
        String a="a";
        if(a.equals("a")){
//            throw new NullPointerException("aaaaaaaaaaaaaa");
        }

        return a;
    }

    public static class Student {
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

    public static class A {
        public HttpState state = HttpState.RUN;
    }

    public static class B {
        public HttpState state = HttpState.RUN;
    }
}
