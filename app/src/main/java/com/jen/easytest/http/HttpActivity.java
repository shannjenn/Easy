package com.jen.easytest.http;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.http.EasyHttp;
import com.jen.easy.http.EasyHttpTool;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.http.request.AirRequest;
import com.jen.easytest.http.request.AirRequest2;
import com.jen.easytest.http.request.PutRequest;
import com.jen.easytest.http.request.QNRequest;
import com.jen.easytest.http.response.StockQuotationResponse;
import com.jen.easytest.request.LogcatRequest;
import com.jen.easytest.request.SystemParamRequest;

import easybase.EasyActivity;

import org.json.JSONObject;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class HttpActivity extends EasyActivity {
    EasyHttp http = new EasyHttp(5);//设置请求最大线程数量值5

    @Override
    public int bindView() {
        return R.layout.activity_http;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LogcatRequest uploadRequest = new LogcatRequest();
        uploadRequest.filePath = "/storage/emulated/0/_LogcatHelper/LogCatch-2019-08-15.txt";
        http.start(uploadRequest);
    }


    @EasyBindClick({R.id.get, R.id.post, R.id.put, R.id.upload, R.id.download, R.id.parse, R.id.request_parse, R.id.response_parse})
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.parse:
                testParse();
            case R.id.get:
//                JsonUtil.toJson("abc");
//                Throw.exception(ExceptionType.NullPointerException;"空指针异常");
                get();
                break;
            case R.id.post:
                post();
                break;
            case R.id.put:
                put();
                break;
            case R.id.upload:
                /*JSONObject jsonObject = new JSONObject();
                if(jsonObject.length() ==0){
                    EasyLog.d("0000000000");
                }
                try {
                    jsonObject.put("name","sdf");
                } catch (JSONException e) {
                    EasyLog.d("error");
                    e.printStackTrace();
                }
                if(jsonObject.length() ==0){
                    EasyLog.d("1111111");
                }*/

                break;
            case R.id.download:
                download();
                break;
            case R.id.request_parse: {
                AirRequest airRequest = new AirRequest();
//                TestHttp.httpReflectManager_test(airRequest);
                break;
            }
            case R.id.response_parse: {
                AirRequest airRequest = new AirRequest();
//                JSONObject jsonObject = TestHttp.httpReflectManager_test(airRequest);
//                AirRequest2 airRequest2 = TestHttp.httpParseManager_test(AirRequest2.class, jsonObject.toString());
                EasyLog.d("----------");
                break;
            }

        }
    }

    private void testParse() {
//        String data = "{\"code\":0,\"result\":{\"data\":[[\"310.6\",\"5.600\",\"0.01836065573770499\",\"60\",\"HKD\",\"100\"]]}}";
        String data = "{\"code\":0,\"result\":{\"data\":[[\"310.6\",\"5.600\"]],\"data1\":[[31,5]],\"data2\":[[310.6,5.600]],\"data3\":[{\"id\": 111,\"name\": \"名字\"}]}}";
        EasyHttpTool.parseResponse(StockQuotationResponse.class, data);
    }

    private void get() {
        /*TaskProgressListRequest taskProgressListRequest = new TaskProgressListRequest();
        taskProgressListRequest.setTaskId(353631);
        taskProgressListRequest.setLimit(3);
        taskProgressListRequest.setPage(0);
        http.setDefaultBaseListener(httpListener);
        http.start(taskProgressListRequest);*/

        QNRequest qnRequest = new QNRequest();
        http.start(qnRequest);
    }

    private void post() {
//        String paramId = SystemParamRequest.PARAM_ID_9 + ","//
//                + SystemParamRequest.PARAM_ID_11 + ","//
//                + SystemParamRequest.PARAM_ID_57 + ","//
//                + SystemParamRequest.PARAM_ID_95;//

        String paramId = SystemParamRequest.PARAM_ID_11;
        SystemParamRequest paramRequest = new SystemParamRequest();
        paramRequest.param.setParamId(paramId);
        http.start(paramRequest);
    }

    private void put() {
        PutRequest putRequest = new PutRequest();
        putRequest.setToken("16643-bdf0737cafb1c1a32451a0d93692edba");

        putRequest.setId("16643");
        http.start(putRequest);
    }

    private void download() {

    }
}
