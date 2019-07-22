package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.Driver;

import java.util.ArrayList;

/**
 * Created by Androgo on 12/21/2018.
 */

public class offerCodeResponseJson {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private int data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

}
