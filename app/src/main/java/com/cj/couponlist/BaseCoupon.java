package com.cj.couponlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BaseCoupon implements Parcelable,Serializable {

    private String discount;
    private String batchId;
    private boolean hideRmbSymbol;
    private String title;
    private String usableTime; // 这两个是新统一字段,需要在服务端统一补齐
    private String limitInfoTxt;
    // 面值
    private String quota;
    // 面值显示内容,服务端统一返回
    private String quotaTxt;

    // 优惠券选择
    private boolean canUse;
    private boolean selected;

    private int status;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public boolean isHideRmbSymbol() {
        return hideRmbSymbol;
    }

    public void setHideRmbSymbol(boolean hideRmbSymbol) {
        this.hideRmbSymbol = hideRmbSymbol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsableTime() {
        return usableTime;
    }

    public void setUsableTime(String usableTime) {
        this.usableTime = usableTime;
    }

    public String getLimitInfoTxt() {
        return limitInfoTxt;
    }

    public void setLimitInfoTxt(String limitInfoTxt) {
        this.limitInfoTxt = limitInfoTxt;
    }

    public boolean isCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getQuotaTxt() {
        return quotaTxt;
    }

    public void setQuotaTxt(String quotaTxt) {
        this.quotaTxt = quotaTxt;
    }

    public BaseCoupon() {}

    protected BaseCoupon(Parcel in) {
        discount = in.readString();
        batchId = in.readString();
        hideRmbSymbol = in.readByte() != 0;
        title = in.readString();
        usableTime = in.readString();
        limitInfoTxt = in.readString();
        quota = in.readString();
        quotaTxt = in.readString();
        canUse = in.readByte() != 0;
        selected = in.readByte() != 0;
        status = in.readInt();
    }

    public static final Creator<BaseCoupon> CREATOR = new Creator<BaseCoupon>() {
        @Override
        public BaseCoupon createFromParcel(Parcel in) {
            return new BaseCoupon(in);
        }

        @Override
        public BaseCoupon[] newArray(int size) {
            return new BaseCoupon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(discount);
        dest.writeString(batchId);
        dest.writeByte((byte) (hideRmbSymbol ? 1 : 0));
        dest.writeString(title);
        dest.writeString(usableTime);
        dest.writeString(limitInfoTxt);
        dest.writeString(quota);
        dest.writeString(quotaTxt);
        dest.writeByte((byte) (canUse ? 1 : 0));
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeInt(status);
    }
}
