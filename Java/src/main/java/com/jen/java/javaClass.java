package com.jen.java;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class javaClass {
    public static void main(String[] args) {

        double aa = 2751545454645646546.235;

        String str = formatDouble(aa/100000000, 2);
        System.out.println(str);

    }

    private static String formatDouble(double value, int decimal) {
        String unit = "%." + decimal + "f";
        return String.format(unit, value);
    }

    private String fromt_2(double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(num);
    }

    private String fromt_3(double num) {
        DecimalFormat df = new DecimalFormat("#.000");
        return df.format(num);
    }

    private static void format(String text) {
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

    private static String test() {
        String a = "a";
        if (a.equals("a")) {
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
