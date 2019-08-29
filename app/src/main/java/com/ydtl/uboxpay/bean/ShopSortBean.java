package com.ydtl.uboxpay.bean;

import java.io.Serializable;
import java.util.List;

//分类展示用的bean
public class ShopSortBean implements Serializable {
    private String name;
    private boolean isSeleted;
    private List<ShopBean> goodslist;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean getIsSeleted(){
        return isSeleted;
    }
    public void setIsSeleted(boolean isSeleted){
        this.isSeleted = isSeleted;
    }

    public List<ShopBean> getGoodslist(){
        return goodslist;
    }
    public void setGoodslist(List<ShopBean> goodslist){
        this.goodslist = goodslist;
    }
}
