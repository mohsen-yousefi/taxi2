package com.rachcode.peykman.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.Driver;

import java.util.ArrayList;

public class GetNearRideDriverResponseJson {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Driver> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Driver> getData() {
        return data;
    }

    public void setData(ArrayList<Driver> data) {
        this.data = data;
    }
}
