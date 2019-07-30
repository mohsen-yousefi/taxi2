package com.rachcode.peykman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rachcode.peykman.R;

import java.io.Serializable;

/**
 * Created by haris on 12/1/16.
 */

public class CountData implements Serializable {
    @Expose
    @SerializedName("driver_job")
    public String driver_job;

    @Expose
    @SerializedName("COUNT")
    public int COUNT;

}
