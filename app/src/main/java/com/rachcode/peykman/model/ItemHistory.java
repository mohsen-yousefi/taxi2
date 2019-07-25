package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rachcode.peykman.R;

import java.io.Serializable;

/**
 * Created by haris on 12/1/16.
 */

public class ItemHistory implements Serializable {
    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("driver_id")
    public String driver_id;

    @Expose
    @SerializedName("order_feature")
    public String order_feature;

    @Expose
    @SerializedName("start_latitude")
    public double start_latitude;

    @Expose
    @SerializedName("start_longitude")
    public double start_longitude;

    @Expose
    @SerializedName("end_latitude")
    public double end_latitude;

    @Expose
    @SerializedName("end_longitude")
    public double end_longitude;

    @Expose
    @SerializedName("order_start_time")
    public String order_start_time;

    @Expose
    @SerializedName("order_finish_time")
    public String order_finish_time;

    @Expose
    @SerializedName("origin_address")
    public String origin_address;

    @Expose
    @SerializedName("destination_address")
    public String destination_address;

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("driver_first_name")
    public String driver_first_name;

    @Expose
    @SerializedName("driver_last_name")
    public String driver_last_name;

    @Expose
    @SerializedName("phone")
    public String phone;

    @Expose
    @SerializedName("photo")
    public String photo;

    @Expose
    @SerializedName("rating")
    public String rating;

    @Expose
    @SerializedName("price")
    public long price;

    @Expose
    @SerializedName("distance")
    public double distance;

    @Expose
    @SerializedName("reg_id")
    public String reg_id;

    @Expose
    @SerializedName("brand")
    public String brand;

    @Expose
    @SerializedName("type")
    public String type;

    @Expose
    @SerializedName("kind")
    public String kind;

    @Expose
    @SerializedName("number_of_vehicle")
    public String number_of_vehicle;

    @Expose
    @SerializedName("color")
    public String color;


    @Expose
    @SerializedName("image_id")
    public int image_id = R.drawable.ic_mride;


}
