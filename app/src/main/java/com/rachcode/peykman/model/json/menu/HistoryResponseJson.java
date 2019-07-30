package com.rachcode.peykman.model.json.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rachcode.peykman.model.CountData;
import com.rachcode.peykman.model.ItemHistory;

import java.util.ArrayList;

/**
 * Created by haris on 11/28/16.
 */

public class HistoryResponseJson {
    @Expose
    @SerializedName("message")
    public String mesage;

    @Expose
    @SerializedName("data")
    public ArrayList<ItemHistory> data = new ArrayList<>();

    @Expose
    @SerializedName("count_data")
    public ArrayList<CountData>  count_data = new ArrayList<>();

}
