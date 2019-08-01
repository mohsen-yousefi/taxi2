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
    @SerializedName("destination_count")
    public int destination_count;
    @Expose
    @SerializedName("discount_amount")
    public String discount_amount;
    @Expose
    @SerializedName("final_price")
    public String final_price;

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

    @SerializedName("end_latitude_second")
    private double end_latitude_second;
    @Expose
    @SerializedName("end_latitude_fourth")
    private double end_latitude_fourth;
    @Expose
    @SerializedName("end_latitude_third")
    private double end_latitude_third;

    @Expose
    @SerializedName("end_longitude_second")
    private double end_longitude_second;
    @Expose
    @SerializedName("end_longitude_third")
    private double end_longitude_third;
    @Expose
    @SerializedName("end_longitude_fourth")
    private double end_longitude_fourth;
    @Expose
    @SerializedName("receiver_phone_third")
    private String receiver_phone_third;


    public double getEnd_latitude_second() {
        return end_latitude_second;
    }

    public void setEnd_latitude_second(double end_latitude_second) {
        this.end_latitude_second = end_latitude_second;
    }

    public double getEnd_latitude_fourth() {
        return end_latitude_fourth;
    }

    public void setEnd_latitude_fourth(double end_latitude_fourth) {
        this.end_latitude_fourth = end_latitude_fourth;
    }

    public double getEnd_latitude_third() {
        return end_latitude_third;
    }

    public void setEnd_latitude_third(double end_latitude_third) {
        this.end_latitude_third = end_latitude_third;
    }

    public double getEnd_longitude_second() {
        return end_longitude_second;
    }

    public void setEnd_longitude_second(double end_longitude_second) {
        this.end_longitude_second = end_longitude_second;
    }

    public double getEnd_longitude_third() {
        return end_longitude_third;
    }

    public void setEnd_longitude_third(double end_longitude_third) {
        this.end_longitude_third = end_longitude_third;
    }

    public double getEnd_longitude_fourth() {
        return end_longitude_fourth;
    }

    public void setEnd_longitude_fourth(double end_longitude_fourth) {
        this.end_longitude_fourth = end_longitude_fourth;
    }

    public Double getStartLatitude() {
        return start_latitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.start_latitude = startLatitude;
    }

    public Double getStartLongitude() {
        return start_longitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.start_longitude = startLongitude;
    }

    public Double getEndLatitude() {
        return end_latitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.end_latitude = endLatitude;
    }

    public Double getEndLongitude() {
        return end_longitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.end_longitude = endLongitude;
    }
    public int getdestination_count() {
        return destination_count;
    }

    public void setdestination_count(int destination_count) {
        this.destination_count = destination_count;
    }

}
