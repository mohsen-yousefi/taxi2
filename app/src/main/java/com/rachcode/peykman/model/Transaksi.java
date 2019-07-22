package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

public class Transaksi extends RealmObject implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_start_time")
    @Expose
    private Date order_start_time;
    @SerializedName("ads_code")
    @Expose
    private String ads_code;
    @SerializedName("ads_credit")
    @Expose
    private String ads_credit;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("destination_address")
    @Expose
    private String destinationAddress;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("end_latitude")
    @Expose
    private Double endLatitude;
    @SerializedName("end_longitude")
    @Expose
    private Double endLongitude;
    @SerializedName("insurance_id")
    @Expose
    private Integer insuranceId;
    @SerializedName("is_pay")
    @Expose
    private int isPay;
    @SerializedName("pay_type")
    @Expose
    private int pay_type;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("name_of_the_sender")
    @Expose
    private String nameOfTheSender;
    @SerializedName("order_feature")
    @Expose
    private String orderFeature;
    @SerializedName("origin_address")
    @Expose
    private String originAddress;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("receiver_floor")
    @Expose
    private String receiverFloor;
    @SerializedName("receiver_name")
    @Expose
    private String receiverName;
    @SerializedName("receiver_phone")
    @Expose
    private String receiverPhone;
    @SerializedName("receiver_plaque")
    @Expose
    private String receiverPlaque;
    @SerializedName("receiver_unit")
    @Expose
    private String receiverUnit;
    @SerializedName("sender_floor")
    @Expose
    private String senderFloor;
    @SerializedName("sender_plaque")
    @Expose
    private String senderPlaque;
    @SerializedName("sender_unit")
    @Expose
    private String senderUnit;
    @SerializedName("senders_phone")
    @Expose
    private String sendersPhone;
    @SerializedName("start_latitude")
    @Expose
    private Double startLatitude;
    @SerializedName("start_longitude")
    @Expose
    private Double startLongitude;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public int getpay_type() {
        return pay_type;
    }

    public void setpay_type(int isPay) {
        this.pay_type = pay_type;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNameOfTheSender() {
        return nameOfTheSender;
    }

    public void setNameOfTheSender(String nameOfTheSender) {
        this.nameOfTheSender = nameOfTheSender;
    }

    public String getads_credit() {
        return ads_credit;
    }

    public void setads_credit(String ads_credit) {
        this.ads_credit = ads_credit;
    }

    public String getads_code() {
        return ads_code;
    }

    public void setads_code(String ads_code) {
        this.ads_code = ads_code;
    }

    public String getOrderFeature() {
        return orderFeature;
    }

    public void setOrderFeature(String orderFeature) {
        this.orderFeature = orderFeature;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getReceiverFloor() {
        return receiverFloor;
    }

    public void setReceiverFloor(String receiverFloor) {
        this.receiverFloor = receiverFloor;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPlaque() {
        return receiverPlaque;
    }

    public void setReceiverPlaque(String receiverPlaque) {
        this.receiverPlaque = receiverPlaque;
    }

    public String getReceiverUnit() {
        return receiverUnit;
    }

    public void setReceiverUnit(String receiverUnit) {
        this.receiverUnit = receiverUnit;
    }

    public String getSenderFloor() {
        return senderFloor;
    }

    public void setSenderFloor(String senderFloor) {
        this.senderFloor = senderFloor;
    }

    public String getSenderPlaque() {
        return senderPlaque;
    }

    public void setSenderPlaque(String senderPlaque) {
        this.senderPlaque = senderPlaque;
    }

    public String getSenderUnit() {
        return senderUnit;
    }

    public void setSenderUnit(String senderUnit) {
        this.senderUnit = senderUnit;
    }

    public String getSendersPhone() {
        return sendersPhone;
    }

    public void setSendersPhone(String sendersPhone) {
        this.sendersPhone = sendersPhone;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }
    public Date getorder_start_time() {
        return order_start_time;
    }

    public void setorder_start_time(Date order_start_time) {
        this.order_start_time = order_start_time;
    }
}
