package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.http.AirRequest;
import com.jen.easytest.http.MD5Util;
import com.jen.easytest.http.PutRequest;
import com.jen.easyui.EasyMain;
import com.jen.easyui.base.EasyActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class HttpActivity extends EasyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
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

    @EasyMouse.BIND.Method({R.id.post, R.id.put, R.id.upload, R.id.download})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                post();
                break;
            case R.id.put:
                put();
                break;
            case R.id.upload:

                break;
            case R.id.download:
                download();
                break;

        }
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

        airRequest.setBseListener(httpListener);
        EasyMain.mHttp.start(airRequest);
        EasyLog.d("mAirResponse mAirResponse:");
    }

    private void put() {
        PutRequest putRequest = new PutRequest();
        putRequest.setToken("16643-bdf0737cafb1c1a32451a0d93692edba");

        putRequest.httpParam.urlAppend = "/16643";
        putRequest.setBseListener(httpListener);
        EasyMain.mHttp.start(putRequest);
    }

    private void download() {

    }

    /**
     * 宝库数据返回
     */
    HttpBaseListener httpListener = new HttpBaseListener() {
        @Override
        public void fail(int flagCode, String flag, String msg) {
            EasyLog.d("airRequestListener fail:" + msg);
        }

        @Override
        public void success(int flagCode, String flag, Object airResponse) {
            EasyLog.d("airRequestListener success");
        }
    };

    @Override
    public void success(int flagCode, String flag, Object response) {

    }

    @Override
    public void fail(int flagCode, String flag, String msg) {

    }
}
