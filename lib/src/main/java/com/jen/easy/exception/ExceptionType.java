package com.jen.easy.exception;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：网络请求运行状态
 * <p>
 * * Android常见的几种RuntimeException
 * * 1、NullPointerException - 空指针引用异常
 * * 2、ClassCastException - 类型强制转换异常。
 * * 3、IllegalArgumentException - 传递非法参数异常。
 * * 4、ArithmeticException - 算术运算异常
 * * 5、ArrayStoreException - 向数组中存放与声明类型不兼容对象异常
 * * 6、IndexOutOfBoundsException - 下标越界异常
 * * 7、NegativeArraySizeException - 创建一个大小为负数的数组错误异常
 * * 8、NumberFormatException - 数字格式异常
 * * 9、SecurityException - 安全异常
 * * 10、UnsupportedOperationException - 不支持的操作异常
 */
@IntDef({ExceptionType.NullPointerException, ExceptionType.ClassCastException, ExceptionType.NumberFormatException,
        ExceptionType.IllegalArgumentException, ExceptionType.RuntimeException, ExceptionType.InstantiationException,
        ExceptionType.IllegalAccessException})
@Retention(RetentionPolicy.SOURCE)
public @interface ExceptionType {
    int NullPointerException = 0;
    int ClassCastException = 1;
    int NumberFormatException = 2;
    int IllegalArgumentException = 3;
    int RuntimeException = 4;
    int InstantiationException = 5;
    int IllegalAccessException = 6;
}
