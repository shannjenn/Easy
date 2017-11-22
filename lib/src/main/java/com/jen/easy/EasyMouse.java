package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释类
 * <p>
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public final class EasyMouse {
    private EasyMouse() {
    }

    /*****************************************************************************
     ★★★ 数据库 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
     ****************************************************************************/
    public final static class DB {
        private DB() {
        }

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Table {

            /**
             * 表名
             */
            String value();
        }

        @Target(ElementType.FIELD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Column {
            /**
             * 字段名称
             */
            String columnName() default "";

            /**
             * 是否为主键
             */
            boolean primaryKey() default false;

            /**
             * 外键名称
             */
            String foreignKey() default "";
        }
    }

    /*******************************************************************************
     ★★★ 控件绑定 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
     ******************************************************************************/
    public final static class BIND {
        private BIND() {
        }

        @Target({ElementType.FIELD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ID {

            /**
             * 绑定ID值
             */
            int value() default -1;
        }

        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Method {
            /**
             * 绑定点击事件
             */
            int[] value();
        }
    }

    /*******************************************************************************
     ★★★ 网络请求 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
     ******************************************************************************/
    public final static class HTTP {
        private HTTP() {
        }

        /**
         * GET网络请求地址
         */
        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface GET {
            /**
             * 参数名称
             */
            String URL() default "";

            /**
             * 返回实体类
             */
            Class Response() default Object.class;
        }

        /**
         * POST网络请求地址
         */
        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface POST {
            /**
             * 参数名称
             */
            String URL() default "";

            /**
             * 返回实体类
             */
            Class Response() default Object.class;
        }

        /**
         * 网络请求参数
         */
        @Target(ElementType.FIELD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface RequestParam {
            /**
             * 参数名称
             */
            String value() default "";
        }

        /**
         * 网络返回参数
         * Created by Jen on 2017/7/21.
         */
        @Target(ElementType.FIELD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ResponseParam {
            /**
             * 参数名称
             */
            String value() default "";

            /**
             * Object类型变量指定具体类型(解析是可以为List获取对象)
             * （@EasyMouse.mHttp.ResponseParam(clazz="Student.class") 注释返回参数）
             *  （@private Object student;）
             */
            Class clazz() default Object.class;
        }
    }

    /*******************************************************************************
     ★★★ 面向切入 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
     ******************************************************************************/
    public final static class AOP {
        private AOP() {
        }

        /**
         * 切入编程前
         */
        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface before {
        }

        /**
         * 切入编程后
         */
        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface after {
        }
    }

}
