package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Androgo on 12/21/2018.
 */

public class OffecrcodeequestJson {

    @Expose
    @SerializedName("order_feature")
    private int order_feature;

    @Expose
    @SerializedName("price")
    private int price;

    @Expose
    @SerializedName("coupon_serial")
    private String coupon_serial;

    public double getOrder_feature() {
        return order_feature;
    }

    public void setOrder_feature(int order_feature) {
        this.order_feature = order_feature;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String  getCoupon_serial() {
        return coupon_serial;
    }

    public void setCoupon_serial(String idService) {
        this.coupon_serial = idService;
    }

}
