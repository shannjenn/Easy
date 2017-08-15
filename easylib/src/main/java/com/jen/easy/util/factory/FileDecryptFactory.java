package com.jen.easy.util.factory;

import com.jen.easy.util.FileDecryptManager;

public abstract class FileDecryptFactory {

    public static FileDecryptFactory getFileDecrypt() {
        return new FileDecryptManager();
    }

    public static Class<?> getFileDecryptClass() {
        return FileDecryptManager.class;
    }

    /**
     * 解密
     *
     * @param strFile 源文件绝对路径
     * @return
     */
    public abstract boolean encrypt(String strFile, String password);

    /**
     * 加密
     *
     * @param strFile 源文件绝对路径
     * @return
     */
    public abstract boolean decrypt(String strFile, String password);
}
