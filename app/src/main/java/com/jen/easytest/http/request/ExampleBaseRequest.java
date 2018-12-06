package com.jen.easytest.http.request;

import com.jen.easy.EasyRequest;
import com.jen.easy.EasyRequestType;
import com.jen.easy.http.HttpBasicRequest;
import com.jen.easy.invalid.EasyInvalid;
import com.jen.easy.invalid.EasyInvalidType;

import java.util.ArrayList;
import java.util.List;

//@EasyRequestInvalid
@EasyInvalid
public class ExampleBaseRequest extends HttpBasicRequest {

    @EasyRequest("_id")//请求参数为_id,值为当前id变量值
    private int id;

    private String name;//不注释默认作为参数请求,请求参数名与变量名一致，也就是name

    @EasyInvalid({EasyInvalidType.Response, EasyInvalidType.Request})
    private int age;

    @EasyRequest(value = "_type", type = EasyRequestType.Head)//注释isHeadReq = true，则作为head参数请求
    private String type;


    public ExampleBaseRequest() {
        tests.add("list1");
        tests.add("list2");
        tests.add("list3");
    }

    private String cid;// 商户公司编号

    private String fromCity;// 起飞城市代码

    String fromCityName;// 起飞城市名称

    List<String> tests = new ArrayList<>();// 起飞城市名称

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
