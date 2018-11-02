package com.jen.java;

import java.util.ArrayList;
import java.util.List;

public class javaClass {
    public static void main(String[] args) {

        format("9999999999.99");
        System.out.println("");

    }

    private static void format(String text){
        String[] arrr = text.split("\\.");
        if (arrr.length > 1) {
            text = arrr[0];
        }
        int b = text.length() / 3;
        String sss = "";
        if (text.length() >= 3) {
            int yushu = text.length() % 3;
            if (yushu == 0) {
                b = text.length() / 3 - 1;
                yushu = 3;
            }
            for (int i = 0; i < b; i++) {
                sss = sss + text.substring(0, yushu) + "," + text.substring(yushu, 3);
                text = text.substring(3, text.length());
            }
            if (arrr.length > 1) {
                sss = sss + text + "." + arrr[1];
            } else {
                sss = sss + text;
            }
            System.out.println(sss);
//            mAomt.setText(sss);
        }
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

}
