package com.ydtl.uboxpay.bean;

import java.io.Serializable;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class GoodsBannerInfo implements Serializable {
    private static final long serialVersionUID = -1237605666312727740L;

    private String jumpUrl; //广告链接
    private String picPath;//图片链接

    @Override
    public String toString() {
        return "GoodsBannerInfo{" +
                "jumpUrl='" + jumpUrl + '\'' +
                ", picPath='" + picPath + '\'' +
                '}';
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
