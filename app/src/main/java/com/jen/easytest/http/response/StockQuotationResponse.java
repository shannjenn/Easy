package com.jen.easytest.http.response;


import com.jen.easytest.sqlite.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取实时行情/股票实时价格
 */
public class StockQuotationResponse extends BaseResponse {

    private Result result;


    public static class Result {
        private List<List<String>> data;
        private List<List<Integer>> data1;
        private List<List<Integer>> data2;
        private List<Student> data3;
//        private List<Student> data3;

    }

    public Result getResult() {
        if (result == null) {
            result = new Result();
        }
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
