package com.jen.easy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyA {

    /**
     * 数据库********************
     */
    public abstract static class DB {
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
    public abstract static class BIND {
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
    public abstract static class HTTP {
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

}
