package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haris on 11/28/16.
 */

public class RateDriverRequestJson {
    @Expose
    @SerializedName("driver_id")
    public String driver_id;

    @Expose
    @SerializedName("customer_id")
    public String customer_id;

    @Expose
    @SerializedName("transaction_id")
    public String transaction_id;

    @Expose
    @SerializedName("rating")
    public String rating;

    @Expose
    @SerializedName("note")
    public String note;

}
