package com.jen.easytest.http.response;


import com.jen.easy.EasyResponse;
import com.jen.easytest.model.Book;

import java.util.List;

public class AirResponse extends ExampleBaseResponse {

    @EasyResponse("book_code")
    private String bookCode;

    @EasyResponse("books")
    private List<Book> books;



    private String Date;

    @EasyResponse("token")
    private String token;

    @EasyResponse("code")
    private String code;

    @EasyResponse("airFreeConsign")
    private List<AirFreeConsign> airFreeConsign;

//    @EasyResponse("planeStyles")
//    private List<PlaneStyles> planeStyles;

    @EasyResponse("airFlights")
    private List<AirFlight> airFlights;


    public static class AirFreeConsign {

        @EasyResponse("id")
        private int id;

        @EasyResponse("classLevel")
        private String classLevel;

        @EasyResponse("consign")
        private String consign;

        @EasyResponse("remark")
        private String remark;

        @EasyResponse("applyCarrier")
        private String applyCarrier;

        @EasyResponse("updateTime")
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

        @EasyResponse("airCraft")
        private String airCraft;

        @EasyResponse("airCraftDes")
        private String airCraftDes;

        @EasyResponse("airportTax")
        private float airportTax;

        @EasyResponse("arrivalDate")
        private String arrivalDate;

        @EasyResponse("arrivalTime")
        private String arrivalTime;

        @EasyResponse("boardCityCode")
        private String boardCityCode;

        @EasyResponse("boardCityName")
        private String boardCityName;

        @EasyResponse("boardPoint")
        private String boardPoint;

        @EasyResponse("boardPointAT")
        private String boardPointAT;

        @EasyResponse("boardPointName")
        private String boardPointName;

        @EasyResponse("carrierCode")
        private String carrierCode;

        @EasyResponse("carrierName")
        private String carrierName;

        @EasyResponse("carrierOrg")
        private String carrierOrg;

        @EasyResponse("departureDate")
        private String departureDate;

        @EasyResponse("departureTime")
        private String departureTime;

        @EasyResponse("flightNo")
        private String flightNo;

        @EasyResponse("flightTime")
        private String flightTime;

        @EasyResponse("flightType")
        private String flightType;

        @EasyResponse("fuelSurTax")
        private float fuelSurTax;

        @EasyResponse("meal")
        private String meal;

        @EasyResponse("offCityCode")
        private String offCityCode;

        @EasyResponse("offCityName")
        private String offCityName;

        @EasyResponse("offPoint")
        private String offPoint;

        @EasyResponse("offPointAT")
        private String offPointAT;

        @EasyResponse("offPointName")
        private String offPointName;

        @EasyResponse("otherTax")
        private float otherTax;

        @EasyResponse("sequence")
        private String sequence;

        @EasyResponse("shortCarrName")
        private String shortCarrName;

        @EasyResponse("signature")
        private String signature;

        @EasyResponse("tpm")
        private String tpm;

        @EasyResponse("viaPoint")
        private String viaPoint;

        @EasyResponse("cclassPrice")
        private float cclassPrice;

        @EasyResponse("fclassPrice")
        private float fclassPrice;

        @EasyResponse("link")
        private String link;

        @EasyResponse("unit")
        private int unit;

        @EasyResponse("yclassPrice")
        private float yclassPrice;

        @EasyResponse("zhongZhuan")
        private boolean zhongZhuan;

        @EasyResponse("airCabins")
        private List<AirCabin> airCabins;

        @EasyResponse("lowCabin")
        private AirCabin lowCabin;


        public static class AirCabin {

            @EasyResponse("agio")
            private int agio;

            @EasyResponse("airlieOffPrice")
            private float airlieOffPrice;

            @EasyResponse("airlineSavePrice")
            private float airlineSavePrice;

            @EasyResponse("carrierAgentFee")
            private float carrierAgentFee;

            @EasyResponse("carrierCustomerFee")
            private float carrierCustomerFee;

            @EasyResponse("channel")
            private String channel;

            @EasyResponse("code")
            private String code;

            @EasyResponse("codeDesc")
            private String codeDesc;

            @EasyResponse("discount")
            private float discount;

            @EasyResponse("extCode")
            private String extCode;

            @EasyResponse("fare")
            private float fare;

            @EasyResponse("farebase")
            private String farebase;

            @EasyResponse("fdPrice")
            private float fdPrice;

            @EasyResponse("originalprice")
            private float originalprice;

            @EasyResponse("raisePrice")
            private float raisePrice;

            @EasyResponse("remark")
            private String remark;

            @EasyResponse("saleRange")
            private String saleRange;

            @EasyResponse("saveprice")
            private float saveprice;

            @EasyResponse("seatNum")
            private int seatNum;

            @EasyResponse("showSKpolicy")
            private boolean showSKpolicy;

            @EasyResponse("signature")
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
