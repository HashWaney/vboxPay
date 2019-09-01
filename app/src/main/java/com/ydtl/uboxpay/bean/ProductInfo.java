package com.ydtl.uboxpay.bean;

import java.io.Serializable;

/**
 * Created by HashWaney on 2019/8/31.
 */

public class ProductInfo implements Serializable {
    private static final long serialVersionUID = -1237605626312727740L;

    private String productId;
    private String productName;
    private String productPrice;
    private String productPic;
    private String productNum;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productPic='" + productPic + '\'' +
                ", productNum='" + productNum + '\'' +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
}
