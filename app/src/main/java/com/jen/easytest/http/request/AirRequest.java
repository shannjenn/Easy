package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easytest.http.response.AirResponse;

@Easy.HTTP.POST(URLBASE = "http://apics.baoku.com/api/air/query", Response = AirResponse.class)
public class AirRequest extends ExampleBaseRequest {

	@Easy.HTTP.RequestParam("cid")
	private String cid;// 商户公司编号

	@Easy.HTTP.RequestParam("fromCity")
	private String fromCity;// 起飞城市代码

	@Easy.HTTP.RequestParam("fromCityName")
	private String fromCityName;// 起飞城市名称

	@Easy.HTTP.RequestParam("arriveCity")
	String arriveCity;// 到达城市代码

	@Easy.HTTP.RequestParam("arriveCityName")
	String arriveCityName;// 到达城市名称

	@Easy.HTTP.RequestParam("carrier")
	String carrier;// 航空公司 (可选)

	@Easy.HTTP.RequestParam("codeLevel")
	String codeLevel;// 仓位级别 (可选)

	@Easy.HTTP.RequestParam("goDate")
	String goDate;// 去程旅行日期

	@Easy.HTTP.RequestParam("backDate")
	String backDate;// 返程旅行日期

	@Easy.HTTP.RequestParam("level")
	String level;// 用户级别 (可选)

	@Easy.HTTP.RequestParam("userCode")
	String userCode;// 用来唯一标识用户

	@Easy.HTTP.RequestParam("signType")
	String signType;// 签名方式

	@Easy.HTTP.RequestParam("sign")
	String sign;// 签名


	@Override
	public String getCid() {
		return cid;
	}

	@Override
	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String getFromCity() {
		return fromCity;
	}

	@Override
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	@Override
	public String getFromCityName() {
		return fromCityName;
	}

	@Override
	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public String getArriveCityName() {
		return arriveCityName;
	}

	public void setArriveCityName(String arriveCityName) {
		this.arriveCityName = arriveCityName;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}

	public String getGoDate() {
		return goDate;
	}

	public void setGoDate(String goDate) {
		this.goDate = goDate;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
