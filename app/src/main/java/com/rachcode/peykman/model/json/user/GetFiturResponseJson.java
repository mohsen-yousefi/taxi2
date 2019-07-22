package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.DiscountMpay;
import com.rachcode.peykman.model.Fitur;
import com.rachcode.peykman.model.MfoodPartner;

import java.util.List;

public class GetFiturResponseJson {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Fitur> data = null;
    @SerializedName("discount_mpay")
    @Expose
    private DiscountMpay discountMpay;
    @SerializedName("mfood_partner")
    @Expose
    private MfoodPartner mfoodPartner;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Fitur> getData() {
        return data;
    }

    public void setData(List<Fitur> data) {
        this.data = data;
    }

    public DiscountMpay getDiscountMpay() {
        return discountMpay;
    }

    public void setDiscountMpay(DiscountMpay discountMpay) {
        this.discountMpay = discountMpay;
    }

    public MfoodPartner getMfoodPartner() {
        return mfoodPartner;
    }

    public void setMfoodPartner(MfoodPartner mfoodPartner) {
        this.mfoodPartner = mfoodPartner;
    }
}
