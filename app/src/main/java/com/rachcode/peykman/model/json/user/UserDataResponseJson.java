package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.UserData;

import java.util.List;

public  class UserDataResponseJson {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<UserData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

//    test
}
