package com.jen.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Created by zl on 2018/10/25.
 */
public class PatternTest {

    public static void test1() {
        String str1 = "\\\\\\";
        String str2 = "\\\\\\abcdefg";
        System.out.println("str1:" + str1);

        if (true) {
            throw new RuntimeException("自定义异常");
        }
//        String str2 = "\\";
//        System.out.println("str2:" + str2);
        Pattern pattern = Pattern.compile("\\\\");
        Matcher matcher = pattern.matcher("\\\\\\" + "abcdefg");
//        String result = matcher.replace("A");
        String result = str2.replace(str1, "B");
        System.out.println("result = " + result);
//        while (matcher.find()) {//查找符合pattern的字符串
//            System.out.println("查找到的：" + matcher.group());
//        }

    }

}
