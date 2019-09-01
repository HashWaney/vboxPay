package com.ydtl.uboxpay.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HashWaney on 2019/8/31.
 */

public class ProductBean implements Parcelable {
    private String app_id;
    private String vmid;


    public ProductBean() {
    }

    protected ProductBean(Parcel in) {
        app_id = in.readString();
        vmid = in.readString();
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel in) {
            return new ProductBean(in);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };

    @Override
    public String toString() {
        return "ProductBean{" +
                "app_id='" + app_id + '\'' +
                ", vmid='" + vmid + '\'' +
                '}';
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(app_id);
        dest.writeString(vmid);
    }
}
