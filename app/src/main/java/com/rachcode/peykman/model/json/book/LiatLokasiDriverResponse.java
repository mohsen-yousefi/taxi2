package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rachcode.peykman.model.LokasiDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fathony on 24/02/2017.
 */

public class LiatLokasiDriverResponse {

    @SerializedName("data")
    @Expose
    private List<LokasiDriver> data = new ArrayList<>();

    public List<LokasiDriver> getData() {
        return data;
    }

}
