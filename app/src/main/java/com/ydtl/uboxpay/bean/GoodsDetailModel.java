package com.ydtl.uboxpay.bean;

import java.io.Serializable;

/**
 * Created by HashWaney on 2019/8/30.
 */

public class GoodsDetailModel implements Serializable {

    private String goodsPic;
    private String goodsPrice;

    private String goodsName;

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


}





