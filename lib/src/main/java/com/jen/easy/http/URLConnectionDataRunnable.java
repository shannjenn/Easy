package com.jen.easy.http;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.HttpLog;
import com.jen.easy.http.imp.EasyHttpListener;
import com.jen.easy.http.request.EasyHttpDataRequest;
import com.jen.easy.http.request.EasyRequestState;
import com.jen.easy.log.JsonLogFormat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class URLConnectionDataRunnable extends URLConnectionFactoryRunnable {
    private EasyHttpListener baseListener;

    URLConnectionDataRunnable(EasyHttpDataRequest request, EasyHttpListener baseListener, int flagCode, String flagStr) {
        super(request, flagCode, flagStr);
        this.baseListener = baseListener;
    }

    @Override
    protected void childRun(HttpURLConnection connection) throws IOException {
        long startTime = System.currentTimeMillis();
        if (!mIsGet) {
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(mBody.toString().getBytes(Unicode.DEFAULT));
            out.flush();
            out.close();
        }

        mResponseCode = connection.getResponseCode();
        if ((mResponseCode == 200)) {
            Map<String, List<String>> headMap = connection.getHeaderFields();//获取head数据
            StringBuilder bodyBuffer = new StringBuilder();
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, mCharset));
            String s;
            while ((s = reader.readLine()) != null) {
                bodyBuffer.append(s);
            }
            reader.close();
            inStream.close();
            connection.disconnect();

            double timeSec = (System.currentTimeMillis() - startTime) / 1000d;
            String body = bodyBuffer.toString();

            final String COMMA = "\\,带英文逗号的值不做Json换行";
            StringBuilder returnLogBuild = new StringBuilder();
            returnLogBuild.append(mRequestLogInfo).append("\nresponse heads：\n{");
            for (Map.Entry<String, List<String>> entry : headMap.entrySet()) {
                returnLogBuild.append(entry.getKey());
                returnLogBuild.append(":");
                StringBuilder valueBuild = new StringBuilder();
                int size = entry.getValue().size();
                for (int i = 0; i < size; i++) {
                    if (i > 0) {
                        valueBuild.append(" && ");
                    }
                    valueBuild.append(entry.getValue().get(i));
                }
                String value = valueBuild.toString().replace(",", COMMA);
                returnLogBuild.append(value);
                returnLogBuild.append(",");
            }
            if (headMap.size() > 0) {
                returnLogBuild.deleteCharAt(returnLogBuild.length() - 1);
            }
            returnLogBuild.append("}");
            returnLogBuild.append("\nresponse body：\n").append(body);
            if (mRequest.getReplaceResult().size() > 0) {
                body = replaceResult(body);
                returnLogBuild.append("\nformat return body：\n").append(body);
            }
            returnLogBuild.append("\nresponse code：").append(mResponseCode).append(" time: ").append(timeSec).append(" second");
            HttpLog.i(JsonLogFormat.formatJson(returnLogBuild.toString()).replace(COMMA, ","));
            success(body, headMap);
        } else {
            double timeSec = (System.currentTimeMillis() - startTime) / 1000d;
            HttpLog.e(JsonLogFormat.formatJson(mRequestLogInfo + "\nresponse code：" + mResponseCode + " time: " + timeSec + " second"));
            fail("http request error：" + mResponseCode);
        }
    }

    @Override
    protected void success(String result, Map<String, List<String>> headMap) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.i(mUrlStr + " The request was interrupted.\n \t");//\n \t打印才出现空行
            return;
        }
        if (baseListener == null) {
            return;
        }
        baseListener.success(flagCode, flagStr, createResponseObjectSuccess(Type.data, result), headMap);
    }

    @Override
    protected void fail(String errorMsg) {
        if (mRequest.getRequestState() == EasyRequestState.interrupt) {
            HttpLog.i(mUrlStr + " The request was interrupted。\n \t");
            return;
        }
        if (baseListener == null) {
            return;
        }
        baseListener.fail(flagCode, flagStr, createResponseObjectFail(Type.data, errorMsg));
    }

}