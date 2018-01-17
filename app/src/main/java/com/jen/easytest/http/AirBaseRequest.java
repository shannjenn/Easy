package com.jen.easytest.http;

import com.jen.easy.http.HttpBaseRequest;

public class AirBaseRequest extends HttpBaseRequest {

    private String cid;// 商户公司编号

    private String fromCity;// 起飞城市代码

    String fromCityName;// 起飞城市名称

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }
}
