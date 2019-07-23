package com.rachcode.peykman.model.json.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Androgo on 10/19/2018.
 */

public class DriverRequest implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reg_id")
    @Expose
    private String regId;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;
    @SerializedName("customer_phone")
    @Expose
    private String customer_phone;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("item_name")
    @Expose
    private String item_name;
    @Expose
    @SerializedName("time_accept")
    private String time_accept;
    @SerializedName("sender_type")
    @Expose
    private String sender_type;
    @SerializedName("sender_name")
    @Expose
    private String sender_name;
    @SerializedName("sender_phone")
    @Expose
    private String sender_phone;
    @SerializedName("ricever_phone")
    @Expose
    private String ricever_phone;
    @SerializedName("ricever_name")
    @Expose
    private String ricever_name;
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
    @SerializedName("pay_type")
    @Expose
    private int pay_type;
    @SerializedName("receiver_floor")
    @Expose
    private String receiverFloor;
    @SerializedName("receiver_name")
    @Expose
    private String receiverName;
    @SerializedName(" receiver_phone")
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


    public int getPay_type() {
        return this.pay_type;
    }

    public void setpay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getid() {
        return id;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }


    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customername) {
        this.customer_name = customername;
    }

    public String getCustomer_phoe() {
        return customer_name;
    }

    public void setCustomer_phone(String customername) {
        this.customer_name = customername;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItem_name() {
        return this.item_name;
    }

    public void settem_name(String item) {
        this.item_name = item;
    }


    public String getSender_name() {
        return this.sender_name;
    }

    public void Setsender_name(String item) {
        this.sender_name = item;
    }

    public String getricever_name() {
        return this.ricever_name;
    }

    public void setSender_phone(String item) {
        this.sender_phone = item;
    }

    public String getSender_phone() {
        return this.sender_phone;
    }

    public void Setricever_name(String item) {
        this.ricever_name = item;
    }

    public String getReciver_phone() {
        return this.receiverPhone;
    }

    public void SetReciver_phone(String item) {
        this.receiverPhone = item;
    }


    public void setid(String id) {
        this.id = id;
    }
    public String getTime_accept() {
        return time_accept;
    }

    public void setTime_accept(String time_accept) {
        this.time_accept = time_accept;
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

    public String getItemName() {
        return item_name;
    }

    public void setItemName(String itemName) {
        this.item_name = itemName;
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
