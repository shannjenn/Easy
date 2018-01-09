package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMain;
import com.jen.easy.EasyMouse;
import com.jen.easy.http.imp.HttpBaseListener;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.base.BaseActivity;
import com.jen.easytest.http.AirRequest;
import com.jen.easytest.http.AirResponse;
import com.jen.easytest.http.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class HttpActivity extends BaseActivity {

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

    @EasyMouse.BIND.Method({R.id.base, R.id.upload, R.id.download})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.base:
                base();
                break;
            case R.id.upload:

                break;
            case R.id.download:
                download();
                break;

        }
    }

    private void base() {
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

        airRequest.setBseListener(airRequestListener);
        EasyMain.mHttp.start(airRequest);
        EasyLog.d("mAirResponse mAirResponse:");
    }

    private void download(){

    }

    /**
     * 宝库数据返回
     */
    HttpBaseListener<AirResponse> airRequestListener = new HttpBaseListener<AirResponse>() {
        @Override
        public void fail(int flagCode, String flag, String msg) {
            EasyLog.d("airRequestListener fail:" + msg);
        }

        @Override
        public void success(int flagCode, String flag, AirResponse airResponse) {
            EasyLog.d("airRequestListener success");
        }
    };

    @Override
    public void httpSuccess(int flagCode, String flag, Object response) {

    }

    @Override
    public void httpFail(int flagCode, String flag, String msg) {

    }
}
