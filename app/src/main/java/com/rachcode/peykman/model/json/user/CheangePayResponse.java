package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rachcode.peykman.model.User;
import com.rachcode.peykman.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androgo on 10/13/2018.
 */

public class CheangePayResponse {

    @SerializedName("status")
    @Expose
    private String status;


    public String getMessage() {
        return status;
    }

    public void setMessage(String message) {
        this.status = message;
    }

}
