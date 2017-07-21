package com.jen.easy.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpURLConnectionBreakFileRunable implements Runnable {
    private final String TAG = "HttpURLConnectionRunable";
    private EasyHttpParam param;
    private DownloadListener downloadListener;
    private final long UNIT_SIZE = 10 * 1024;
    private boolean mCancel;
    private boolean isRunning;

    public HttpURLConnectionBreakFileRunable(EasyHttpParam param) {
        super();
        this.param = param;
    }

    @Override
    public void run() {
        int result = EasyHttpCode.FAIL;
        if (param == null || param.url == null) {
            HttpLog.e("param or url is null");
            if (downloadListener != null)
                downloadListener.finish(param.mark, param.parampos, result);
            return;
        }

        //-------------------------文件传输
        //下载文件
        boolean isDownLoad = false;
        //下载文件位置*/
        String downLoadFile = null;
        //上传文件
        boolean isUpLoad = false;
        //下载文件位置
        String upLoadFile = null;
        if (param instanceof EasyHttpFileParam) {
            EasyHttpFileParam fileParam = (EasyHttpFileParam) param;
            isDownLoad = fileParam.isDownLoad;
            downLoadFile = fileParam.downLoadFile;
            isUpLoad = fileParam.isUpLoad;
            upLoadFile = fileParam.upLoadFile;
        }

        //-------------------------断点文件传输
        boolean isBreakFile = false;
        //断点开始位置
        long startPoit = 0;
        //断点结束位置
        long endPoit = 0;
        if (param instanceof EasyHttpBreakFileParam) {
            EasyHttpBreakFileParam breakFileParam = (EasyHttpBreakFileParam) param;
            startPoit = breakFileParam.startPoit;
            endPoit = breakFileParam.endPoit;
            isBreakFile =true;
        }

        isRunning = true;

        if (startPoit >= 2 * 1024) {
            startPoit -= 2 * 1024;
        } else {
            startPoit = 0;
        }

        long curbytes = startPoit;
        HttpURLConnection connection = null;
        RandomAccessFile randFile = null;
        InputStream inStream = null;
        try {
            URL url = new URL(param.url);
            connection = (HttpURLConnection) url.openConnection();
            // connection.setDoInput(true);
            // connection.setDoOutput(true);
            //connection.setUseCaches(false);
            connection.setRequestProperty("Charset", param.charset);
            connection.setConnectTimeout(param.timeout);
            connection.setReadTimeout(param.timeout);
            connection.setRequestProperty("Content-Type", "text/html");
            connection.setRequestMethod("GET");
            //	connection.setRequestProperty("Range", "bytes=" + param.start + "-" + param.end);

            HttpLog.d(TAG, "ResponseCode=" + connection.getResponseCode());
            if ((connection.getResponseCode() == 200)) {
                param.end = connection.getContentLength();
                inStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                randFile = new RandomAccessFile(param.path, "rwd");
                randFile.seek(param.start);
                int len = 0;
                int unit = 1;
                while ((len = inStream.read(buffer)) != -1) {
                    if (mCancel)
                        break;
                    randFile.write(buffer, 0, len);
                    curbytes += len;
                    HttpLog.d(TAG, "curbytes = " + curbytes);
                    if (downloadListener != null && curbytes > UNIT_SIZE * unit && !mCancel) {
                        unit++;
                        downloadListener.process(param.dutyIdAndCoureId, param.pos, curbytes, param.end);
                    }
                }
                if (downloadListener != null && !mCancel)
                    downloadListener.process(param.dutyIdAndCoureId, param.pos, curbytes, param.end);
            }
            if (mCancel) {
                result = EasyHttpCode.CANCEL;
            } else if (curbytes == param.end) {
                result = EasyHttpCode.SUCCESS;
            }/* else if (curbytes < 1024) {
                result = EasyHttpCode.FAIL;
			} else if (curbytes < param.end) {
				result = EasyHttpCode.BREAK;
			}*/ else {
                result = EasyHttpCode.FAIL;
            }
        } catch (MalformedURLException e) {
            HttpLog.e(TAG, "MalformedURLException error --------");
            e.printStackTrace();
        } catch (ProtocolException e) {
            HttpLog.e(TAG, "ProtocolException error --------");
            e.printStackTrace();
        } catch (IOException e) {
            HttpLog.e(TAG, "IOException error --------");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            HttpLog.e(TAG, "IllegalStateException error --------");
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (null != randFile)
                    randFile.close();
                if (null != inStream)
                    inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        isRunning = false;
        HttpLog.d(TAG, "result = " + result);
        if (downloadListener != null) {
            downloadListener.finish(param.dutyIdAndCoureId, param.pos, result);
        }
    }

    protected interface DownloadListener {
        void process(String dutyIdAndCoureId, int pos, long progress, long size);

        /**
         * @param flag   标记
         * @param result 1:成功，-1：失败，2断网
         */
        void finish(String dutyIdAndCoureId, int pos, int result);
    }

    protected void setPoolListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public DownloadParam getParam() {
        return param;
    }

    public void cancel() {
        mCancel = true;
        if (!isRunning && downloadListener != null) {
            downloadListener.finish(param.dutyIdAndCoureId, param.pos, EasyHttpCode.CANCEL);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

}