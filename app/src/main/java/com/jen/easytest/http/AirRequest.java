package com.jen.easytest.http;

import com.jen.easy.EasyMouse;
import com.jen.easy.http.HttpBaseRequest;

@EasyMouse.HTTP.POST(URL = "http://apics.baoku.com/api/air/query", Response = AirResponse.class)
public class AirRequest extends HttpBaseRequest {

	@EasyMouse.HTTP.RequestParam("cid")
	String cid;// 商户公司编号

	@EasyMouse.HTTP.RequestParam("fromCity")
	String fromCity;// 起飞城市代码

	@EasyMouse.HTTP.RequestParam("fromCityName")
	String fromCityName;// 起飞城市名称

	@EasyMouse.HTTP.RequestParam("arriveCity")
	String arriveCity;// 到达城市代码

	@EasyMouse.HTTP.RequestParam("arriveCityName")
	String arriveCityName;// 到达城市名称

//	@EasyMouse.HTTP.RequestParam("carrier")
	String carrier;// 航空公司 (可选)

//	@EasyMouse.HTTP.RequestParam("codeLevel")
	String codeLevel;// 仓位级别 (可选)

	@EasyMouse.HTTP.RequestParam("goDate")
	String goDate;// 去程旅行日期

	@EasyMouse.HTTP.RequestParam("backDate")
	String backDate;// 返程旅行日期

//	@EasyMouse.HTTP.RequestParam("level")
	String level;// 用户级别 (可选)

	@EasyMouse.HTTP.RequestParam("userCode")
	String userCode;// 用来唯一标识用户

	@EasyMouse.HTTP.RequestParam("signType")
	String signType;// 签名方式

	@EasyMouse.HTTP.RequestParam("sign")
	String sign;// 签名

	/*******************************************************************************************************
	 * ★★★ 下面是getter、settter ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	 ********************************************************************************************************/

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
