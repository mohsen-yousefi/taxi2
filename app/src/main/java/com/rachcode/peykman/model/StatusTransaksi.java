package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fathony on 11/02/2017.
 */

public class StatusTransaksi {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("information")
    @Expose
    private String information;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return information;
    }

    public void setKeterangan(String information) {
        this.information = information;
    }
}
