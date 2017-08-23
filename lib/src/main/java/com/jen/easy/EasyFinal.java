package com.jen.easy;

/**
 * 常量
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public final class EasyFinal {
    private EasyFinal() {
    }

    /**
     * ********************
     */
    public static final class HTTP {
        private HTTP() {
        }

        public static final class Code {
            private Code() {
            }

            /**
             * 成功
             */
            public final static int SUCCESS = 0;
            /**
             * 失败
             */
            public final static int FAIL = 1;
            /**
             * 超时
             */
            public final static int TIME_OUT = 2;
            /**
             * 用户取消
             */
            public final static int USER_CANCEL = 3;
        }
    }

}
