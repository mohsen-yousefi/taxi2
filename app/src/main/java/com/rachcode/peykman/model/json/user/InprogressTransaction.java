package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.Driver;
import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.json.fcm.DriverRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androgo on 10/13/2018.
 */

public class InprogressTransaction {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("driver_request")
    @Expose
    private DriverRequest driver_request;

    @SerializedName("driver")
    @Expose
    private Driver driver;

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public DriverRequest getData() {
        return driver_request;
    }

    public void setData(DriverRequest data) {
        this.driver_request = data;
    }

    public Driver getdriver() {
        return driver;
    }

    public void setdriver(Driver data) {
        this.driver = driver;
    }
}
