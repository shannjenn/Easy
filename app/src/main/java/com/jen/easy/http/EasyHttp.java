package com.jen.easy.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jen on 2017/7/21.
 */

public class EasyHttp {
    private static EasyHttp instance;
    private ExecutorService pool;
    private int maxThreadSize = 100;


    private EasyHttp() {
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static EasyHttp getInstance() {
        if (instance == null) {
            instance = new EasyHttp();
        }
        return instance;
    }

    public void setTimeOut() {

    }


    public void init() {

    }

    public int getMaxThreadSize() {
        return maxThreadSize;
    }

    public void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }
}
