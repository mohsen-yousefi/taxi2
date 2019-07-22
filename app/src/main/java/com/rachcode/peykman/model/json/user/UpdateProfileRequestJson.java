package com.rachcode.peykman.model.json.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Androgo on 10/13/2018.
 */

public class UpdateProfileRequestJson {

    @SerializedName("first_name")
    @Expose
    public String first_name;

    @SerializedName("last_name")
    @Expose
    public String last_name;

    @SerializedName("profile_picture")
    @Expose
    public String profile_picture;

    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("id")
    @Expose
    public String id;

}