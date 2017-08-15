package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @类 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public final class EasyMouse {
    private EasyMouse() {
    }

    /**
     * 数据库********************
     */
    public final static class DB {
        private DB() {
        }

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Table {

            /**
             * 表名
             */
            String tableName();
        }

        @Target(ElementType.FIELD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Column {
            /**
             * 字段名称
             */
            String columnName();

            /**
             * 是否为主键
             */
            boolean primaryKey() default false;
        }
    }

    /**
     * 控件绑定********************
     */
    public final static class BIND {
        private BIND() {
        }

        @Target({ElementType.FIELD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ID {

            /**
             * 绑定ID值
             */
            int ID();
        }

        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Method {
            /**
             * 绑定点击事件
             */
            int[] onClick();
        }
    }

    /**
     * 网络请求********************
     */
    public final static class HTTP {
        private HTTP() {
        }

        /**
         * 网络请求对象
         * Created by Jen on 2017/7/21.
         */
        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Model {

            /**
             * 对象名称
             *
             * @return
             */
            String modelName();
        }

        /**
         * 网络请求参数名
         * Created by Jen on 2017/7/21.
         */
        @Target(ElementType.FIELD)
        @Retention(RetentionPolicy.RUNTIME)
        public @interface Param {

            /**
             * 参数名称
             *
             * @return
             */
            String paramName();
        }
    }

    /**
     * 面向切入********************
     */
    public final static class AOP {
        private AOP() {
        }

        /**
         * 切入编程前
         * Created by Jen on 2017/7/21.
         */
        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface SingleBefore {
        }

        /**
         * 切入编程后
         * Created by Jen on 2017/7/21.
         */
        @Target({ElementType.METHOD})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface SingleAfter {
        }
    }

}
