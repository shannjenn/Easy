package com.jen.easy;

import com.jen.easy.aop.DynamicProxyManager;
import com.jen.easy.decrypt.FileDecryptManager;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */
public final class EasyClass {
    private EasyClass() {
    }

    /**
     * AOP动态代理
     * 创建人：ShannJenn
     * 时间：2017/8/16.
     */
    public static final class DynamicProxy extends DynamicProxyManager {

        /**
         * 绑定对象
         *
         * @param target
         * @return
         */
        @Override
        public Object bind(Object target) {
            return super.bind(target);
        }


        /**
         * 切入前执行
         *
         * @param beforeClzz
         * @param beforeParams
         * @return
         */
        @Override
        public DynamicProxyManager setBeforeMethod(Class<?> beforeClzz, Object... beforeParams) {
            return super.setBeforeMethod(beforeClzz, beforeParams);
        }

        /**
         * 切入后执行
         *
         * @param afterClzz
         * @param afterParams
         * @return
         */
        @Override
        public DynamicProxyManager setAfterMethod(Class<?> afterClzz, Object... afterParams) {
            return super.setAfterMethod(afterClzz, afterParams);
        }
    }


    /**
     * 文件加密
     * 创建人：ShannJenn
     * 时间：2017/8/16.
     */
    public static final class FileDecrypt extends FileDecryptManager {

        /**
         * 解密
         *
         * @param strFile  源文件绝对路径
         * @param password
         * @return
         */
        @EasyMouse.AOP.before
        @Override
        public boolean encrypt(String strFile, String password) {
            return super.encrypt(strFile, password);
        }

        /**
         * 加密
         *
         * @param strFile  源文件绝对路径
         * @param password
         * @return
         */
        @EasyMouse.AOP.after
        @Override
        public boolean decrypt(String strFile, String password) {
            return super.decrypt(strFile, password);
        }
    }
}
