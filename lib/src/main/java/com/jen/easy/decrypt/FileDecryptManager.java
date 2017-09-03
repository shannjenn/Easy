package com.jen.easy.decrypt;

import com.jen.easy.EasyMain;
import com.jen.easy.log.EasyLog;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileDecryptManager {
    private final String TAG = "FileDecryptManager : ";

    /**
     * 解密
     *
     * @param strFile 源文件绝对路径
     * @return
     */
    public boolean encrypt(String strFile, String password) {
        File file = new File(strFile);
        if (!file.exists() || !isDecripted(strFile)) {
            return true;
        }
        byte[] psws = FileDecryptPassword.getPassword(password);
        try {
            File f = new File(strFile);
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            long len = raf.length();
            if (len <= 0) {
                return true;
            }

            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, len);
            int times = 1;
            byte tmp;
            for (int i = 0; i < len; ++i) {
                int pos = i;
                if (i >= psws.length * (times + 1)) {
                    times++;
                    pos = i - psws.length * times;
                } else if (i >= psws.length * times) {
                    pos = i - psws.length * times;
                }
                byte psw = psws[pos];
                byte rawByte = buffer.get(i);
                tmp = (byte) (rawByte ^ i ^ psw);
                buffer.put(i, tmp);
            }
            buffer.force();
            buffer.clear();
            channel.close();
            raf.close();
            EasyMain.Share.setBoolean(strFile, false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLog.e(TAG + "encrypt 数据解密异常");
        }
        return false;
    }

    /**
     * 加密
     *
     * @param strFile 源文件绝对路径
     * @return
     */
    public boolean decrypt(String strFile, String password) {
        File file = new File(strFile);
        if (!file.exists() || isDecripted(strFile)) {
            return true;
        }
        byte[] psws = FileDecryptPassword.getPassword(password);
        try {
            File f = new File(strFile);
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            long len = raf.length();
            if (len <= 0) {
                return true;
            }

            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, len);
            int times = 1;
            byte tmp;
            for (int i = 0; i < len; ++i) {
                int pos = i;
                if (i >= psws.length * (times + 1)) {
                    times++;
                    pos = i - psws.length * times;
                } else if (i >= psws.length * times) {
                    pos = i - psws.length * times;
                }
                byte psw = psws[pos];
                byte rawByte = buffer.get(i);
                tmp = (byte) (rawByte ^ i ^ psw);
                buffer.put(i, tmp);
            }
            buffer.force();
            buffer.clear();
            channel.close();
            raf.close();
            EasyMain.Share.setBoolean(strFile, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            EasyLog.e(TAG + "decrypt 数据加密异常");
        }
        return false;
    }

    /**
     * 文件是否已经加密了
     *
     * @param strFile
     * @return
     */
    private boolean isDecripted(String strFile) {
        boolean result = EasyMain.Share.getBoolean(strFile);//true为已经加密
        return result;
    }
}
