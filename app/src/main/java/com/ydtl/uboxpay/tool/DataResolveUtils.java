package com.ydtl.uboxpay.tool;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.component.Constant;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by HashWaney on 2019/8/30.
 */

public class DataResolveUtils<T> {


    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String formatSignParam(Object object) {
        Map<String, Object> objectMap = getObjByJson(JSONObject.toJSONString(object, SerializerFeature.MapSortField), Map.class);
        List<String> keys = new ArrayList(objectMap.keySet());
        Collections.sort(keys);
        StringBuilder s = new StringBuilder();
        for (String key : keys) {
            if (!TextUtils.isEmpty(key))
                s.append(key + "=" + objectMap.get(key));
        }
        s.append("_" + Constant.APP_KEY);
        String sha1Sign = encrypt(s.toString(), "SHA-1");
        Log.e(DataResolveUtils.class.getSimpleName(), "sha1Sign:" + sha1Sign);
        return sha1Sign;


    }

    public static String buildRequestParam(Object object) {
        Map<String, Object> objectMap = getObjByJson(JSONObject.toJSONString(object, SerializerFeature.MapSortField), Map.class);
        Log.e(DataResolveUtils.class.getSimpleName(), "objectMap:" + objectMap);
        List<String> keys = new ArrayList(objectMap.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            if (!TextUtils.isEmpty(key)) {
                stringBuilder.append(key + "=" + objectMap.get(key));
                stringBuilder.append("&");
            }

        }
        Log.e(DataResolveUtils.class.getSimpleName(), "buildRequestParam:" + stringBuilder + "sign=");
        return stringBuilder.toString() + "sign=";
    }

    private static <T> T getObjByJson(String string, Class<T> mapClass) {
        T Info = null;
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        try {
            Info = mapper.readValue(string, mapClass);
        } catch (JsonParseException e) {
            Info = null;
        } catch (JsonMappingException e) {
            Info = null;
        } catch (IOException e) {
            Info = null;
        }
        return Info;
    }


    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();

    }

    public static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("签名失败！");
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;

    }

    /**
     * 获取商品集合数据
     *
     * @param jsonObject
     * @return
     */
    public static ArrayList<ProductInfo> parseProductList(JSONObject jsonObject) {
        if (jsonObject.containsKey("head")) {
            JSONObject head = jsonObject.getJSONObject("head");
            if (head != null) {
                int resultCode = head.getIntValue("return_code");
                if (200 == resultCode) {
                    JSONObject body = jsonObject.getJSONObject("body");
                    ArrayList<ProductInfo> productList = new ArrayList<>();
                    if (null != body) {
                        JSONArray products = body.getJSONArray("products");
                        if (null != products && products.size() > 0) {
                            for (int i = 0; i < products.size(); i++) {
                                JSONObject productsInfo = products.getJSONObject(i);
                                ProductInfo bean = new ProductInfo();
                                bean.setProductId(String.valueOf(productsInfo.getIntValue("id")));
                                bean.setProductName(
                                        AndroidUtil.isEmpty(productsInfo.getString("name"))
                                                ? "" : productsInfo.getString("name"));
                                bean.setProductNum(String.valueOf(productsInfo.getIntValue("num")));
                                bean.setProductPic(AndroidUtil.isEmpty(productsInfo.getString("pic")) ? "" :
                                        productsInfo.getString("pic"));
                                bean.setProductPrice(String.valueOf(productsInfo.getIntValue("price")));
                                productList.add(bean);
                            }
                        }
                    }
                    return productList;
                }
            }
        }
        return null;

    }


}
