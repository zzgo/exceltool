package com.zzgo.bean;

/**
 * Created by 9527 on 2017/11/6.
 */
public class BatchAddress {
    private String id;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BatchAddress(String id, String address) {
        this.id = id;
        this.address = address;
    }
}
