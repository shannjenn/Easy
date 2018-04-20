package com.jen.easytest.http.request;

import com.jen.easy.Easy;
import com.jen.easytest.http.response.ExampleResponse;

@Easy.HTTP.POST(URL = "http://apics.baoku.com/api/air/query", Response = ExampleResponse.class)
public class ExampleRequest extends ExampleBaseRequest {

    @Easy.HTTP.RequestParam("book_id")//请求参数为book_id,值为当前bookId变量值
    private int bookId;

    private String bookName;//不注释默认作为参数请求,请求参数名与变量名一致，也就是bookName

    @Easy.HTTP.RequestParam(noReq = true)//注释noReq = true，则不作为参数请求
    private boolean isCheck;

    @Easy.HTTP.RequestParam(value = "book_code", type = Easy.HTTP.TYPE.HEAD)//注释isHeadReq = true，则作为head参数请求
    private String bookCode;




//	@Easy.HTTP.RequestParam(noReq = true)
//	String cid;// 商户公司编号

//	@Easy.HTTP.RequestParam(noReq = true)
//	String fromCity;// 起飞城市代码

//	@Easy.HTTP.RequestParam(noReq = true)
//	String fromCityName;// 起飞城市名称

//	@Easy.HTTP.RequestParam("arriveCity")
	String arriveCity;// 到达城市代码

//	@Easy.HTTP.RequestParam("arriveCityName")
	String arriveCityName;// 到达城市名称

	@Easy.HTTP.RequestParam(noReq = true)
	String carrier;// 航空公司 (可选)

	@Easy.HTTP.RequestParam(noReq = true)
	String codeLevel;// 仓位级别 (可选)

	@Easy.HTTP.RequestParam("goDate")
	String goDate;// 去程旅行日期

	@Easy.HTTP.RequestParam("backDate")
	String backDate;// 返程旅行日期

//	@Easy.HTTP.RequestParam("level")
	String level;// 用户级别 (可选)

	@Easy.HTTP.RequestParam("userCode")
	String userCode;// 用来唯一标识用户

	@Easy.HTTP.RequestParam("signType")
	String signType;// 签名方式

	@Easy.HTTP.RequestParam("sign")
	String sign;// 签名

	/*******************************************************************************************************
	 * ★★★ 下面是getter、settter ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	 ********************************************************************************************************/


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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }
}
