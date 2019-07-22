package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class MfoodPartner extends RealmObject implements Serializable {
    @SerializedName("id_feature")
    @Expose
    private String idFeature;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("minimum_price")
    @Expose
    private String minimumPrice;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("final_price")
    @Expose
    private Double finalPrice;

    public String getIdFeature() {
        return idFeature;
    }

    public void setIdFeature(String idFeature) {
        this.idFeature = idFeature;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
