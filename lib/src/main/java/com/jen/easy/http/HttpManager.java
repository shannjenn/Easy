package com.jen.easy.http;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jen on 2017/7/21.
 */

abstract class HttpManager {
    private final String TAG = "HttpManager : ";
    private ExecutorService pool;
    private int maxThreadSize = 100;

    private String method = "GET";
    private String charset = Constant.Unicode.DEFAULT;
    private String contentType = "text/html";
    private String connection = "Keep-Alive";

    private int timeout = 30 * 1000;
    private int readTimeout = 30 * 1000;


    protected HttpManager() {
        pool = Executors.newFixedThreadPool(maxThreadSize);
    }

    /**
     * 开始
     *
     * @param request
     */
    protected void start(HttpRequest request) {
        start(request, null);
    }

    /**
     * 开始
     *
     * @param request
     */
    protected void start(HttpRequest request, HttpResponse response) {
        if (request == null) {
            EasyLog.w(TAG + "start 参数为空");
            return;
        }
        setDefault(request);
        if (request instanceof HttpUploadRequest) {
            HttpURLConnectionUploadRunable upload = new HttpURLConnectionUploadRunable((HttpUploadRequest) request);
            upload.setResponse(response);
            pool.execute(upload);
        } else if (request instanceof HttpDownloadPRequest) {
            HttpURLConnectionDownloadRunable download = new HttpURLConnectionDownloadRunable((HttpDownloadPRequest) request);
            pool.execute(download);
        } else {
            HttpURLConnectionRunable base = new HttpURLConnectionRunable((HttpBaseRequest) request);
            base.setResponse(response);
            pool.execute(base);
        }
    }

    /**
     * 用户未设置时，设置为默认值
     *
     * @param param
     */
    private void setDefault(HttpRequest param) {
        if (param.http.method == null)
            param.http.method = method;
        if (param.http.charset == null)
            param.http.charset = charset;
        if (param.http.contentType == null)
            param.http.contentType = contentType;
        if (param.http.connection == null)
            param.http.connection = connection;
        if (param.http.timeout == -1)
            param.http.timeout = timeout;
        if (param.http.readTimeout == -1)
            param.http.readTimeout = readTimeout;
    }

    /**
     * 设置默认请求方式
     *
     * @param method
     */
    protected void setMethod(String method) {
        this.method = method;
    }

    /**
     * 设置默认编码
     *
     * @param charset
     */
    protected void setCharset(String charset) {
        this.charset = charset;
    }


    /**
     * 设置默认
     *
     * @param contentType
     */
    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 设置默认超时
     *
     * @param timeout
     */
    protected void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置默认读取超时
     *
     * @param readTimeout
     */
    protected void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 设置默认字符编码连接参数
     *
     * @param connection
     */
    protected void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * 设置线程池最大数量
     *
     * @param maxThreadSize
     */
    protected void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

}
