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
    private String goodsPic;
    private String goodsPrice;
    private ArrayList<Object> mBannerInfoData;

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "itemType=" + itemType +
                ", goodsPic='" + goodsPic + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }

    private String goodsName;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public GoodsInfo() {
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


    public class GoodsBannerInfo {
        private String jumpUrl;
        private String picPath;

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
