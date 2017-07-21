package com.jen.easy.http;

import com.jen.easy.http.param.EasyHttpDownloadFileParam;
import com.jen.easy.http.param.EasyHttpParam;
import com.jen.easy.http.param.EasyHttpUploadFileParam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jen on 2017/7/21.
 */

public class EasyHttp {
    private static EasyHttp instance;
    private ExecutorService pool;
    private int maxThreadSize = 100;

    private String method = "GET";
    private String charset = "utf-8";
    private String contentType = "text/html";

    private int timeout = 30 * 1000;
    private int readTimeout = 30 * 1000;


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

    /**
     * 开始
     *
     * @param param
     */
    public void start(EasyHttpParam param) {
        if (param == null) {
            HttpLog.w("参数为空");
            return;
        }
        setDefault(param);
        if (param instanceof EasyHttpUploadFileParam) {
            HttpURLConnectionUploadFileRunable upload = new HttpURLConnectionUploadFileRunable((EasyHttpUploadFileParam) param);
            pool.execute(upload);
        } else if (param instanceof EasyHttpDownloadFileParam) {
            HttpURLConnectionDownloadFileRunable download = new HttpURLConnectionDownloadFileRunable((EasyHttpDownloadFileParam) param);
            pool.execute(download);
        } else {
            HttpURLConnectionRunable httpURLConnectionRunable = new HttpURLConnectionRunable(param);
            pool.execute(httpURLConnectionRunable);
        }
    }

    /**
     * 用户未设置时，设置为默认值
     *
     * @param param
     */
    private void setDefault(EasyHttpParam param) {
        if (param.getMethod() == null)
            param.setMethod(method);
        if (param.getCharset() == null)
            param.setCharset(charset);
        if (param.getContentType() == null)
            param.setContentType(contentType);
        if (param.getTimeout() == -1)
            param.setTimeout(timeout);
        if (param.getReadTimeout() == -1)
            param.setReadTimeout(readTimeout);
    }

    /**
     * 设置默认请求方式
     *
     * @param method
     */
    public void setDefaultMethod(String method) {
        this.method = method;
    }

    /**
     * 设置默认编码
     *
     * @param charset
     */
    public void setDefaultCharset(String charset) {
        this.charset = charset;
    }


    /**
     * 设置默认
     *
     * @param contentType
     */
    public void setDefaultContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 设置默认超时
     *
     * @param timeout
     */
    public void setDefaultTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置默认读取超时
     *
     * @param readTimeout
     */
    public void setDefaultReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 设置线程池最大数量
     *
     * @param maxThreadSize
     */
    public void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

}
