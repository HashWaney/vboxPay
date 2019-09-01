package com.ydtl.uboxpay.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hash.lib.ui.recyclerview.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class GoodsInfo implements MultiItemEntity, Serializable {
    private static final long serialVersionUID = -1237605666312727740L;
    public static final int NORMAL = 0;//方便后期扩展

    public static final int BANNER = 1;//banner
    private int itemType;

    private ArrayList<GoodsDetailModel> mGoodsDetailList;

    private ArrayList<Object> mBannerInfoData;

    public ArrayList<GoodsDetailModel> getGoodsDetailList() {
        return mGoodsDetailList;
    }

    public void setGoodsDetailList(ArrayList<GoodsDetailModel> mGoodsDetailList) {
        this.mGoodsDetailList = mGoodsDetailList;
    }

    public GoodsInfo() {
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setBannerList(ArrayList<Object> mBannerInfoData) {
        this.mBannerInfoData = mBannerInfoData;

    }

    public ArrayList<Object> getBannerList() {
        return mBannerInfoData;
    }


}
