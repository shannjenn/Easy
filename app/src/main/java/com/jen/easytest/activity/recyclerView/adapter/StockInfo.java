package com.jen.easytest.activity.recyclerView.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.jen.easy.EasyResponse;
import com.jen.easy.invalid.EasyInvalid;

import java.io.Serializable;

/**
 * 作者：ShannJenn
 * 时间：2018/10/30.
 * 说明：
 */
public class StockInfo implements Comparable<StockInfo>, Parcelable, Serializable {

    public StockInfo() {

    }

    protected StockInfo(Parcel in) {
        isCheck = in.readByte() != 0;
        assetId = in.readString();
        name = in.readString();
        secType = in.readInt();
        secSType = in.readInt();
        stkStatus = in.readInt();
        price = in.readDouble();
        changePct = in.readDouble();
        change = in.readDouble();
        totalVal = in.readDouble();
        totalVolume = in.readDouble();
        turnOver = in.readDouble();
        turnRate = in.readDouble();
        pe = in.readDouble();
        pb = in.readDouble();
        amplitude = in.readDouble();
        volRate = in.readDouble();
        committee = in.readDouble();
        sevenDayChgPct = in.readDouble();
        prevClose = in.readDouble();
    }

    public static final Creator<StockInfo> CREATOR = new Creator<StockInfo>() {

        @Override
        public StockInfo createFromParcel(Parcel in) {
            return new StockInfo(in);
        }

        @Override
        public StockInfo[] newArray(int size) {
            return new StockInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(assetId);
        dest.writeString(name);
        dest.writeInt(secType);
        dest.writeInt(secSType);
        dest.writeInt(stkStatus);
        dest.writeDouble(price);
        dest.writeDouble(changePct);
        dest.writeDouble(change);
        dest.writeDouble(totalVal);
        dest.writeDouble(totalVolume);
        dest.writeDouble(turnOver);
        dest.writeDouble(turnRate);
        dest.writeDouble(pe);
        dest.writeDouble(pb);
        dest.writeDouble(amplitude);
        dest.writeDouble(volRate);
        dest.writeDouble(committee);
        dest.writeDouble(sevenDayChgPct);
        dest.writeDouble(prevClose);
    }

    //名称代码 最新价格 涨跌幅 涨跌额，总市值，成交额，成交量，换手率，市盈率，振幅，量比，委比
    @EasyInvalid
    public enum CompType {
        price,
        changePct,
        change,
        totalVal,
        turnOver,
        totalVolume,
        turnRate,
        pe,
        pb,
        amplitude,
        volRate,
        committee,
        sevenDayChgPct,
        prevClose
    }

    @EasyInvalid
    public static SortStockTextView.Sort sort = SortStockTextView.Sort.DEFAULT;
    @EasyInvalid
    public static CompType compType = CompType.price;
    @EasyInvalid
    private boolean isCheck;

    private String assetId;// 股票资产ID
    @EasyResponse("stkName")
    private String name;// 股票名称
    private int secType = -1;// 股票类别
    private int secSType = -1;// 股票细分类别
    private int stkStatus = 0; // 股票自选状态 0 未添加自选 1 已添加自选

    private double price;// 现价
    private double changePct;// 涨跌幅
    private double change; //涨跌额
    private double totalVal; //总市值
    private double totalVolume; // 成交量
    private double turnOver; //成交额
    private double turnRate;//换手率
    private double pe;//市盈率
    private double pb;//市净率
    private double amplitude;//振幅
    private double volRate;//量比
    private double committee;//委比
    private double sevenDayChgPct; // 7日涨跌幅

    private double prevClose; //昨收价

    @Override
    public int compareTo(@NonNull StockInfo o) {
        if (sort == SortStockTextView.Sort.DEFAULT) {
            return getName().compareTo(o.getName());
        }
        switch (compType) {
            case price: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (price - o.price > 0) {
                        res = 1;
                    } else if (price - o.price < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (price - o.price > 0) {
                        res = -1;
                    } else if (price - o.price < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case changePct: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (changePct - o.changePct > 0) {
                        res = 1;
                    } else if (changePct - o.changePct < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (changePct - o.changePct > 0) {
                        res = -1;
                    } else if (changePct - o.changePct < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case change: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (change - o.change > 0) {
                        res = 1;
                    } else if (change - o.change < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (change - o.change > 0) {
                        res = -1;
                    } else if (change - o.change < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case totalVal: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (totalVal - o.totalVal > 0) {
                        res = 1;
                    } else if (totalVal - o.totalVal < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (totalVal - o.totalVal > 0) {
                        res = -1;
                    } else if (totalVal - o.totalVal < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case turnOver: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (turnOver - o.turnOver > 0) {
                        res = 1;
                    } else if (turnOver - o.turnOver < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (turnOver - o.turnOver > 0) {
                        res = -1;
                    } else if (turnOver - o.turnOver < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case totalVolume: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (totalVolume - o.totalVolume > 0) {
                        res = 1;
                    } else if (totalVolume - o.totalVolume < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (totalVolume - o.totalVolume > 0) {
                        res = -1;
                    } else if (totalVolume - o.totalVolume < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case turnRate: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (turnRate - o.turnRate > 0) {
                        res = 1;
                    } else if (turnRate - o.turnRate < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (turnRate - o.turnRate > 0) {
                        res = -1;
                    } else if (turnRate - o.turnRate < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case pe: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (pe - o.pe > 0) {
                        res = 1;
                    } else if (pe - o.pe < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (pe - o.pe > 0) {
                        res = -1;
                    } else if (pe - o.pe < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case pb: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (pb - o.pb > 0) {
                        res = 1;
                    } else if (pb - o.pb < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (pb - o.pb > 0) {
                        res = -1;
                    } else if (pb - o.pb < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case amplitude: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (amplitude - o.amplitude > 0) {
                        res = 1;
                    } else if (amplitude - o.amplitude < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (amplitude - o.amplitude > 0) {
                        res = -1;
                    } else if (amplitude - o.amplitude < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case volRate: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (volRate - o.volRate > 0) {
                        res = 1;
                    } else if (volRate - o.volRate < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (volRate - o.volRate > 0) {
                        res = -1;
                    } else if (volRate - o.volRate < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case committee: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (committee - o.committee > 0) {
                        res = 1;
                    } else if (committee - o.committee < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (committee - o.committee > 0) {
                        res = -1;
                    } else if (committee - o.committee < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case sevenDayChgPct: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (sevenDayChgPct - o.sevenDayChgPct > 0) {
                        res = 1;
                    } else if (sevenDayChgPct - o.sevenDayChgPct < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (sevenDayChgPct - o.sevenDayChgPct > 0) {
                        res = -1;
                    } else if (sevenDayChgPct - o.sevenDayChgPct < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
            case prevClose: {
                if (sort == SortStockTextView.Sort.ASC) {
                    int res = 0;
                    if (prevClose - o.prevClose > 0) {
                        res = 1;
                    } else if (prevClose - o.prevClose < 0) {
                        res = -1;
                    }
                    return res;
                } else {
                    int res = 0;
                    if (prevClose - o.prevClose > 0) {
                        res = -1;
                    } else if (prevClose - o.prevClose < 0) {
                        res = 1;
                    }
                    return res;
                }
            }
        }
        return name.compareTo(o.name);
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    */

    /**
     * 该方法负责序列化
     *//*
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price);
        dest.writeString(name);
        // boolean 可以使用int或byte方式进行存储,怎么存就怎么取
//        dest.writeInt(isMale ? 1 : 0);
    }

    public StockInfo() {

    }
    private StockInfo(Parcel in) {
        int data = in.readInt();
    }

    public static final Parcelable.Creator<StockInfo> CREATOR
            = new Parcelable.Creator<StockInfo>() {
        public StockInfo createFromParcel(Parcel in) {
            return new StockInfo(in);
        }

        public StockInfo[] newArray(int size) {
            return new StockInfo[size];
        }
    };*/
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getAssetId() {
        return assetId == null ? "" : assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecType() {
        return secType;
    }

    public void setSecType(int secType) {
        this.secType = secType;
    }

    public int getSecSType() {
        return secSType;
    }

    public void setSecSType(int secSType) {
        this.secSType = secSType;
    }

    public int getStkStatus() {
        return stkStatus;
    }

    public void setStkStatus(int stkStatus) {
        this.stkStatus = stkStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChangePct() {
        return changePct;
    }

    public void setChangePct(double changePct) {
        this.changePct = changePct;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getTotalVal() {
        return totalVal;
    }

    public void setTotalVal(double totalVal) {
        this.totalVal = totalVal;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(double turnOver) {
        this.turnOver = turnOver;
    }

    public double getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

    public double getPe() {
        return pe;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }

    public double getPb() {
        return pb;
    }

    public void setPb(double pb) {
        this.pb = pb;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getVolRate() {
        return volRate;
    }

    public void setVolRate(double volRate) {
        this.volRate = volRate;
    }

    public double getCommittee() {
        return committee;
    }

    public void setCommittee(double committee) {
        this.committee = committee;
    }

    public double getSevenDayChgPct() {
        return sevenDayChgPct;
    }

    public void setSevenDayChgPct(double sevenDayChgPct) {
        this.sevenDayChgPct = sevenDayChgPct;
    }

    public double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(double prevClose) {
        this.prevClose = prevClose;
    }
}
