package com.jen.easytest.http.response;


import com.jen.easy.Easy;
import com.jen.easytest.model.Book;

import java.util.List;

public class ExampleResponse extends ExampleBaseResponse {

    @Easy.HTTP.ResponseParam("book_code")
    private String bookCode;

    @Easy.HTTP.ResponseParam("books")
    private List<Book> books;



    @Easy.HTTP.ResponseParam(isHeadRsp = true)
    private String Date;

    @Easy.HTTP.ResponseParam("token")
    private String token;

    @Easy.HTTP.ResponseParam("code")
    private String code;

    @Easy.HTTP.ResponseParam("airFreeConsign")
    private List<AirFreeConsign> airFreeConsign;

//    @Easy.HTTP.ResponseParam("planeStyles")
//    private List<PlaneStyles> planeStyles;

    @Easy.HTTP.ResponseParam("airFlights")
    private List<AirFlight> airFlights;


    public static class AirFreeConsign {

        @Easy.HTTP.ResponseParam("id")
        private int id;

        @Easy.HTTP.ResponseParam("classLevel")
        private String classLevel;

        @Easy.HTTP.ResponseParam("consign")
        private String consign;

        @Easy.HTTP.ResponseParam("remark")
        private String remark;

        @Easy.HTTP.ResponseParam("applyCarrier")
        private String applyCarrier;

        @Easy.HTTP.ResponseParam("updateTime")
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

        @Easy.HTTP.ResponseParam("airCraft")
        private String airCraft;

        @Easy.HTTP.ResponseParam("airCraftDes")
        private String airCraftDes;

        @Easy.HTTP.ResponseParam("airportTax")
        private float airportTax;

        @Easy.HTTP.ResponseParam("arrivalDate")
        private String arrivalDate;

        @Easy.HTTP.ResponseParam("arrivalTime")
        private String arrivalTime;

        @Easy.HTTP.ResponseParam("boardCityCode")
        private String boardCityCode;

        @Easy.HTTP.ResponseParam("boardCityName")
        private String boardCityName;

        @Easy.HTTP.ResponseParam("boardPoint")
        private String boardPoint;

        @Easy.HTTP.ResponseParam("boardPointAT")
        private String boardPointAT;

        @Easy.HTTP.ResponseParam("boardPointName")
        private String boardPointName;

        @Easy.HTTP.ResponseParam("carrierCode")
        private String carrierCode;

        @Easy.HTTP.ResponseParam("carrierName")
        private String carrierName;

        @Easy.HTTP.ResponseParam("carrierOrg")
        private String carrierOrg;

        @Easy.HTTP.ResponseParam("departureDate")
        private String departureDate;

        @Easy.HTTP.ResponseParam("departureTime")
        private String departureTime;

        @Easy.HTTP.ResponseParam("flightNo")
        private String flightNo;

        @Easy.HTTP.ResponseParam("flightTime")
        private String flightTime;

        @Easy.HTTP.ResponseParam("flightType")
        private String flightType;

        @Easy.HTTP.ResponseParam("fuelSurTax")
        private float fuelSurTax;

        @Easy.HTTP.ResponseParam("meal")
        private String meal;

        @Easy.HTTP.ResponseParam("offCityCode")
        private String offCityCode;

        @Easy.HTTP.ResponseParam("offCityName")
        private String offCityName;

        @Easy.HTTP.ResponseParam("offPoint")
        private String offPoint;

        @Easy.HTTP.ResponseParam("offPointAT")
        private String offPointAT;

        @Easy.HTTP.ResponseParam("offPointName")
        private String offPointName;

        @Easy.HTTP.ResponseParam("otherTax")
        private float otherTax;

        @Easy.HTTP.ResponseParam("sequence")
        private String sequence;

        @Easy.HTTP.ResponseParam("shortCarrName")
        private String shortCarrName;

        @Easy.HTTP.ResponseParam("signature")
        private String signature;

        @Easy.HTTP.ResponseParam("tpm")
        private String tpm;

        @Easy.HTTP.ResponseParam("viaPoint")
        private String viaPoint;

        @Easy.HTTP.ResponseParam("cclassPrice")
        private float cclassPrice;

        @Easy.HTTP.ResponseParam("fclassPrice")
        private float fclassPrice;

        @Easy.HTTP.ResponseParam("link")
        private String link;

        @Easy.HTTP.ResponseParam("unit")
        private int unit;

        @Easy.HTTP.ResponseParam("yclassPrice")
        private float yclassPrice;

        @Easy.HTTP.ResponseParam("zhongZhuan")
        private boolean zhongZhuan;

        @Easy.HTTP.ResponseParam("airCabins")
        private List<AirCabin> airCabins;

        @Easy.HTTP.ResponseParam("lowCabin")
        private AirCabin lowCabin;


        public static class AirCabin {

            @Easy.HTTP.ResponseParam("agio")
            private int agio;

            @Easy.HTTP.ResponseParam("airlieOffPrice")
            private float airlieOffPrice;

            @Easy.HTTP.ResponseParam("airlineSavePrice")
            private float airlineSavePrice;

            @Easy.HTTP.ResponseParam("carrierAgentFee")
            private float carrierAgentFee;

            @Easy.HTTP.ResponseParam("carrierCustomerFee")
            private float carrierCustomerFee;

            @Easy.HTTP.ResponseParam("channel")
            private String channel;

            @Easy.HTTP.ResponseParam("code")
            private String code;

            @Easy.HTTP.ResponseParam("codeDesc")
            private String codeDesc;

            @Easy.HTTP.ResponseParam("discount")
            private float discount;

            @Easy.HTTP.ResponseParam("extCode")
            private String extCode;

            @Easy.HTTP.ResponseParam("fare")
            private float fare;

            @Easy.HTTP.ResponseParam("farebase")
            private String farebase;

            @Easy.HTTP.ResponseParam("fdPrice")
            private float fdPrice;

            @Easy.HTTP.ResponseParam("originalprice")
            private float originalprice;

            @Easy.HTTP.ResponseParam("raisePrice")
            private float raisePrice;

            @Easy.HTTP.ResponseParam("remark")
            private String remark;

            @Easy.HTTP.ResponseParam("saleRange")
            private String saleRange;

            @Easy.HTTP.ResponseParam("saveprice")
            private float saveprice;

            @Easy.HTTP.ResponseParam("seatNum")
            private int seatNum;

            @Easy.HTTP.ResponseParam("showSKpolicy")
            private boolean showSKpolicy;

            @Easy.HTTP.ResponseParam("signature")
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
