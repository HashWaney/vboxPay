package com.ydtl.uboxpay.bean;

public class ShopData {
    private String companyName;
    private String deviceId;
    private String prefix;
    private int paytime;
    private int playtime;

    public String getCompanyName(){
        return companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getDeviceId(){
        return deviceId;
    }
    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public String getPrefix(){
        return prefix;
    }
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    public int getPaytime(){
        return paytime;
    }
    public void setPaytime(int paytime){
        this.paytime = paytime;
    }

    public int getPlaytime(){
        return playtime;
    }
    public void setPlaytime(int playtime){
        this.playtime = playtime;
    }
}
