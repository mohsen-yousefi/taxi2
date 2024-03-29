package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rachcode.peykman.model.AdditionalMbox;

/**
 * Created by haris on 12/23/16.
 */

public class GetAdditionalMboxResponseJson {

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("data")
    public AdditionalMbox data;

}
