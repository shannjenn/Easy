package com.jen.easy.http;

import com.jen.easy.constant.FieldType;
import com.jen.easy.constant.TAG;
import com.jen.easy.constant.Unicode;
import com.jen.easy.http.imp.HttpBasicListener;
import com.jen.easy.log.EasyLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class HttpURLConnectionBasicRunnable extends HttpURLConnectionRunnable {
    private HttpBasicListener baseListener;

    HttpURLConnectionBasicRunnable(HttpBasicRequest request, HttpBasicListener baseListener) {
        super(request);
        this.baseListener = baseListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mBody.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        EasyLog.d(TAG.EasyHttp, mUrlStr + " Http请求返回码：" + mResponseCode);
        if ((mResponseCode == 200)) {
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            StringBuilder resultBuffer = new StringBuilder();
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, mCharset));
            String s;
            while ((s = reader.readLine()) != null) {
                resultBuffer.append(s);
            }
            reader.close();
            inStream.close();
            connection.disconnect();
            if (mRequest.getStatus() != HttpRequestStatus.RUN) {//拦截数据解析
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            String result = resultBuffer.toString();
            EasyLog.d(TAG.EasyHttp, mUrlStr + " 返回原始数据：" + result);
            if (mRequest.replaceHttpResultMap != null && mRequest.replaceHttpResultMap.size() > 0) {
                result = replaceStringBeforeParseResponse(result);
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 格式化后数据：" + result);
            }
            mRequest.status = HttpRequestStatus.FINISH;
            success(result, headMap);
        } else {
            if (mRequest.getStatus() != HttpRequestStatus.RUN) {//拦截数据解析
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 网络请求停止!\n   ");
                return;
            }
            mRequest.status = HttpRequestStatus.FINISH;
            fail(" 网络请求异常：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (baseListener != null) {
            if (FieldType.isObject(mResponse) || FieldType.isString(mResponse)) {//Object和String类型不做数据解析
                EasyLog.d(TAG.EasyHttp, mUrlStr + " 成功!\n   ");
                baseListener.success(mRequest.flagCode, mRequest.flagStr, result);
            } else {
                HttpParseManager parseManager = new HttpParseManager();
                Object parseObject = parseManager.parseResponseFromJSONString(mResponse, result, headMap);
                if (parseObject == null) {
                    fail("解析数据解析出错\n   ");
                } else {
                    EasyLog.d(TAG.EasyHttp, mUrlStr + " 成功!\n   ");
                    baseListener.success(mRequest.flagCode, mRequest.flagStr, parseObject);
                }
            }
        }
    }

    @Override
    protected void fail(String result) {
        EasyLog.w(TAG.EasyHttp, mUrlStr + " " + result + "\n   ");
        if (baseListener != null)
            baseListener.fail(mRequest.flagCode, mRequest.flagStr, result);
    }
}