package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by haris on 12/11/16.
 */

public class Banner implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("ads_features")
    @Expose
    private String adsFeatures;
    @SerializedName("photo")
    @Expose
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdsFeatures() {
        return adsFeatures;
    }

    public void setAdsFeatures(String adsFeatures) {
        this.adsFeatures = adsFeatures;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
