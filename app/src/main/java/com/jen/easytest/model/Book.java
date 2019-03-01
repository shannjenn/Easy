package com.jen.easytest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jen.easy.EasyRequest;
import com.jen.easy.EasyRequestType;
import com.jen.easy.EasyResponse;

/**
 * Created by Administrator on 2018/3/28.
 */

public class Book implements Parcelable {

    public Book(){

    }

    @EasyResponse(value = "_id")//返回参数名为_id
    private int id = 11;

    @EasyRequest(type = EasyRequestType.Param)
    private String name = "22";//不注释默认作为参数返回,参数名与变量名一致，name

    private long date = 333;

    private String des;

    //    @EasyResponse(invalid = true)//noResp = true，则不作为返回参数
    private boolean isCheck;


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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeLong(date);
        dest.writeString(des);
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }

    protected Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
        date = in.readLong();
        des = in.readString();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
