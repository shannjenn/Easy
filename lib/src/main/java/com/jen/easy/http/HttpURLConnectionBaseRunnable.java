package com.jen.easy.http;

import com.jen.easy.constant.TAG;
import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.log.EasyLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class HttpURLConnectionBaseRunnable extends HttpURLConnectionRunnable {
    private HttpBaseListener baseListener;

    HttpURLConnectionBaseRunnable(HttpBaseRequest request, HttpBaseListener baseListener) {
        super(request);
        this.baseListener = baseListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mJsonParam.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        EasyLog.d(TAG.EasyHttp, mUrlStr + " Http请求返回码：" + mResponseCode);
        if ((mResponseCode == 200)) {
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            StringBuilder resultBuffer = new StringBuilder("");
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, mCharset));
            String s;
            while ((s = reader.readLine()) != null) {
                if (mRequest.state == HttpState.STOP) {
                    break;
                }
                resultBuffer.append(s);
            }
            reader.close();
            inStream.close();
            connection.disconnect();
            String result = resultBuffer.toString();
            success(result, headMap);
        } else {
            fail(" 网络请求异常：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        HttpBaseRequest request = (HttpBaseRequest) this.mRequest;
        if (baseListener != null && request.state == HttpState.RUN) {
            EasyLog.d(TAG.EasyHttp, mUrlStr + " 返回数据：" + result);
            HttpParseManager parseManager = new HttpParseManager();
            parseManager.setHttpState(request.state);
            Object parseObject = parseManager.parseJson(mResponse, result, headMap);
            if (request.state == HttpState.STOP) {
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 线程停止!\n   ");
            } else if (parseObject == null) {
                fail("解析数据解析出错\n   ");
            } else {
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 成功!\n   ");
                baseListener.success(request.flagCode, request.flagStr, parseObject);
            }
        }
    }

    @Override
    protected void fail(String result) {
        EasyLog.w(TAG.EasyHttp, mUrlStr + " " + result + "\n   ");
        HttpBaseRequest request = (HttpBaseRequest) mRequest;
        if (baseListener != null && request.state == HttpState.RUN)
            baseListener.fail(request.flagCode, request.flagStr, result);
    }
}