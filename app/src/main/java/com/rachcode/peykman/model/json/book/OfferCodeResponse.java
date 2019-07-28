package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fathony on 11/02/2017.
 */

public class OfferCodeResponse {
    @Expose
    @SerializedName("ads_credit")
    private String ads_credit;

    @Expose
    @SerializedName("final_price")
    private String final_price;

    public String getfinal_price() {
        return final_price;
    }

    public void setfinal_price(String final_price) {
        this.final_price = final_price;
    }


    public String getAds_credit() {
        return ads_credit;
    }

    public void setads_credit(String ads_credit) {
        this.ads_credit = ads_credit;
    }
}
