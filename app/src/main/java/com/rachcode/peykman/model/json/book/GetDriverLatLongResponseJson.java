package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.FavoriteAddress;

import java.util.List;

public class GetDriverLatLongResponseJson {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<GetDataRestoRequestJson> data = null;

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public List<GetDataRestoRequestJson> getData() {
        return data;
    }

    public void setData(List<GetDataRestoRequestJson> data) {
        this.data = data;
    }
}
