package com.jen.easytest.request;

import com.jen.easy.invalid.EasyInvalid;
import com.jen.easy.invalid.EasyInvalidType;

import java.io.Serializable;

public class OrderRuleModel implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 股票代码
     */
    private String stockCode;
    /**
     * 股票名称
     */
    private String stockName;
    /**
     * 股票名称
     */
    private Double stockPrice;
    /**
     * 市场
     */
    private String mktCode;
    /**
     * 0 已撤单 1 待执行 2 执行中 3 已结束
     */
    private Integer executeStatue;


    /**
     * 跌幅下
     */
    private Double dropRangeDown;
    /**
     * 跌幅上
     */
    private Double dropRangeUp;
    /**
     * 涨幅下
     */
    private Double rangeDown;
    /**
     * 涨幅上
     */
    private Double rangeUp;


    /**
     * 价格下（止盈下)）
     */
    private Double priceDown;
    /**
     * 价格上（止盈上)）
     */
    private Double priceUp;
    /**
     * 价格止损下
     */
    private Double dropPriceDown;
    /**
     * 价格止损 上
     */
    private Double dropPriceUp;


    /**
     * 周期下
     */
    private String cycleDown;
    /**
     * 周期上
     */
    private String cycleUp;//固定今天
    /**
     * 交易次数 0 不限次数 1 一次 2 2次 3 3次 4 4次
     */
    private Integer tradeNum;
    /**
     * 交易方向 1 买入 2 卖出 3 风险
     */
    private Integer tradeManagement;
    /**
     * 风险类型 1 设置风险管理 2 风险通知
     */
    private Integer riskType;
    /**
     * 买卖仓位
     */
    private Long position;
    /**
     * 仓位类型 0 全仓 1 半仓 2 1/3仓 3 1/4仓
     */
//    private Integer positionType;//已经取消，不传
    /**
     * 剩余次数 0 限制下单 1 一次 2 2次 3 3次 4 4次
     */
    private Integer surplusNum;


    @EasyInvalid(value = EasyInvalidType.Request)
    private int id;
    @EasyInvalid(value = EasyInvalidType.Request)
    private int positionType;
    @EasyInvalid(value = EasyInvalidType.Request)
    private int createTime;
    @EasyInvalid(value = EasyInvalidType.Request)
    private int updateTime;
    @EasyInvalid(value = EasyInvalidType.Request)
    private String createTimeStr;
    @EasyInvalid(value = EasyInvalidType.Request)
    private String updateTimeStr;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStockCode() {
        return stockCode == null ? "" : stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName == null ? "" : stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getMktCode() {
        return mktCode == null ? "" : mktCode;
    }

    public void setMktCode(String mktCode) {
        this.mktCode = mktCode;
    }

    public Integer getExecuteStatue() {
        return executeStatue;
    }

    public void setExecuteStatue(Integer executeStatue) {
        this.executeStatue = executeStatue;
    }

    public Double getDropRangeDown() {
        return dropRangeDown;
    }

    public void setDropRangeDown(Double dropRangeDown) {
        this.dropRangeDown = dropRangeDown;
    }

    public Double getDropRangeUp() {
        return dropRangeUp;
    }

    public void setDropRangeUp(Double dropRangeUp) {
        this.dropRangeUp = dropRangeUp;
    }

    public Double getRangeDown() {
        return rangeDown;
    }

    public void setRangeDown(Double rangeDown) {
        this.rangeDown = rangeDown;
    }

    public Double getRangeUp() {
        return rangeUp;
    }

    public void setRangeUp(Double rangeUp) {
        this.rangeUp = rangeUp;
    }

    public Double getPriceDown() {
        return priceDown;
    }

    public void setPriceDown(Double priceDown) {
        this.priceDown = priceDown;
    }

    public Double getPriceUp() {
        return priceUp;
    }

    public void setPriceUp(Double priceUp) {
        this.priceUp = priceUp;
    }

    public Double getDropPriceDown() {
        return dropPriceDown;
    }

    public void setDropPriceDown(Double dropPriceDown) {
        this.dropPriceDown = dropPriceDown;
    }

    public Double getDropPriceUp() {
        return dropPriceUp;
    }

    public void setDropPriceUp(Double dropPriceUp) {
        this.dropPriceUp = dropPriceUp;
    }

    public String getCycleDown() {
        return cycleDown == null ? "" : cycleDown;
    }

    public void setCycleDown(String cycleDown) {
        this.cycleDown = cycleDown;
    }

    public String getCycleUp() {
        return cycleUp == null ? "" : cycleUp;
    }

    public void setCycleUp(String cycleUp) {
        this.cycleUp = cycleUp;
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }

    public Integer getTradeManagement() {
        return tradeManagement;
    }

    public void setTradeManagement(Integer tradeManagement) {
        this.tradeManagement = tradeManagement;
    }

    public Integer getRiskType() {
        return riskType;
    }

    public void setRiskType(Integer riskType) {
        this.riskType = riskType;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

//    public Integer getPositionType() {
//        return positionType;
//    }
//
//    public void setPositionType(Integer positionType) {
//        this.positionType = positionType;
//    }

    public Integer getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionType() {
        return positionType;
    }

    public void setPositionType(int positionType) {
        this.positionType = positionType;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr == null ? "" : createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr == null ? "" : updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
