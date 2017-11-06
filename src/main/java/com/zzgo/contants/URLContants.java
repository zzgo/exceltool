package com.zzgo.contants;

/**
 * Created by 9527 on 2017/10/30.
 */
public class URLContants {
    public static final String NEW_URL = "http://192.168.10.252/gateway/geocoding/geoSingle?address=${address}&ak" +
            "=9999&coordType=gcj02ll&isDebug=true&isPrecise=true";

    public static final String DITUHUI_ONLINE_URL = "http://restapi.dituhui" +
            ".com/v1/geocoding/geoSingle?address=${address}&ak=d84ab9adb4c04216b5f86f6d42156838&coordType=gcj02ll" +
            "&isDebug=true&isPrecise=true";
    public static final String BATCH_DITUHUI_ONLINE_URL =
            "http://restapi.dituhui.com/v1/geocoding/geo?addresses=${addresses}&ak=d84ab9adb4c04216b5f86f6d42156838" +
                    "&isPrecise" +
                    "=true";
    //官
    public static final String GUAN_URL =
            "http://192.168.11.31:8080/gateway/geocoding/geoSingle?address=${address}&ak" +
                    "=d84ab9adb4c04216b5f86f6d42156838&coordType" +
                    "=gcj02ll" +
                    "&isDebug=true&isPrecise=true";
}
