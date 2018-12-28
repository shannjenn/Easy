package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyViewMethod;
import com.jen.easy.http.Http;
import com.jen.easy.http.HttpTestUtil;
import com.jen.easy.http.imp.HttpBasicListener;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.http.MD5Util;
import com.jen.easytest.http.request.AirRequest;
import com.jen.easytest.http.request.PutRequest;
import com.jen.easytest.http.request.QNRequest;
import com.jen.easytest.http.request.StockChooseConditionResultRequest;
import com.jen.easytest.request.OrderConditionSaveRequest;
import com.jen.easytest.request.OrderRuleModel;
import com.jen.easyui.EasyMain;
import com.jen.easyui.base.EasyActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        EasyMain.mHttp.setHttpBaseListener(httpListener);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void loadDataAfterView() {

    }

    @EasyViewMethod({R.id.get, R.id.post, R.id.put, R.id.upload, R.id.download})
    protected void onBindClick(View view) {
        switch (view.getId()) {
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
            case R.id.HttpReflectManager_getRequestParams: {
                AirRequest airRequest = new AirRequest();
                HttpTestUtil.HttpReflectManager_getRequestParams(airRequest);
                break;
            }

        }
    }

    private void get() {
        /*TaskProgressListRequest taskProgressListRequest = new TaskProgressListRequest();
        taskProgressListRequest.setTaskId(353631);
        taskProgressListRequest.setLimit(3);
        taskProgressListRequest.setPage(0);
        http.setHttpBaseListener(httpListener);
        http.start(taskProgressListRequest);*/

        QNRequest qnRequest = new QNRequest();
        EasyMain.mHttp.start(qnRequest);
    }

    private void post() {
        String signKey = "zteNTHVcBQ[UDpVoF^4";//用户名+密码
        String cid = "39189";//测试公司编号
        String signType = "MD5";

        String fromCity = "SZX";
        String fromCityName = "深圳";
        String arriveCity = "PEK";
        String arriveCityName = "北京";
        String carrier = "";
        String codeLevel = "";
        String goDate = "2017-12-20";
        String level = "";
        String backDate = "";
        String userCode = "30999901";


        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = smdf.format(new Date());
        String signString = cid + fromCity + fromCityName + arriveCity + arriveCityName + carrier + codeLevel + goDate + backDate + userCode + signType
                + signKey + nowDate;
        EasyLog.d("signString=" + signString);
        String sign = MD5Util.encrypt(signString);
//        sign = "6D715C3DACEC16D640852DAC6272FB6B";

        AirRequest airRequest = new AirRequest();
        airRequest.setCid(cid);
        airRequest.setFromCity(fromCity);
        airRequest.setFromCityName(fromCityName);
        airRequest.setArriveCity(arriveCity);
        airRequest.setArriveCityName(arriveCityName);
        airRequest.setCarrier(carrier);
        airRequest.setCodeLevel(codeLevel);
        airRequest.setGoDate(goDate);
        airRequest.setBackDate(backDate);
        airRequest.setLevel(level);
        airRequest.setUserCode(userCode);
        airRequest.setSignType(signType);
        airRequest.setSign(sign);

        StockChooseConditionResultRequest stockChooseConditionResultRequest = new StockChooseConditionResultRequest();

//        EasyMain.mHttp.start(airRequest);
//        EasyMain.mHttp.start(stockChooseConditionResultRequest);
        EasyLog.d("mAirResponse mAirResponse:");


        OrderConditionSaveRequest request = new OrderConditionSaveRequest();
        OrderRuleModel model1 = new OrderRuleModel();
        model1.setCycleUp("100");
        OrderRuleModel model2 = new OrderRuleModel();
        model2.setCycleUp("200");
        OrderRuleModel model3 = new OrderRuleModel();
        model3.setCycleUp("300");
        OrderRuleModel model4 = new OrderRuleModel();

        request.params.add(model1);
        request.params.add(model2);
        request.params.add(model3);
        request.params.add(model4);
        EasyMain.mHttp.start(request);
    }

    private void put() {
        PutRequest putRequest = new PutRequest();
        putRequest.setToken("16643-bdf0737cafb1c1a32451a0d93692edba");

        putRequest.setId("16643");
        EasyMain.mHttp.start(putRequest);
    }

    private void download() {

    }

    /**
     * 宝库数据返回
     */
    HttpBasicListener httpListener = new HttpBasicListener() {
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
