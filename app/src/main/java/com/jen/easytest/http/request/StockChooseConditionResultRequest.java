package com.jen.easytest.http.request;

import com.jen.easy.EasyHttpPost;
import com.jen.easy.http.request.EasyHttpDataRequest;

/**
 * 选股器筛选结果 请求
 *
 * @author Created by zs on 2018/10/22.
 */
@EasyHttpPost(UrlAppend = "http://192.168.10.250:9001/mktinfo_api/get_assentList")
public class StockChooseConditionResultRequest extends EasyHttpDataRequest {
    protected String HEAD_CONTENT_TYPE = "application/json";

    private Params params;

    public StockChooseConditionResultRequest() {
        params = new Params();
    }

    public static class Params {
        private int count = 50;// 拉取的条数，分页查询的话是每页的条数
        private String sortField;// 排序字段
        private String sortDir;// 排序方向
        private int page = 1;//当前页

        /**
         * 市场类型  ERegion.java枚举类 - 目前默认HK
         */
        private String marketType;
        /**
         * 总市值类型  默认-1无选择项  0 自定义范围 minMarketValue - maxMarketValue
         * 1 - ≤10亿 , 2 - 10亿-100亿 , 3 - 100亿-1000亿 , 4 - ≥1000亿
         */
        private float marketValueType = -1;
        private float minMarketValue;//最小市值(亿)
        private float maxMarketValue;//最大市值(亿)

        /**
         * 成交量  默认-1无选择项  0 自定义范围 mfloatotalVolume - maxTotalVolume
         */
        private float totalVolume = -1;
        private float mfloatotalVolume;//最小成交量(万)
        private float maxTotalVolume;//最大成交量(万)

        /**
         * 市净率 默认-1无选择项  0 自定义范围 minPriceToBook - maxPriceToBook
         * 1 - ≤1 , 2 - ≤2 , 3 - ≤5
         */
        private float priceToBook = -1;
        private float minPriceToBook;//最小市净率(%)
        private float maxPriceToBook;//最大市净率(%)

        /**
         * 市盈率 默认-1无选择项  0 自定义范围 minPriceEarnings - maxPriceEarnings
         * 1 - ≤5 , 2 - ≤10 , 3 - ≤15
         */
        private float priceEarnings = -1;
        private float minPriceEarnings;//最小市盈率(%)
        private float maxPriceEarnings;//最大市盈率(%)


        /**
         * 换手率 默认-1无选择项  0 自定义范围 mfloaturnoverRate - maxTurnoverRate
         */
        private float turnoverRate = -1;
        private float mfloaturnoverRate;//最小换手率(%)
        private float maxTurnoverRate;//最大换手率(%)

        /**
         * 股票价格 默认-1无选择项 0 自定义范围 minAssetPrice - maxAssetPrice
         */
        private float assetPrice = -1;
        private float minAssetPrice;//最小股票价格
        private float maxAssetPrice;//最大股票价格

        /**
         * 本日/七日涨跌幅 默认-1无选择项 0 自定义范围 minStkChgPct - maxStkChgPct
         */
        //本日涨跌幅
        private float todayStkChgPct = -1;
        private float mfloatodayStkChgPct;//最小涨跌幅(%)
        private float maxTodayStkChgPct;//最大涨跌幅(%)

        //七日涨跌幅
        private float sevenDaysStkChgPct = -1;
        private float minSevenDaysStkChgPct;//最小涨跌幅(%)
        private float maxSevenDaysStkChgPct;//最大涨跌幅(%)


        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getSortField() {
            return sortField == null ? "" : sortField;
        }

        public void setSortField(String sortField) {
            this.sortField = sortField;
        }

        public String getSortDir() {
            return sortDir == null ? "" : sortDir;
        }

