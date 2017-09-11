package com.jen.easy.decrypt;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLog;

import java.io.UnsupportedEncodingException;

class FileDecryptPassword {
    private final static String TAG = FileDecryptPassword.class.getSimpleName() + " : ";
    private static final String defautPassword = "louxiumeiwoaini520";

    static byte[] getPassword(String password) {
        if (password == null) {
            EasyLog.e(TAG + "getPassword 密码不能为空");
            return null;
        }
        try {
            byte[] psw = password.getBytes(Constant.Unicode.DEFAULT);
            byte[] depsw = defautPassword.getBytes(Constant.Unicode.DEFAULT);
            int len = psw.length > depsw.length ? depsw.length : psw.length;
            byte[] psws = new byte[len];
            for (int i = 0; i < len; i++) {
                psws[i] = (byte) (psw[i] ^ depsw[i]);
            }
            return psws;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "getPassword 编码转换错误");
            return null;
        }
    }
}
