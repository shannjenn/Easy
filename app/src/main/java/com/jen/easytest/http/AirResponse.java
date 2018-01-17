package com.jen.easytest.http;


import com.jen.easy.EasyMouse;

import java.util.List;

public class AirResponse extends AirBaseResponse {

    @EasyMouse.HTTP.ResponseParam(isHeadRsp = true)
    private String Date;

    @EasyMouse.HTTP.ResponseParam("token")
    private String token;

    @EasyMouse.HTTP.ResponseParam("code")
    private String code;

    @EasyMouse.HTTP.ResponseParam("airFreeConsign")
    private List<AirFreeConsign> airFreeConsign;

//    @EasyMouse.HTTP.ResponseParam("planeStyles")
//    private List<PlaneStyles> planeStyles;

    @EasyMouse.HTTP.ResponseParam("airFlights")
    private List<AirFlight> airFlights;


    public static class AirFreeConsign {

        @EasyMouse.HTTP.ResponseParam("id")
        private int id;

        @EasyMouse.HTTP.ResponseParam("classLevel")
        private String classLevel;

        @EasyMouse.HTTP.ResponseParam("consign")
        private String consign;

        @EasyMouse.HTTP.ResponseParam("remark")
        private String remark;

        @EasyMouse.HTTP.ResponseParam("applyCarrier")
        private String applyCarrier;

        @EasyMouse.HTTP.ResponseParam("updateTime")
        private String updateTime;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClassLevel() {
            return classLevel;
        }

        public void setClassLevel(String classLevel) {
            this.classLevel = classLevel;
        }

        public String getConsign() {
            return consign;
        }

