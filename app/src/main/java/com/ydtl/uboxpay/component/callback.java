package com.ydtl.uboxpay.component;

import android.util.Log;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public abstract class callback extends StringCallback {
    @Override
    public void onSuccess(Response<String> response) {
        String sJson = response.body();
        Log.e("callback: sjson =",sJson);
        try{
            onresponse(sJson);
        }catch(Exception e){
            onfailure(response,Constant.TH_INTERFACE_FAILED);
        }
    }
    @Override
    public void onError(Response<String> response) {
        Log.e("onError",response.toString());
        super.onError(response);
        onfailure(response,Constant.TH_INTERFACE_FAILED);
    }

    public abstract void onresponse(String sJson);

    public abstract void onfailure(Response<String> response, int rtnCode);
}
