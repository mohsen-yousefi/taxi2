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
    @SerializedName("totalPrice")
    @Expose
    private String totalPrice;
    @SerializedName("byme_price")
    @Expose
    private int byme_price;
    @SerializedName("user_inventory")
    @Expose
    private int user_inventory;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("price_takhfifed")
    @Expose
    private int price_takhfifed;





    @Expose
    @SerializedName("receiver_plaque_second")
    private String receiver_plaque_second;

    @Expose
    @SerializedName("receiver_plaque_third")
    private String receiver_plaque_third;
    @Expose
    @SerializedName("receiver_plaque_fourth")
    private String receiver_plaque_fourth;




    @SerializedName("item_name")
    @Expose
    private String item_name;
    @Expose
    @SerializedName("time_accept")
    private String time_accept;
    @SerializedName("sender_type")
    @Expose
    private String sender_type;
    @Expose
    @SerializedName("receiver_floor_second")
    private String receiver_floor_second;
    @Expose
    @SerializedName("receiver_floor_third")
    private String receiver_floor_third;
    @Expose
    @SerializedName("receiver_floor_fourth")
    private String receiver_floor_fourth;

    @Expose
    @SerializedName("receiver_unit_second")
    private String receiver_unit_second;
    @Expose
    @SerializedName("receiver_unit_third")
    private String receiver_unit_third;
    @Expose
    @SerializedName("receiver_unit_fourth")
    private String receiver_unit_fourth;
    @Expose
    @SerializedName("destination_address_second")
    private String destination_address_second;
    @Expose
    @SerializedName("destination_address_third")
    private String destination_address_third;
    @Expose
    @SerializedName("destination_address_fourth")
    private String destination_address_fourth;
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
    @SerializedName("destination_count")
    @Expose
    private int destination_count;
    @SerializedName("end_latitude")
    @Expose
    private Double endLatitude;
    @SerializedName("end_longitude")
    @Expose
    private Double endLongitude;
    @SerializedName("product_description")
    @Expose
    private String product_description;
    @SerializedName("insurance_id")
    @Expose
    private Integer insuranceId;
    @SerializedName("is_pay")
    @Expose
    private int isPay;

    @SerializedName("product_type")
    @Expose
    private String product_type;
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
    private String price;
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
    @SerializedName("receiver_phone")
    @Expose
    private String receiverPhone;








    @Expose
    @SerializedName("receiver_name_second")
    private String receiver_name_second;
    @Expose
    @SerializedName("receiver_name_third")
    private String receiver_name_third;
    @Expose
    @SerializedName("receiver_name_fourth")
    private String receiver_name_fourth;
    @Expose
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
    @Expose
    @SerializedName("receiver_phone_second")
    private String receiver_phone_second;
    @Expose
    @SerializedName("receiver_phone_fourth")
    private String receiver_phone_fourth;








    @SerializedName("box_type")
    @Expose
    private int box_type;
    @SerializedName("receiver_plaque")
    @Expose
    private String receiverPlaque;
    @SerializedName("receiver_unit")
    @Expose
    private String receiverUnit;
    @SerializedName("sender_floor")
    @Expose
    private String senderFloor;
    @SerializedName("premium")
    @Expose
    private String premium;

    @SerializedName("estimated_costs")
    @Expose
    private String estimated_costs;
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

    public int getdestination_count() {
        return destination_count;
    }

    public void setdestination_count(int destination_count) {
        this.destination_count = destination_count;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String  price) {
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

    public int getBox_type() {
        return box_type;
    }

    public void setBox_type(int box_type) {
        this.box_type = box_type;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getEstimated_costs() {
        return estimated_costs;
    }

    public void setEstimated_costs(String estimated_costs) {
        this.estimated_costs = estimated_costs;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getByme_price() {
        return byme_price;
    }

    public void setByme_price(int byme_price) {
        this.byme_price = byme_price;
    }

    public int getPrice_takhfifed() {
        return price_takhfifed;
    }

    public void setPrice_takhfifed(int price_takhfifed) {
        this.price_takhfifed = price_takhfifed;
    }

    public int getUser_inventory() {
        return user_inventory;
    }

    public void setUser_inventory(int user_inventory) {
        this.user_inventory = user_inventory;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDestination_address_second() {
        return destination_address_second;
    }

    public void setDestination_address_second(String destination_address_second) {
        this.destination_address_second = destination_address_second;
    }

    public String getDestination_address_fourth() {
        return destination_address_fourth;
    }

    public void setDestination_address_fourth(String destination_address_fourth) {
        this.destination_address_fourth = destination_address_fourth;
    }

    public String getDestination_address_third() {
        return destination_address_third;
    }

    public void setDestination_address_third(String destination_address_third) {
        this.destination_address_third = destination_address_third;
    }

    public String getReceiver_floor_fourth() {
        return receiver_floor_fourth;
    }

    public void setReceiver_floor_fourth(String receiver_floor_fourth) {
        this.receiver_floor_fourth = receiver_floor_fourth;
    }

    public String getReceiver_floor_third() {
        return receiver_floor_third;
    }

    public void setReceiver_floor_third(String receiver_floor_third) {
        this.receiver_floor_third = receiver_floor_third;
    }

    public String getReceiver_floor_second() {
        return receiver_floor_second;
    }

    public void setReceiver_floor_second(String receiver_floor_second) {
        this.receiver_floor_second = receiver_floor_second;
    }

    public String getReceiver_unit_third() {
        return receiver_unit_third;
    }

    public void setReceiver_unit_third(String receiver_unit_third) {
        this.receiver_unit_third = receiver_unit_third;
    }


    public String getReceiver_unit_fourth() {
        return receiver_unit_fourth;
    }

    public void setReceiver_unit_fourth(String receiver_unit_fourth) {
        this.receiver_unit_fourth = receiver_unit_fourth;
    }

    public String getReceiver_unit_second() {
        return receiver_unit_second;
    }

    public void setReceiver_unit_second(String receiver_unit_second) {
        this.receiver_unit_second = receiver_unit_second;
    }


    public String getReceiver_plaque_second() {
        return receiver_plaque_second;
    }

    public void setReceiver_plaque_second(String receiver_plaque_second) {
        this.receiver_plaque_second = receiver_plaque_second;
    }

    public String getReceiver_plaque_third() {
        return receiver_plaque_third;
    }

    public void setReceiver_plaque_third(String receiver_plaque_third) {
        this.receiver_plaque_third = receiver_plaque_third;
    }

    public String getReceiver_plaque_fourth() {
        return receiver_plaque_fourth;
    }

    public void setReceiver_plaque_fourth(String receiver_plaque_fourth) {
        this.receiver_plaque_fourth = receiver_plaque_fourth;
    }

    public String getReceiver_name_second() {
        return receiver_name_second;
    }

    public void setReceiver_name_second(String receiver_name_second) {
        this.receiver_name_second = receiver_name_second;
    }

    public String getReceiver_name_third() {
        return receiver_name_third;
    }

    public void setReceiver_name_third(String receiver_name_third) {
        this.receiver_name_third = receiver_name_third;
    }

    public String getReceiver_name_fourth() {
        return receiver_name_fourth;
    }

    public void setReceiver_name_fourth(String receiver_name_fourth) {
        this.receiver_name_fourth = receiver_name_fourth;
    }

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

    public String getReceiver_phone_third() {
        return receiver_phone_third;
    }

    public void setReceiver_phone_third(String receiver_phone_third) {
        this.receiver_phone_third = receiver_phone_third;
    }

    public String getReceiver_phone_second() {
        return receiver_phone_second;
    }

    public void setReceiver_phone_second(String receiver_phone_second) {
        this.receiver_phone_second = receiver_phone_second;
    }

    public String getReceiver_phone_fourth() {
        return receiver_phone_fourth;
    }

    public void setReceiver_phone_fourth(String receiver_phone_fourth) {
        this.receiver_phone_fourth = receiver_phone_fourth;
    }
}
