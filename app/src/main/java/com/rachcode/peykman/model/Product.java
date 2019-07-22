package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haris on 12/23/16.
 */

public class Product implements Serializable {
    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("product_type")
    public  String product_type;


}