        public void setSortDir(String sortDir) {
            this.sortDir = sortDir;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getMarketType() {
            return marketType == null ? "" : marketType;
        }

        public void setMarketType(String marketType) {
            this.marketType = marketType;
        }

        public float getMarketValueType() {
            return marketValueType;
        }

        public void setMarketValueType(float marketValueType) {
            this.marketValueType = marketValueType;
        }

        public float getMinMarketValue() {
            return minMarketValue;
        }

        public void setMinMarketValue(float minMarketValue) {
            this.minMarketValue = minMarketValue;
        }

        public float getMaxMarketValue() {
            return maxMarketValue;
        }

        public void setMaxMarketValue(float maxMarketValue) {
            this.maxMarketValue = maxMarketValue;
        }

        public float getTotalVolume() {
            return totalVolume;
        }

        public void setTotalVolume(float totalVolume) {
            this.totalVolume = totalVolume;
        }

        public float getMfloatotalVolume() {
            return mfloatotalVolume;
        }

        public void setMfloatotalVolume(float mfloatotalVolume) {
            this.mfloatotalVolume = mfloatotalVolume;
        }

        public float getMaxTotalVolume() {
            return maxTotalVolume;
        }

        public void setMaxTotalVolume(float maxTotalVolume) {
            this.maxTotalVolume = maxTotalVolume;
        }

        public float getPriceToBook() {
            return priceToBook;
        }

        public void setPriceToBook(float priceToBook) {
            this.priceToBook = priceToBook;
        }

        public float getMinPriceToBook() {
            return minPriceToBook;
        }

        public void setMinPriceToBook(float minPriceToBook) {
            this.minPriceToBook = minPriceToBook;
        }

        public float getMaxPriceToBook() {
            return maxPriceToBook;
        }

        public void setMaxPriceToBook(float maxPriceToBook) {
            this.maxPriceToBook = maxPriceToBook;
        }

        public float getPriceEarnings() {
            return priceEarnings;
        }

        public void setPriceEarnings(float priceEarnings) {
            this.priceEarnings = priceEarnings;
        }

        public float getMinPriceEarnings() {
            return minPriceEarnings;
        }

        public void setMinPriceEarnings(float minPriceEarnings) {
            this.minPriceEarnings = minPriceEarnings;
        }

        public float getMaxPriceEarnings() {
            return maxPriceEarnings;
        }

        public void setMaxPriceEarnings(float maxPriceEarnings) {
            this.maxPriceEarnings = maxPriceEarnings;
        }

        public float getTurnoverRate() {
            return turnoverRate;
        }

        public void setTurnoverRate(float turnoverRate) {
            this.turnoverRate = turnoverRate;
        }

        public float getMfloaturnoverRate() {
            return mfloaturnoverRate;
        }

        public void setMfloaturnoverRate(float mfloaturnoverRate) {
            this.mfloaturnoverRate = mfloaturnoverRate;
        }

        public float getMaxTurnoverRate() {
            return maxTurnoverRate;
        }

        public void setMaxTurnoverRate(float maxTurnoverRate) {
            this.maxTurnoverRate = maxTurnoverRate;
        }

        public float getAssetPrice() {
            return assetPrice;
        }

        public void setAssetPrice(float assetPrice) {
            this.assetPrice = assetPrice;
        }

        public float getMinAssetPrice() {
            return minAssetPrice;
        }

        public void setMinAssetPrice(float minAssetPrice) {
            this.minAssetPrice = minAssetPrice;
        }

        public float getMaxAssetPrice() {
            return maxAssetPrice;
        }

        public void setMaxAssetPrice(float maxAssetPrice) {
            this.maxAssetPrice = maxAssetPrice;
        }

        public float getTodayStkChgPct() {
            return todayStkChgPct;
        }

        public void setTodayStkChgPct(float todayStkChgPct) {
            this.todayStkChgPct = todayStkChgPct;
        }

        public float getMfloatodayStkChgPct() {
            return mfloatodayStkChgPct;
        }

        public void setMfloatodayStkChgPct(float mfloatodayStkChgPct) {
            this.mfloatodayStkChgPct = mfloatodayStkChgPct;
        }

        public float getMaxTodayStkChgPct() {
            return maxTodayStkChgPct;
        }

        public void setMaxTodayStkChgPct(float maxTodayStkChgPct) {
            this.maxTodayStkChgPct = maxTodayStkChgPct;
        }

        public float getSevenDaysStkChgPct() {
            return sevenDaysStkChgPct;
        }

        public void setSevenDaysStkChgPct(float sevenDaysStkChgPct) {
            this.sevenDaysStkChgPct = sevenDaysStkChgPct;
        }

        public float getMinSevenDaysStkChgPct() {
            return minSevenDaysStkChgPct;
        }

        public void setMinSevenDaysStkChgPct(float minSevenDaysStkChgPct) {
            this.minSevenDaysStkChgPct = minSevenDaysStkChgPct;
        }

        public float getMaxSevenDaysStkChgPct() {
            return maxSevenDaysStkChgPct;
        }

        public void setMaxSevenDaysStkChgPct(float maxSevenDaysStkChgPct) {
            this.maxSevenDaysStkChgPct = maxSevenDaysStkChgPct;
        }
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}