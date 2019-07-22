package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by haris on 12/23/16.
 */

public class MboxInsurance implements Serializable {
    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("premium")
    public long premium;

    @Expose
    @SerializedName("estimated_costs")
    public long estimated_costs;

}
