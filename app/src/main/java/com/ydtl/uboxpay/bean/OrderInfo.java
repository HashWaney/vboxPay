package com.ydtl.uboxpay.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HashWaney on 2019/9/2.
 */

public class OrderInfo implements Parcelable {
    private String app_id;

    private String vmid;

    private String product_id; //商品id

    private String app_uid; //第三方应用用户id  默认

    private String app_tran_id;//第三方应用的交易id

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getVmid() {
        return vmid;
    }

    public void setVmid(String vmid) {
        this.vmid = vmid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getApp_uid() {
        return app_uid;
    }

    public void setApp_uid(String app_uid) {
        this.app_uid = app_uid;
    }

    public String getApp_tran_id() {
        return app_tran_id;
    }

    public void setApp_tran_id(String app_tran_id) {
        this.app_tran_id = app_tran_id;
    }

    public OrderInfo() {

    }

    protected OrderInfo(Parcel in) {
        app_id = in.readString();
        vmid = in.readString();
        product_id = in.readString();
        app_uid = in.readString();
        app_tran_id = in.readString();
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel in) {
            return new OrderInfo(in);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(app_id);
        dest.writeString(vmid);
        dest.writeString(product_id);
        dest.writeString(app_uid);
        dest.writeString(app_tran_id);
    }
}
