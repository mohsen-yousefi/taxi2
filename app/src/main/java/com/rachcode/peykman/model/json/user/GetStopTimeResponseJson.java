package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.GetStopTime;

import java.util.List;

public class GetStopTimeResponseJson {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<GetStopTime> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetStopTime> getData() {
        return data;
    }

    public void setData(List<GetStopTime> data) {
        this.data = data;
    }
}
