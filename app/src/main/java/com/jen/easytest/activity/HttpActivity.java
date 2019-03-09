package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.http.Http;
import com.jen.easy.http.TestHttp;
import com.jen.easy.http.HttpTool;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.http.request.AirRequest;
import com.jen.easytest.http.request.AirRequest2;
import com.jen.easytest.http.request.PutRequest;
import com.jen.easytest.http.request.QNRequest;
import com.jen.easytest.http.response.StockQuotationResponse;
import com.jen.easytest.request.SystemParamRequest;
import com.jen.easyui.base.EasyActivity;

import org.json.JSONObject;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class HttpActivity extends EasyActivity {
    Http http = new Http(5);//设置请求最大线程数量值5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
//        EasyMain.mHttp.setDefaultBaseListener(httpListener);
    }

    @Override
    protected void intDataBeforeView() {
        http.setHttpBaseListener(httpListener);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void loadDataAfterView() {

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
                TestHttp.httpReflectManager_test(airRequest);
                break;
            }
            case R.id.response_parse: {
                AirRequest airRequest = new AirRequest();
                JSONObject jsonObject = TestHttp.httpReflectManager_test(airRequest);
                AirRequest2 airRequest2 = TestHttp.httpParseManager_test(AirRequest2.class, jsonObject.toString(), null);
                EasyLog.d("----------");
                break;
            }

        }
    }

    private void testParse() {
//        String data = "{\"code\":0,\"result\":{\"data\":[[\"310.6\",\"5.600\",\"0.01836065573770499\",\"60\",\"HKD\",\"100\"]]}}";
        String data = "{\"code\":0,\"result\":{\"data\":[[\"310.6\",\"5.600\"]],\"data1\":[[31,5]],\"data2\":[[310.6,5.600]],\"data3\":[{\"id\": 111,\"name\": \"名字\"}]}}";
        HttpTool.parseResponse(StockQuotationResponse.class, data);
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

    /**
     * 宝库数据返回
     */
    HttpBaseListener httpListener = new HttpBaseListener() {
        @Override
        public void success(int flagCode, String flag, Object response) {

        }

        @Override
        public void fail(int flagCode, String flag, String msg) {

        }

        /*@Override
        public void fail(int flagCode; String flag; String msg) {
            EasyLog.d("exampleRequest fail:" + msg);
        }

        @Override
        public void success(int flagCode; String flag; Object airResponse) {
            EasyLog.d("exampleRequest success");
        }*/
    };

    /*@Override
    public void success(int flagCode; String flag; Object response) {

    }

    @Override
    public void fail(int flagCode; String flag; String msg) {

    }*/
}
