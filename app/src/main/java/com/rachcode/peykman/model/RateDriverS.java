package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

public class RateDriverS extends RealmObject implements Serializable {

    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;
    @SerializedName("customer_id")
    @Expose
    private String customer_id;
    @SerializedName("id_driver")
    @Expose
    private String id_driver;
    @SerializedName("driver_photo")
    @Expose
    private String driver_photo;
    @SerializedName("dfirst_name")
    @Expose
    private String dfirst_name;
    @SerializedName("dlast_name")
    @Expose
    private String dlast_name;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("type")
    @Expose
    private String type;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getDriver_photo() {
        return driver_photo;
    }

    public void setDriver_photo(String driver_photo) {
        this.driver_photo = driver_photo;
    }

    public String getDfirst_name() {
        return dfirst_name;
    }

    public void setDfirst_name(String dfirst_name) {
        this.dfirst_name = dfirst_name;
    }

    public String getDlast_name() {
        return dlast_name;
    }

    public void setDlast_name(String dlast_name) {
        this.dlast_name = dlast_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @SerializedName("color")
    @Expose
    private String color;


}
