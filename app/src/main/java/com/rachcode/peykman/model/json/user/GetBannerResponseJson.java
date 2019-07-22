package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.Banner;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androgo on 10/17/2018.
 */

public class GetBannerResponseJson {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Banner> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Banner> getData() {
        return data;
    }

    public void setData(ArrayList<Banner> data) {
        this.data = data;
    }

}
