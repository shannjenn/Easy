package com.jen.easy.http;

import com.jen.easy.EasyFactory;
import com.jen.easy.constant.Constant;
import com.jen.easy.http.imp.HttpImp;
import com.jen.easy.http.param.factory.ParamFather;
import com.jen.easy.log.EasyLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jen on 2017/7/21.
 */

public class HttpManager implements HttpImp {
    private ExecutorService pool;
    private int maxThreadSize = 100;

    private String method = "GET";
    private String charset = Constant.Unicode.DEFAULT;
    private String contentType = "text/html";
    private String connection = "Keep-Alive";

    private int timeout = 30 * 1000;
    private int readTimeout = 30 * 1000;


    public HttpManager() {
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param param
     */
    @Override
    public void start(ParamFather param) {
        if (param == null) {
            EasyLog.w("参数为空");
            return;
        }
        setDefault(param);
        if (param instanceof EasyFactory.HTTP.UploadParam) {
            HttpURLConnectionUploadRunable upload = new HttpURLConnectionUploadRunable((EasyFactory.HTTP.UploadParam) param);
            pool.execute(upload);
        } else if (param instanceof EasyFactory.HTTP.DownloadParam) {
            HttpURLConnectionDownloadRunable download = new HttpURLConnectionDownloadRunable((EasyFactory.HTTP.DownloadParam) param);
            pool.execute(download);
        } else {
            HttpURLConnectionRunable httpURLConnectionRunable = new HttpURLConnectionRunable((EasyFactory.HTTP.BaseParam) param);
            pool.execute(httpURLConnectionRunable);
        }
    }

    /**
     * 用户未设置时，设置为默认值
     *
     * @param param
     */
    private void setDefault(ParamFather param) {
        if (param.httpParam.method == null)
            param.httpParam.method = method;
        if (param.httpParam.charset == null)
            param.httpParam.charset = charset;
        if (param.httpParam.contentType == null)
            param.httpParam.contentType = contentType;
        if (param.httpParam.connection == null)
            param.httpParam.connection = connection;
        if (param.httpParam.timeout == -1)
            param.httpParam.timeout = timeout;
        if (param.httpParam.readTimeout == -1)
            param.httpParam.readTimeout = readTimeout;
    }

    /**
     * 设置默认请求方式
     *
     * @param method
     */
    @Override
    public void setDefaultMethod(String method) {
        this.method = method;
    }

    /**
     * 设置默认编码
     *
     * @param charset
     */
    @Override
    public void setDefaultCharset(String charset) {
        this.charset = charset;
    }


    /**
     * 设置默认
     *
     * @param contentType
     */
    @Override
    public void setDefaultContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 设置默认超时
     *
     * @param timeout
     */
    @Override
    public void setDefaultTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置默认读取超时
     *
     * @param readTimeout
     */
    @Override
    public void setDefaultReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 设置默认字符编码连接参数
     *
     * @param connection
     */
    @Override
    public void setDefaultConnection(String connection) {
        this.connection = connection;
    }

    /**
     * 设置线程池最大数量
     *
     * @param maxThreadSize
     */
    @Override
    public void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

}
