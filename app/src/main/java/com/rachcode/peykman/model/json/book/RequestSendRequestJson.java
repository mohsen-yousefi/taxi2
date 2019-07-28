package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Androgo on 10/19/2018.
 */

public class RequestSendRequestJson implements Serializable {

    @Expose
    @SerializedName("customer_id")
    public String customer_id;
    @Expose
    @SerializedName("ads_code")
    public int ads_code;
    @Expose
    @SerializedName("mablaghTakhfifSabet")
    public int mablaghTakhfifSabet;
    @Expose
    @SerializedName("ads_credit")
    public String ads_credit;
    @Expose
    @SerializedName("discount_id")
    public String discount_id;
    @Expose
    @SerializedName("totalPrice")
    public String totalPrice;

    @Expose
    @SerializedName("user_inventory")
    public String user_inventory;

    @Expose
    @SerializedName("order_feature")
    public int order_feature;
    @Expose
    @SerializedName("price_takhfifed")
    public int price_takhfifed;
    @Expose
    @SerializedName("delay")
    public int delay;
    @Expose
    @SerializedName("byme_price")
    public int byme_price;
    @Expose
    @SerializedName("go_back")
    public int go_back;

    @Expose
    @SerializedName("start_latitude")
    public double start_latitude;

    @Expose
    @SerializedName("box_type")
    public int box_type;
    @Expose
    @SerializedName("product_type")
    private String product_type;

    @Expose
    @SerializedName("start_longitude")
    public double start_longitude;

    @Expose
    @SerializedName("product_description")
    public String product_description;

    @Expose
    @SerializedName("end_latitude")
    public double end_latitude;
    @Expose
    @SerializedName("end_latitude_second")
    public double end_latitude_second;
    @Expose
    @SerializedName("end_latitude_fourth")
    public double end_latitude_fourth;
    @Expose
    @SerializedName("end_latitude_third")
    public double end_latitude_third;

    @Expose
    @SerializedName("end_longitude")
    public double end_longitude;
    @Expose
    @SerializedName("end_longitude_second")
    public double end_longitude_second;
    @Expose
    @SerializedName("end_longitude_third")
    public double end_longitude_third;
    @Expose
    @SerializedName("end_longitude_fourth")
    public double end_longitude_fourth;

    @Expose
    @SerializedName("distance")
    public double distance;

    @Expose
    @SerializedName("price")
    public String price;

    @Expose
    @SerializedName("final_price")
    public long final_price;

    @Expose
    @SerializedName("origin_address")
    public String origin_address;

    @Expose
    @SerializedName("destination_address")
    public String destination_address;
    @Expose
    @SerializedName("destination_address_second")
    public String destination_address_second;
    @Expose
    @SerializedName("destination_address_third")
    public String destination_address_third;
    @Expose
    @SerializedName("destination_address_fourth")
    public String destination_address_fourth;

    @Expose
    @SerializedName("is_pay")
    public int is_pay;

    @Expose
    @SerializedName("name_of_the_sender")
    public String name_of_the_sender;

    @Expose
    @SerializedName("destination_count")
    public int destination_count;

    @Expose
    @SerializedName("senders_phone")
    public String senders_phone;

    @Expose
    @SerializedName("pay_type")
    public int pay_type;

    @Expose
    @SerializedName("receiver_name")
    public String receiver_name;
    @Expose
    @SerializedName("receiver_name_second")
    public String receiver_name_second;
    @Expose
    @SerializedName("receiver_name_third")
    public String receiver_name_third;
    @Expose
    @SerializedName("receiver_name_fourth")
    public String receiver_name_fourth;

    @Expose
    @SerializedName("sender_plaque")
    public String sender_plaque;


    @Expose
    @SerializedName("sender_floor")
    public String sender_floor;

    @Expose
    @SerializedName("sender_unit")
    public String sender_unit;

    @Expose
    @SerializedName("receiver_plaque")
    public String receiver_plaque;

    @Expose
    @SerializedName("receiver_plaque_second")
    public String receiver_plaque_second;

    @Expose
    @SerializedName("receiver_plaque_third")
    public String receiver_plaque_third;
    @Expose
    @SerializedName("receiver_plaque_fourth")
    public String receiver_plaque_fourth;

    @Expose
    @SerializedName("receiver_floor")
    public String receiver_floor;

    @Expose
    @SerializedName("receiver_floor_second")
    public String receiver_floor_second;
    @Expose
    @SerializedName("receiver_floor_third")
    public String receiver_floor_third;
    @Expose
    @SerializedName("receiver_floor_fourth")
    public String receiver_floor_fourth;

    @Expose
    @SerializedName("receiver_phone")
    public String receiver_phone;
    @Expose
    @SerializedName("receiver_phone_third")
    public String receiver_phone_third;
    @Expose
    @SerializedName("receiver_phone_second")
    public String receiver_phone_second;
    @Expose
    @SerializedName("receiver_phone_fourth")
    public String receiver_phone_fourth;

    @Expose
    @SerializedName("receiver_unit")
    public String receiver_unit;
    @Expose
    @SerializedName("receiver_unit_second")
    public String receiver_unit_second;
    @Expose
    @SerializedName("receiver_unit_third")
    public String receiver_unit_third;
    @Expose
    @SerializedName("receiver_unit_fourth")
    public String receiver_unit_fourth;

    @Expose
    @SerializedName("insurance_id")
    public int insurance_id;


    @Expose
    @SerializedName("item_name")
    public String item_name;

    @Expose
    @SerializedName("product_id")
    public int product_id;


    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_type() {
        return product_type;
    }


}
