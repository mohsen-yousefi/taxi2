package com.rachcode.peykman.model.json.book;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.model.FavoriteAddress;

import java.util.List;

public class GetFavoriteAddressResponseJson {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<FavoriteAddress> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FavoriteAddress> getData() {
        return data;
    }

    public void setData(List<FavoriteAddress> data) {
        this.data = data;
    }
}
