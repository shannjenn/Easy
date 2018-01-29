package com.jen.easyui.util;

import java.io.File;

/**
 * 作者：ShannJenn
 * 时间：2017/12/14:19:53
 * 说明：
 */

public class FileUtil extends FileUtilManager {

    @Override
    public void deleteFile(String path) {
        super.deleteFile(path);
    }

    @Override
    public void deletFile(File file) {
        super.deletFile(file);
    }

    @Override
    public void deleteDir(String path) {
        super.deleteDir(path);
    }

    @Override
    public void deleteDir(File dir) {
        super.deleteDir(dir);
    }
}