        public void setConsign(String consign) {
            this.consign = consign;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getApplyCarrier() {
            return applyCarrier;
        }

        public void setApplyCarrier(String applyCarrier) {
            this.applyCarrier = applyCarrier;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class AirFlight {

        @EasyMouse.HTTP.ResponseParam("airCraft")
        private String airCraft;

        @EasyMouse.HTTP.ResponseParam("airCraftDes")
        private String airCraftDes;

        @EasyMouse.HTTP.ResponseParam("airportTax")
        private float airportTax;

        @EasyMouse.HTTP.ResponseParam("arrivalDate")
        private String arrivalDate;

        @EasyMouse.HTTP.ResponseParam("arrivalTime")
        private String arrivalTime;

        @EasyMouse.HTTP.ResponseParam("boardCityCode")
        private String boardCityCode;

        @EasyMouse.HTTP.ResponseParam("boardCityName")
        private String boardCityName;

        @EasyMouse.HTTP.ResponseParam("boardPoint")
        private String boardPoint;

        @EasyMouse.HTTP.ResponseParam("boardPointAT")
        private String boardPointAT;

        @EasyMouse.HTTP.ResponseParam("boardPointName")
        private String boardPointName;

        @EasyMouse.HTTP.ResponseParam("carrierCode")
        private String carrierCode;

        @EasyMouse.HTTP.ResponseParam("carrierName")
        private String carrierName;

        @EasyMouse.HTTP.ResponseParam("carrierOrg")
        private String carrierOrg;

        @EasyMouse.HTTP.ResponseParam("departureDate")
        private String departureDate;

        @EasyMouse.HTTP.ResponseParam("departureTime")
        private String departureTime;

        @EasyMouse.HTTP.ResponseParam("flightNo")
        private String flightNo;

        @EasyMouse.HTTP.ResponseParam("flightTime")
        private String flightTime;

        @EasyMouse.HTTP.ResponseParam("flightType")
        private String flightType;

        @EasyMouse.HTTP.ResponseParam("fuelSurTax")
        private float fuelSurTax;

        @EasyMouse.HTTP.ResponseParam("meal")
        private String meal;

        @EasyMouse.HTTP.ResponseParam("offCityCode")
        private String offCityCode;

        @EasyMouse.HTTP.ResponseParam("offCityName")
        private String offCityName;

        @EasyMouse.HTTP.ResponseParam("offPoint")
        private String offPoint;

        @EasyMouse.HTTP.ResponseParam("offPointAT")
        private String offPointAT;

        @EasyMouse.HTTP.ResponseParam("offPointName")
        private String offPointName;

        @EasyMouse.HTTP.ResponseParam("otherTax")
        private float otherTax;

        @EasyMouse.HTTP.ResponseParam("sequence")
        private String sequence;

        @EasyMouse.HTTP.ResponseParam("shortCarrName")
        private String shortCarrName;

        @EasyMouse.HTTP.ResponseParam("signature")
        private String signature;

        @EasyMouse.HTTP.ResponseParam("tpm")
        private String tpm;

        @EasyMouse.HTTP.ResponseParam("viaPoint")
        private String viaPoint;

        @EasyMouse.HTTP.ResponseParam("cclassPrice")
        private float cclassPrice;

        @EasyMouse.HTTP.ResponseParam("fclassPrice")
        private float fclassPrice;

        @EasyMouse.HTTP.ResponseParam("link")
        private String link;

        @EasyMouse.HTTP.ResponseParam("unit")
        private int unit;

        @EasyMouse.HTTP.ResponseParam("yclassPrice")
        private float yclassPrice;

        @EasyMouse.HTTP.ResponseParam("zhongZhuan")
        private boolean zhongZhuan;

        @EasyMouse.HTTP.ResponseParam("airCabins")
        private List<AirCabin> airCabins;

        @EasyMouse.HTTP.ResponseParam("lowCabin")
        private AirCabin lowCabin;


        public static class AirCabin {

            @EasyMouse.HTTP.ResponseParam("agio")
            private int agio;

            @EasyMouse.HTTP.ResponseParam("airlieOffPrice")
            private float airlieOffPrice;

            @EasyMouse.HTTP.ResponseParam("airlineSavePrice")
            private float airlineSavePrice;

            @EasyMouse.HTTP.ResponseParam("carrierAgentFee")
            private float carrierAgentFee;

            @EasyMouse.HTTP.ResponseParam("carrierCustomerFee")
            private float carrierCustomerFee;

            @EasyMouse.HTTP.ResponseParam("channel")
            private String channel;

            @EasyMouse.HTTP.ResponseParam("code")
            private String code;

            @EasyMouse.HTTP.ResponseParam("codeDesc")
            private String codeDesc;

            @EasyMouse.HTTP.ResponseParam("discount")
            private float discount;

            @EasyMouse.HTTP.ResponseParam("extCode")
            private String extCode;

            @EasyMouse.HTTP.ResponseParam("fare")
            private float fare;

            @EasyMouse.HTTP.ResponseParam("farebase")
            private String farebase;

            @EasyMouse.HTTP.ResponseParam("fdPrice")
            private float fdPrice;

            @EasyMouse.HTTP.ResponseParam("originalprice")
            private float originalprice;

            @EasyMouse.HTTP.ResponseParam("raisePrice")
            private float raisePrice;

            @EasyMouse.HTTP.ResponseParam("remark")
            private String remark;

            @EasyMouse.HTTP.ResponseParam("saleRange")
            private String saleRange;

            @EasyMouse.HTTP.ResponseParam("saveprice")
            private float saveprice;

            @EasyMouse.HTTP.ResponseParam("seatNum")
            private int seatNum;

            @EasyMouse.HTTP.ResponseParam("showSKpolicy")
            private boolean showSKpolicy;

            @EasyMouse.HTTP.ResponseParam("signature")
            private String signature;


            public int getAgio() {
                return agio;
            }

            public void setAgio(int agio) {
                this.agio = agio;
            }

            public float getAirlieOffPrice() {
                return airlieOffPrice;
            }

            public void setAirlieOffPrice(float airlieOffPrice) {
                this.airlieOffPrice = airlieOffPrice;
            }

            public float getAirlineSavePrice() {
                return airlineSavePrice;
            }

            public void setAirlineSavePrice(float airlineSavePrice) {
                this.airlineSavePrice = airlineSavePrice;
            }

            public float getCarrierAgentFee() {
                return carrierAgentFee;
            }

            public void setCarrierAgentFee(float carrierAgentFee) {
                this.carrierAgentFee = carrierAgentFee;
            }

            public float getCarrierCustomerFee() {
                return carrierCustomerFee;
            }

            public void setCarrierCustomerFee(float carrierCustomerFee) {
                this.carrierCustomerFee = carrierCustomerFee;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCodeDesc() {
                return codeDesc;
            }

            public void setCodeDesc(String codeDesc) {
                this.codeDesc = codeDesc;
            }

            public float getDiscount() {
                return discount;
            }

            public void setDiscount(float discount) {
                this.discount = discount;
            }

            public String getExtCode() {
                return extCode;
            }

            public void setExtCode(String extCode) {
                this.extCode = extCode;
            }

            public float getFare() {
                return fare;
            }

            public void setFare(float fare) {
                this.fare = fare;
            }

            public String getFarebase() {
                return farebase;
            }

            public void setFarebase(String farebase) {
                this.farebase = farebase;
            }

            public float getFdPrice() {
                return fdPrice;
            }

            public void setFdPrice(float fdPrice) {
                this.fdPrice = fdPrice;
            }

            public float getOriginalprice() {
                return originalprice;
            }

            public void setOriginalprice(float originalprice) {
                this.originalprice = originalprice;
            }

            public float getRaisePrice() {
                return raisePrice;
            }

            public void setRaisePrice(float raisePrice) {
                this.raisePrice = raisePrice;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSaleRange() {
                return saleRange;
            }

            public void setSaleRange(String saleRange) {
                this.saleRange = saleRange;
            }

            public float getSaveprice() {
                return saveprice;
            }

            public void setSaveprice(float saveprice) {
                this.saveprice = saveprice;
            }

            public int getSeatNum() {
                return seatNum;
            }

            public void setSeatNum(int seatNum) {
                this.seatNum = seatNum;
            }

            public boolean isShowSKpolicy() {
                return showSKpolicy;
            }

            public void setShowSKpolicy(boolean showSKpolicy) {
                this.showSKpolicy = showSKpolicy;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }

        public String getAirCraft() {
            return airCraft;
        }

        public void setAirCraft(String airCraft) {
            this.airCraft = airCraft;
        }

        public String getAirCraftDes() {
            return airCraftDes;
        }

        public void setAirCraftDes(String airCraftDes) {
            this.airCraftDes = airCraftDes;
        }

        public float getAirportTax() {
            return airportTax;
        }

        public void setAirportTax(float airportTax) {
            this.airportTax = airportTax;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public String getBoardCityCode() {
            return boardCityCode;
        }

        public void setBoardCityCode(String boardCityCode) {
            this.boardCityCode = boardCityCode;
        }

        public String getBoardCityName() {
            return boardCityName;
        }

        public void setBoardCityName(String boardCityName) {
            this.boardCityName = boardCityName;
        }

        public String getBoardPoint() {
            return boardPoint;
        }

        public void setBoardPoint(String boardPoint) {
            this.boardPoint = boardPoint;
        }

        public String getBoardPointAT() {
            return boardPointAT;
        }

        public void setBoardPointAT(String boardPointAT) {
            this.boardPointAT = boardPointAT;
        }

        public String getBoardPointName() {
            return boardPointName;
        }

        public void setBoardPointName(String boardPointName) {
            this.boardPointName = boardPointName;
        }

        public String getCarrierCode() {
            return carrierCode;
        }

        public void setCarrierCode(String carrierCode) {
            this.carrierCode = carrierCode;
        }

        public String getCarrierName() {
            return carrierName;
        }

        public void setCarrierName(String carrierName) {
            this.carrierName = carrierName;
        }

        public String getCarrierOrg() {
            return carrierOrg;
        }

        public void setCarrierOrg(String carrierOrg) {
            this.carrierOrg = carrierOrg;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public String getFlightTime() {
            return flightTime;
        }

        public void setFlightTime(String flightTime) {
            this.flightTime = flightTime;
        }

        public String getFlightType() {
            return flightType;
        }

        public void setFlightType(String flightType) {
            this.flightType = flightType;
        }

        public float getFuelSurTax() {
            return fuelSurTax;
        }

        public void setFuelSurTax(float fuelSurTax) {
            this.fuelSurTax = fuelSurTax;
        }

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getOffCityCode() {
            return offCityCode;
        }

        public void setOffCityCode(String offCityCode) {
            this.offCityCode = offCityCode;
        }

        public String getOffCityName() {
            return offCityName;
        }

        public void setOffCityName(String offCityName) {
            this.offCityName = offCityName;
        }

        public String getOffPoint() {
            return offPoint;
        }

        public void setOffPoint(String offPoint) {
            this.offPoint = offPoint;
        }

        public String getOffPointAT() {
            return offPointAT;
        }

        public void setOffPointAT(String offPointAT) {
            this.offPointAT = offPointAT;
        }

        public String getOffPointName() {
            return offPointName;
        }

        public void setOffPointName(String offPointName) {
            this.offPointName = offPointName;
        }

        public float getOtherTax() {
            return otherTax;
        }

        public void setOtherTax(float otherTax) {
            this.otherTax = otherTax;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getShortCarrName() {
            return shortCarrName;
        }

        public void setShortCarrName(String shortCarrName) {
            this.shortCarrName = shortCarrName;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getTpm() {
            return tpm;
        }

        public void setTpm(String tpm) {
            this.tpm = tpm;
        }

        public String getViaPoint() {
            return viaPoint;
        }

        public void setViaPoint(String viaPoint) {
            this.viaPoint = viaPoint;
        }

        public float getCclassPrice() {
            return cclassPrice;
        }

        public void setCclassPrice(float cclassPrice) {
            this.cclassPrice = cclassPrice;
        }

        public float getFclassPrice() {
            return fclassPrice;
        }

        public void setFclassPrice(float fclassPrice) {
            this.fclassPrice = fclassPrice;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public float getYclassPrice() {
            return yclassPrice;
        }

        public void setYclassPrice(float yclassPrice) {
            this.yclassPrice = yclassPrice;
        }

        public boolean isZhongZhuan() {
            return zhongZhuan;
        }

        public void setZhongZhuan(boolean zhongZhuan) {
            this.zhongZhuan = zhongZhuan;
        }

        public List<AirCabin> getAirCabins() {
            return airCabins;
        }

        public void setAirCabins(List<AirCabin> airCabins) {
            this.airCabins = airCabins;
        }

        public AirCabin getLowCabin() {
            return lowCabin;
        }

        public void setLowCabin(AirCabin lowCabin) {
            this.lowCabin = lowCabin;
        }
    }


//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AirFreeConsign> getAirFreeConsign() {
        return airFreeConsign;
    }

    public void setAirFreeConsign(List<AirFreeConsign> airFreeConsign) {
        this.airFreeConsign = airFreeConsign;
    }

    public List<AirFlight> getAirFlights() {
        return airFlights;
    }

    public void setAirFlights(List<AirFlight> airFlights) {
        this.airFlights = airFlights;
    }
}
