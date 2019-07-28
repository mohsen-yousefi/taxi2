package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androgo on 12/21/2018.
 */

public class offerCodeResponseJson {

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("data")
    private OfferCodeResponse data;

    public String getMessage() {
        return status;
    }

    public void setMessage(String message) {
        this.status = message;
    }

    public OfferCodeResponse getData() {
        return data;
    }

    public void setData(OfferCodeResponse data) {
        this.data = data;
    }

}
