package com.jen.easy.decrypt;

class FileDecryptPassword {
    private static final String defautPassword = "louxiumeiwoaini520";

    static byte[] getPassword(String password) {
        byte[] psw = password.getBytes();
        byte[] depsw = defautPassword.getBytes();
        int len = psw.length > depsw.length ? depsw.length : psw.length;
        byte[] psws = new byte[len];
        for (int i = 0; i < len; i++) {
            psws[i] = (byte) (psw[i] ^ depsw[i]);
        }
        return psws;
    }
}
