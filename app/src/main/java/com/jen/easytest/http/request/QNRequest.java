package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpGet;
import com.jen.easytest.http.response.AirResponse;

/*获取去哪儿票源的退改签说明接口（新增）*/
@EasyHttpGet(UrlBase = "http://mdmtest.zte.com.cn:8888/etrip/SystemFacade/AndroidService/AirticketBookHandle.ashx", Response = AirResponse.class)
public class QNRequest extends ExampleBaseRequest {

    public QNRequest() {
        CommandName = "GETRULES";
        charset = "gb2312";//该接口不支持utf-8
        Token = "";
        LANG = "2052";
    }

    public String DEPARTAIRPORT = "SZX";// 出发机场三字码 示例：PVG
    public String LANDAIRPORT = "SYX";// 降落机场三字码 示例：SZX
    public String DEPARTDATE = "2018-09-23";// 日期，格式[2018-08-30]
    public String CABINCODE = "P";// 机舱号（Y：经济舱，C：商务舱，F：头等舱）
    public String FLIGHTNO = "HU7750";// 航线（航班号） 示例：CZ3831

    public String CommandName;
    public String Token;
    public String LANG;
}
