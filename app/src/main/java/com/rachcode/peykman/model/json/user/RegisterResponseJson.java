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

public class RegisterResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<UserData> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }
}
