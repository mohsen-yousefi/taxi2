package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.AdditionalMbox;
import com.rachcode.peykman.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haris on 12/23/16.
 */

public class GetProductResponseJson {

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("data")
    public ArrayList<Product> data;

}
