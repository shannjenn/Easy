package com.jen.easytest.http.response;


import java.util.ArrayList;
import java.util.List;

/**
 * 获取实时行情/股票实时价格
 */
public class StockQuotationResponse extends BaseResponse {

    private Result result;


    public static class Result {
        private List<List<String>> data;

        public List<List<String>> getData() {
            if (data == null) {
                return new ArrayList<>();
            }
            return data;
        }

        public void setData(List<List<String>> data) {
            this.data = data;
        }
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
