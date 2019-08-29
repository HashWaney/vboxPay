package com.ydtl.uboxpay.bean;

import java.io.Serializable;

public class ShopBean implements Serializable  {
    private String name;
    private String thumbpic;
    private String goods_id;
    private String id;
    private int shopNum;
    private int isSelect;
    private int price;

    public void initShopBean(){
        shopNum = 0;
        isSelect = 0;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getThumbpic(){
        return thumbpic;
    }
    public void setThumbpic(String thumbpic){
        this.thumbpic = thumbpic;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getGoods_id(){
        return goods_id;
    }
    public void setGoods_id(String goods_id){
        this.goods_id = goods_id;
    }

    public int getShopNum(){
        return shopNum;
    }
    public void setShopNum(int shopNum){
        this.shopNum = shopNum;
    }

    public int getIsSelect(){
        return isSelect;
    }
    public void setIsSelect(int isSelect){
        this.isSelect = isSelect;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price = price;
    }
}
