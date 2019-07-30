package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Androgo on 10/17/2018.
 */

public class Driver  extends RealmObject implements Serializable {



    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("number_of_vehicle")
    @Expose
    private String numberOfVehicle;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("reg_id")
    @Expose
    private String regId;
    @SerializedName("driver_job")
    @Expose
    private String driverJob;

    @SerializedName("feature")
    @Expose
    private String feature;
    @SerializedName("distance")
    @Expose
    private String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNumberOfVehicle() {
        return numberOfVehicle;
    }

    public void setNumberOfVehicle(String numberOfVehicle) {
        this.numberOfVehicle = numberOfVehicle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getDriverJob() {
        return driverJob;
    }

    public void setDriverJob(String driverJob) {
        this.driverJob = driverJob;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
