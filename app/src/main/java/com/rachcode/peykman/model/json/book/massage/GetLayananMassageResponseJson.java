package com.rachcode.peykman.model.json.book.massage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androgo on 12/22/2018.
 */

public class GetLayananMassageResponseJson {

    @Expose
    @SerializedName("message")
    private String message;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


 }
