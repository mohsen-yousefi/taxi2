package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Androgo on 10/17/2018.
 */

public class Fitur extends RealmObject implements Serializable {

    @SerializedName("id_feature")
    @Expose
    private int id_feature;
    @SerializedName("feature")
    @Expose
    private String feature;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("minimum_price")
    @Expose
    private int minimumPrice;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_id")
    @Expose
    private String discount_id;
    @SerializedName("final_price")
    @Expose
    private String finalPrice;

    public int getIdFeature() {
        return id_feature;
    }

    public void setIdFeature(int id_feature) {
        this.id_feature = id_feature;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(int minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }
}
